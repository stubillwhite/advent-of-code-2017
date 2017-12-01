(ns advent-of-code-2017.day-1
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- digit-seq [x]
  (->> x
      (str)
      (seq)
      (map #(Integer/parseInt (str %)))))

(defn- digit-pairs [x]
  (let [digits (digit-seq x)]
    (->> (interleave digits (drop 1 (cycle digits)))
         (partition 2)
         )))

(defn sum-of-digits [x]
  (->> (digit-pairs x)
       (filter #(apply = %))
       (map first)
       (apply +)))

(def captcha
  (string/trim (slurp (io/resource "day-1-input.txt"))))

(defn solution-part-one []
  (sum-of-digits captcha))
