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

(defn- min-max-row-checksum [xs]
  (let [sorted (sort xs)
        max-x  (last sorted)
        min-x  (first sorted)]
    (- max-x min-x)))

(def- sum (partial apply +))

(defn min-max-checksum-calculator [s]
  (->> (parse-spreadsheet s)
       (map min-max-row-checksum)
       (sum)))

(defn solution-part-one []
  (min-max-checksum-calculator spreadsheet)) 

