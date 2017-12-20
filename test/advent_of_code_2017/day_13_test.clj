(ns advent-of-code-2017.day-13-test
  (:require [advent-of-code-2017.day-13 :refer :all]
            [advent-of-code-2017.utils :refer [def-]]
            [clojure.test :refer :all]))

(def- example-input
  ["0: 3"
   "1: 2"
   "4: 4"
   "6: 4"])

(deftest total-severity-given-example-input-then-example-result
  (is (= 24 (total-severity example-input))))

(deftest safe-packet-delay-given-example-input-then-example-result
  (is (= 10 (safe-packet-delay example-input))))
