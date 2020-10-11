(ns clj-brave-and-true.chapter09)

;; Exercise 1: Write a function that takes a string as an argument 
;; and searches for it on Bing and Google using the slurp function. 
;; Your function should return the HTML of the first page returned by the search.
(def search-engines {:bing "https://www.bing.com/search?q=" :yandex "https://yandex.ru/search/?text="})

(defn search
  ([query] (search query :bing))
  ([query engine]
   (let [result (promise)]
     (future (deliver result (slurp (str (engine search-engines) query))))
     @result)))

;; Exercise 2: Update your function so it takes 
;; a second argument consisting of the search engines to use.
(defn search-multiple
  ([query] (search-multiple query [:bing :yandex]))
  ([query engines]
   (let [result (promise)]
     (doseq [engine engines]
       (future (deliver result (slurp (str (engine search-engines) query)))))
     @result)))

;; Exercise 3: Create a new function that takes a search term
;; and search engines as arguments, and returns a vector of the URLs
;; from the first page of search results from each search engine.
(def url-regex #"(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]+\.[^\s]{2,}|www\.[a-zA-Z0-9]+\.[^\s]{2,})")

(defn find-urls
  "Queries the search engine and returns a list of URLs found in the results page."
  [page]
  (map second (re-seq url-regex page)))

(defn search-return-results
  "Queries each search engine from engines and returns a map of (search engine, urls future) pairs.
  URL future are futures that when derefenced return a list of URLs found in
  the results page of the search engine."
  ([query] (search-return-results query [:bing]))
  ([query engines]
   (mapcat
     #(find-urls
      (let [result (promise)]
        (future (deliver result (slurp (str (% search-engines) query))))
        @result)) engines)))