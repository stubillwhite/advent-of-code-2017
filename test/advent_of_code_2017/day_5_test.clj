(ns advent-of-code-2017.day-5-test
  (:require [advent-of-code-2017.day-5 :refer :all]
            [clojure.test :refer :all]))

(deftest steps-to-exit-given-part-one-mutator-and-example-input-then-example-result
  (is (= 5 (steps-to-exit [0 3 0 1 -3] inc))))

(deftest steps-to-exit-given-part-two-mutator-and-example-input-then-example-result
  (is (= 10 (steps-to-exit [0 3 0 1 -3] inc-if-greater-than-three))))
