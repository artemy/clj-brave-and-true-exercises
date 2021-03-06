(ns clj-brave-and-true.chapter05)

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

(def c-int (comp :intelligence :attributes))

(c-int character)

;; Exercise 1: You used (comp :intelligence :attributes) 
;; to create a function that returns a character’s intelligence. 
;; Create a new function, attr, that you can call 
;; like (attr :intelligence) and that does the same thing.
(defn attr
  [key]
  (comp key :attributes))

;; Exercise 2: Implement the comp function.
(defn my-comp 
  ([] identity)
  ([f] f)
  ([f g]
   (fn
     ([] (f (g)))
     ([arg] (f (g arg)))
     ([arg1 arg2] (f (g arg1 arg2)))
     ([arg1 arg2 & args] (f apply (g arg1 arg2 args)))))
  ([f g & funcs] 
   (reduce my-comp (list* f g funcs))))

;; Exercise 3: Implement the assoc-in function.
;; Hint: use the assoc function and define its parameters as [m [k & ks] v].
(defn my-assoc-in 
  [m [k & ks] v]
    (if ks
    (assoc m k (my-assoc-in (get m k) ks v)) 
    (assoc m k v)))

;; Exercise 5: Implement update-in.
(defn my-update-in 
  [m [k & ks] f & args]
  (if ks
    (assoc m k (apply my-update-in (get m k) ks f args))
    (assoc m k (apply f (get m k) args))))