(ns clj-brave-and-true.chapter04
  (:require [clojure.string]))

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int [str] (Integer. str))

(def conversions {:name identity :glitter-index str->int})

(defn convert [vamp-key value] ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",") (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(def suspects (mapify (parse (slurp filename))))

;; Exercise 1: Turn the result of your glitter filter into a list of names.
(defn glitter-filter-names
  [minimum-glitter records]
  (map :name (glitter-filter minimum-glitter records)))

;; Exercise 2: Write a function, append, which will append a new suspect to your list of suspects.
(defn append [map new-suspect]
  (conj map new-suspect))

;; Exercise 3: Write a function, validate, which will check that 
;; :name and :glitter-index are present when you append.
;; The validate function should accept two arguments: 
;; a map of keywords to validating functions, similar to conversions, and the record to be validated.
(defn validate [entry]
  (and (contains? entry :name) (contains? entry :glitter-index)))

(defn append-with-validate [map new-suspect] 
  (if (validate new-suspect)
    (conj map new-suspect)
    map))

;; Exercise 4: Write a function that will take your list of maps
;; and convert it back to a CSV string. You’ll need to use the clojure.string/join function.
(defn suspects->csv [suspects] (reduce (fn [input-str values]
                                         (str input-str (clojure.string/join "," values) "\n"))
                                       ""
                                       (map #(vals %) suspects)))