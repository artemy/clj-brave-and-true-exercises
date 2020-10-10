(ns clj-brave-and-true.chapter08)

(defn error-messages-for
  "Return a seq of error messages"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 message-validator-pairs))))

(defn validate
  "Returns a map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[fieldname validation-check-groups] validation
                  value (get to-validate fieldname)
                  error-messages (error-messages-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
          {}
          validations))

;; Exercise 1: Write the macro when-valid so that it behaves similarly to `when`.
(defmacro when-valid
  [to-validate validations & forms]
  `(when (empty? (validate ~to-validate ~validations))
     ~@forms))

;; Exercise 2: You saw that `and` is implemented as a macro. Implement `or` as a macro.
(defmacro custom-or
  ([] nil)
  ([x] x)
  ([x & next]
   `(let [or# ~x]
      (if or# or# (custom-or ~@next)))))

;; Exercise 3: In Chapter 5 you created a series of functions (c-int, c-str, c-dex) 
;; to read an RPG character’s attributes. Write a macro that defines an arbitrary number 
;; of attribute-retrieving functions using one macro call.
(defmacro defattrs
  [& args]
  `(do
     ~@(map
        (fn [[func attr]] `(def ~func ~attr))
        (partition 2 args))))