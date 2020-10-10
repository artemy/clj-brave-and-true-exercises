(ns clj-brave-and-true.chapter08-test
  (:require [clojure.test :refer [deftest testing is]]
            [clj-brave-and-true.chapter08 :refer [when-valid custom-or defattrs]]))

(deftest when-valid-test
  (testing
   (let [valid-output "It's a success!\n:success\n"
         order-details-invalid
         {:name "Mitchard Blimmons"
          :email "mitchard.blimmonsgmail.com"}
         order-details-valid
         {:name "Mitchard Blimmons"
          :email "mitchard.blimmons@gmail.com"}
         order-details-validations
         {:name
          ["Please enter a name" not-empty]
          :email
          ["Please enter an email address" not-empty
           "Your email address doesn't look like an email address"
           #(or (empty? %) (re-seq #"@" %))]}]
     (is (= valid-output (with-out-str (when-valid order-details-valid order-details-validations
                                                   (println "It's a success!")
                                                   (println :success)))))
     (is (= "" (with-out-str (when-valid order-details-invalid order-details-validations
                                         (println "It's a success!")
                                         (println :success))))))))

(deftest custom-or-test
  (testing
   (is (= true (custom-or false false false false true)))
    (is (= false (custom-or false false false false false)))
    (is (= true (custom-or true)))))

(deftest defattrs-test
  (testing
   (let [expected '(do
                     (def clj-brave-and-true.chapter08-test/c-int :intelligence)
                     (def clj-brave-and-true.chapter08-test/c-str :strength)
                     (def clj-brave-and-true.chapter08-test/c-dex :dexterity))]
     (is (= expected (macroexpand `(defattrs c-int :intelligence
                                     c-str :strength
                                     c-dex :dexterity)))))))