(ns advent-of-code-2017.day-16-test
  (:require [advent-of-code-2017.day-16 :refer :all]
            [advent-of-code-2017.utils :refer [def-]]
            [clojure.test :refer :all]))

(deftest dance-given-example-actions-then-example-results
  (is (= "eabcd" (dance "abcde" ["s1"])))
  (is (= "eabdc" (dance "eabcd" ["x3/4"])))
  (is (= "baedc" (dance "eabdc" ["pe/b"]))))

(deftest iterated-dance-given-example-actions-then-example-results
  (is (= "abcde" (iterated-dance "abcde" ["s1" "x3/4" "pe/b"] 4))))
