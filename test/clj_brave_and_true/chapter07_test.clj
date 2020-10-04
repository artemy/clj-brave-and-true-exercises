(ns clj-brave-and-true.chapter07-test
  (:require [clojure.test :refer [deftest testing is]]
            [clj-brave-and-true.chapter07 :refer [first-name-and-movie infix-to-prefix]]))

(deftest first-name-and-movie-test
  (testing
   (let [expected '(println 
                   (str "John Snow" ", " "Harry Potter and the Curse of the Parentheses"))]
     (is (= expected first-name-and-movie)))))

(deftest infix-to-prefix-test
  (testing
   (let [expected '(- (+ 1 (* 3 4)) 5)]
     (is (= expected (infix-to-prefix '(1 + 3 * 4 - 5)))))))