(ns advent-of-code-2017.day-2
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- split-tab [s]
  (string/split s #"\t"))

(defn- split-newline [s]
  (string/split s #"\n"))

(defn- to-int [x]
  (Integer/parseInt x))

(def- spreadsheet
  (->> (io/resource "day-2-input.txt")
       (slurp)
       (string/trim)))

(defn- parse-spreadsheet [s]
  (->> s
       (split-newline)
       (map split-tab)
       (map #(map to-int %))))

(defn min-max-row-checksum [xs]
  (let [sorted (sort xs)
        max-x  (last sorted)
        min-x  (first sorted)]
    (- max-x min-x)))

(def- sum (partial apply +))

(defn checksum-calculator [f-row s]
  (->> (parse-spreadsheet s)
       (map f-row)
       (sum)))

(defn solution-part-one []
  (checksum-calculator min-max-row-checksum spreadsheet)) 

;; Part two

(defn- divides-exactly? [x y]
  (zero? (mod y x)))

(defn- find-even-divisor [[x & xs]]
  (if-let [result (first (filter (partial divides-exactly? x) xs))]
    (/ result x)
    (when (seq xs) (recur xs))))

(defn even-divisor-row-checksum [xs]
  (find-even-divisor (sort xs)))

(defn solution-part-two []
  (checksum-calculator even-divisor-row-checksum spreadsheet))
