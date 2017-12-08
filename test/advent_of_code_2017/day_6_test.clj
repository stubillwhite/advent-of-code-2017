(ns advent-of-code-2017.day-6-test
  (:require [advent-of-code-2017.day-6 :refer :all]
            [clojure.test :refer :all]))

(defn redistribute-n-times [banks n]
  (first (drop n (iterate redistribute banks))))

(deftest redistribute-given-example-input-then-example-result
  (let [banks [0 2 7 0]]
    (is (= [2 4 1 2] (redistribute-n-times banks 1)))
    (is (= [3 1 2 3] (redistribute-n-times banks 2)))
    (is (= [0 2 3 4] (redistribute-n-times banks 3)))
    (is (= [1 3 4 1] (redistribute-n-times banks 4)))
    (is (= [2 4 1 2] (redistribute-n-times banks 5)))))
