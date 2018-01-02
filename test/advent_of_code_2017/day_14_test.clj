(ns advent-of-code-2017.day-14-test
  (:require [advent-of-code-2017.day-14 :refer :all]
            [advent-of-code-2017.utils :refer [def-]]
            [clojure.test :refer :all]))

(def- example-key-string "flqrgnkx")

(def- example-grid
  ["11010100"
   "01010101"   
   "00001010"   
   "10101101"   
   "01101000"   
   "11001001"   
   "01000100"   
   "11010110"])

(defn- top-left-sample [grid]
  (map (fn [x] (apply str (take 8 x)))
       (take 8 grid)))

(deftest binary-grid-given-example-input-then-example-grid
  (is (= example-grid (top-left-sample (binary-grid example-key-string)))))

(deftest count-used-squares-given-example-input-then-example-output
  (is (= 8108 (count-used-squares (binary-grid example-key-string)))))

(deftest index-grid-given-grid-then-indexed-content
  (let [grid     ["123"
                  "456"
                  "789"]
        expected {[0 0] \1 [1 0] \2 [2 0] \3
                  [0 1] \4 [1 1] \5 [2 1] \6
                  [0 2] \7 [1 2] \8 [2 2] \9}]
    (is (= expected (index-grid grid)))))

(deftest connected-groups-given-groups-exist-then-identifies-groups
  (let [raw-grid ["0011"
                  "0001"
                  "1110"
                  "0001"]
        grid     (index-binary-grid raw-grid)
        expected #{#{[2 0] [3 0] [3 1]}
                   #{[0 2] [1 2] [2 2]}
                   #{[3 3]}}]
    (is (= expected (connected-groups grid)))))

(deftest count-of-connected-groups-given-example-input-then-example-count
  (is (= 1242 (count-of-connected-groups example-key-string))))
