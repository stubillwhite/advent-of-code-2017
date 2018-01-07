(ns advent-of-code-2017.day-16-test
  (:require [advent-of-code-2017.day-16 :refer :all]
            [advent-of-code-2017.utils :refer [def-]]
            [clojure.test :refer :all]))

(deftest execute-actions-given-example-actions-then-example-results
  (is (= "eabcd" (execute-actions "abcde" ["s1"])))
  (is (= "eabdc" (execute-actions "eabcd" ["x3/4"])))
  (is (= "baedc" (execute-actions "eabdc" ["pe/b"]))))


