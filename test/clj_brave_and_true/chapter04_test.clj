(ns clj-brave-and-true.chapter04-test
  (:require [clojure.test :refer [deftest testing is]]
            [clj-brave-and-true.chapter04 :refer 
             [suspects glitter-filter-names append validate append-with-validate suspects->csv]]))

(deftest glitter-filter-names-test
  (testing
   (let [expected '("Edward Cullen" "Jacob Black" "Carlisle Cullen")]
     (is (= expected (glitter-filter-names 2 suspects))))))

(deftest append-test
  (testing
   (let [suspects '({:name "Edward Cullen", :glitter-index 10}
                    {:name "Bella Swan", :glitter-index 0})
         expected '({:name "Charlie Swan", :glitter-index 4}
                    {:name "Edward Cullen", :glitter-index 10}
                    {:name "Bella Swan", :glitter-index 0})]
     (is (= expected (append suspects {:name "Charlie Swan" :glitter-index 4}))))))

(deftest validate-test
  (testing
   (is (= false (validate {:some-unrelated-key "Value"}))))
  (testing
   (is (= true (validate {:name "John Snow" :glitter-index 0})))))

(deftest append-with-validate-test
  (testing
   (let [suspects '({:name "Edward Cullen" :glitter-index 10}
                    {:name "Bella Swan" :glitter-index 0})]
     (is (= suspects (append-with-validate suspects {:name "Charlie Swan"}))))))

(deftest suspects-to-csv
  (testing
   (let [expected "Edward Cullen,10\nBella Swan,0\nCharlie Swan,0\nJacob Black,3\nCarlisle Cullen,6\n"]
     (is (= expected (suspects->csv suspects))))))