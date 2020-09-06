(ns clj-brave-and-true.chapter03-test
  (:require [clojure.test :refer [deftest testing is]]
            [clj-brave-and-true.chapter03 :refer 
             [add100 dec-maker mapset]]))

(deftest add100-test
  (testing
   (let [expected 103]
     (is (= expected (add100 3))))))

(deftest dec-maker-test
  (testing
   (let [dec9 (dec-maker 9)
         expected 1]
     (is (= expected (dec9 10))))))

(deftest mapset-test
  (testing
   (let [expected #{2 3}]
     (is (= expected (mapset inc [1 1 2 2]))))))