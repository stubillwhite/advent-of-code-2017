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
