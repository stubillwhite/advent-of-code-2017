(ns advent-of-code-2017.day-2-test
  (:require [advent-of-code-2017.day-2 :refer :all]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def part-one-example-input
  (let [s ["5 1 9 5"
           "7 5 3"
           "2 4 6 8"]]
    (string/replace (string/join "\n" s) " " "\t")))

(deftest checksum-given-part-one-expected-input-then-expected-result
  (is (= 18 (checksum-calculator min-max-row-checksum part-one-example-input))))

(def part-two-example-input
  (let [s ["5 9 2 8"
           "9 4 7 3"
           "3 8 6 5"]]
    (string/replace (string/join "\n" s) " " "\t")))

(deftest checksum-given-part-two-expected-input-then-expected-result
  (is (= 9 (checksum-calculator even-divisor-row-checksum part-two-example-input))))
