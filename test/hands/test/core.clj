(ns hands.test.core
  (:use [hands.core])
  (:use [clojure.test]))

(deftest card-equality 
  (is (card-equal? (make-card 5 :diamonds) (make-card 5 :diamonds)) "card should be equals"))

(deftest card-inequality 
  (is (not (card-equal? (make-card 5 :diamonds) (make-card 3 :diamonds)))))

(deftest suite-existence
  (is (thrown-with-msg? Exception #"Suite does not exist" (make-card 5 :dkjf))))

(deftest card-in-col
  (is (card-in? [(make-card 2 :clubs), (make-card 5 :clubs)] (make-card 2 :clubs))))

(deftest card-not-in-col
  (is (not (card-in? [(make-card 2 :clubs), (make-card 5 :clubs)] (make-card 2 :diamonds)))))

