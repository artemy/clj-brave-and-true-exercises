(ns clj-brave-and-true.chapter05-test
  (:require [clojure.test :refer [deftest testing is]]
            [clj-brave-and-true.chapter05 :refer [attr my-comp my-assoc-in my-update-in]]))

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

(deftest attr-test
  (testing
   (let [expected 10]
     (is (= expected ((attr :intelligence) character))))))

(deftest my-comp-test
  (testing
  (is (= "Smooches McCutes" ((my-comp :name) character)))
  (is (= 5 ((my-comp :dexterity :attributes) character)))))

(deftest my-assoc-in-test
  (testing
  (let [expected {:name "Smooches McCutes"
                  :attributes {:intelligence 10
                               :strength 4
                               :dexterity 5
                               :humor 0}}]
   (is (= expected (my-assoc-in character [:attributes :humor] 0))))))

(deftest my-update-in-test
  (testing
   (let [expected {:name "Smooches McCutes"
                  :attributes {:intelligence 10
                               :strength 4
                               :dexterity 6}}]
    (is (= expected (my-update-in character [:attributes :dexterity] inc))))))