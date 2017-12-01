(ns advent-of-code-2017.day-1-test
  (:require [advent-of-code-2017.day-1 :refer :all]
            [clojure.test :refer :all]))

(deftest sum-of-digits-given-expected-input-then-expected-result
  (is (= 3 (sum-of-digits 1122)))
  (is (= 4 (sum-of-digits 1111)))
  (is (= 0 (sum-of-digits 1234) 0))
  (is (= 9 (sum-of-digits 91212129))))
