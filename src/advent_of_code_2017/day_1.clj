(ns advent-of-code-2017.day-1
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def captcha
  (string/trim (slurp (io/resource "day-1-input.txt"))))

(defn- digit-seq [x]
  (->> x
       (str)
       (seq)
       (map #(Integer/parseInt (str %)))))

(defn pair-adjacent [x]
  (let [digits (digit-seq x)]
    (->> (interleave digits (drop 1 (cycle digits)))
         (partition 2))))

(defn sum-of-digits [pair-fn x]
  (->> (pair-fn x)
       (filter #(apply = %))
       (map first)
       (apply +)))

(defn solution-part-one []
  (sum-of-digits pair-adjacent captcha))

;; Part two

(defn pair-halfway [x]
  (let [digits (digit-seq x)
        n      (/ (count digits) 2)]
    (->> (interleave digits (drop n (cycle digits)))
         (partition 2))))

(defn solution-part-two []
  (sum-of-digits pair-halfway captcha))

