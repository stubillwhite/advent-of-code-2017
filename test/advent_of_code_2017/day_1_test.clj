(ns advent-of-code-2017.day-1-test
  (:require [advent-of-code-2017.day-1 :refer :all]
            [clojure.test :refer :all]))

(deftest sum-of-digits-given-part-one-example-input-then-example-result
  (is (= 3 (sum-of-digits pair-adjacent 1122)))
  (is (= 4 (sum-of-digits pair-adjacent 1111)))
  (is (= 0 (sum-of-digits pair-adjacent 1234) 0))
  (is (= 9 (sum-of-digits pair-adjacent 91212129))))

(deftest sum-of-digits-given-part-two-example-input-then-example-result
  (is (= 6 (sum-of-digits pair-halfway 1212)))
  (is (= 0 (sum-of-digits pair-halfway 1221)))
  (is (= 4 (sum-of-digits pair-halfway 123425)))
  (is (= 12 (sum-of-digits pair-halfway 123123)))
  (is (= 4 (sum-of-digits pair-halfway 12131415))))
