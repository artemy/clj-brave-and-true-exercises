(ns clj-brave-and-true.chapter07)

;; Exercise 1: Use the list function, quoting, and read-string
;; to create a list that, when evaluated,
;; prints your first name and your favorite sci-fi movie.

(def first-name-and-movie 
  (list (read-string "println") '(str "John Snow" ", " "Harry Potter and the Curse of the Parentheses")))


;; Exercise 2: Create an infix function 
;; that takes a list like (1 + 3 * 4 - 5) 
;; and transforms it into the lists 
;; that Clojure needs in order to correctly evaluate
;; the expression using operator precedence rules.

;; (1 + 3 * 4 - 5)
(def operators {'+ 1 '- 1 '* 2 '/ 2})

(defn priority 
  [operator] 
  (get operators operator 0))

(defn find-max-priority 
  [symbols] 
  (apply max (map priority symbols)))

(defn infix-to-prefix
  ([symbols] 
   (if (second symbols)
     (recur (infix-to-prefix symbols (find-max-priority symbols)))
     (first symbols)))
  ([symbols max-priority]
   (let [[left operator right & rest] symbols]
     (if (= (priority operator) max-priority)
       (apply list (list operator left right) rest)
       (apply list left operator (infix-to-prefix (cons right rest) max-priority))))))