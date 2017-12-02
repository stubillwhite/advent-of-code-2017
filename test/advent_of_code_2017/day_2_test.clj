(ns advent-of-code-2017.day-2-test
  (:require [advent-of-code-2017.day-2 :refer :all]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def example-input
  (let [s ["5 1 9 5"
           "7 5 3"
           "2 4 6 8"]]
    (string/replace (string/join "\n" s) " " "\t")))

(deftest sum-of-digits-given-part-one-expected-input-then-expected-result
  (is (= 18 (min-max-checksum-calculator example-input))))
