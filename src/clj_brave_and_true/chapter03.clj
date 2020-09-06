(ns clj-brave-and-true.chapter03
  (:require [clojure.string]))

;; Exercise 2: Write a function that takes a number and adds 100 to it.
(defn add100 [n] (+ 100 n))

;; Exercise 3: Write a function, dec-maker, that works 
;; exactly like the function inc-maker except with subtraction
(defn dec-maker [x] (fn [y] (- y x)))

;; Exercise 4: Write a function, mapset, that works like map
;; except the return value is a set
(defn mapset [f args] (set (map f args)))