(ns advent-of-code-2017.day-8-test
  (:require [advent-of-code-2017.day-8 :refer :all]
            [clojure.test :refer :all]))

(def example-lines
  ["b inc 5 if a > 1"
   "a inc 1 if b < 5"
   "c dec -10 if a >= 1"
   "c inc -20 if c == 10"])

(deftest parse-and-execute-instructions-given-example-lines-then-example-result
  (is (= {"a" 1 "c" -10 :max-value 10} (parse-and-execute-instructions example-lines))))

(deftest parse-and-execute-instructions-given-final-step-is-max-value-then-records-max-value
  (is (= {"a" 10 :max-value 10} (parse-and-execute-instructions ["a inc 10 if a == 0"]))))
