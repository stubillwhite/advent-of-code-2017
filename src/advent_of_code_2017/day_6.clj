(ns advent-of-code-2017.day-6
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- split-tab [s]
  (string/split s #"\t"))

(defn- to-int [s]
  (Integer/parseInt s))

(def- initial-state
  (->> (io/resource "day-6-input.txt")
       (slurp)
       (string/trim)
       (split-tab)
       (map to-int)
       (into [])))

(defn- index-of [x xs]
  (keep-indexed #(when (= %2 x) %1) xs))

(defn- most-blocks [banks]
  (first (index-of (apply max banks) banks)))

(defn- redistribution-indices [banks n idx]
  (->> (cycle (range (count banks)))
       (drop (inc idx))
       (take n)))

(defn redistribute [banks]
  (let [idx     (most-blocks banks)
        n       (banks idx)
        indices (redistribution-indices banks n idx)]
    (reduce (fn [acc x] (update acc x inc)) (assoc banks idx 0) indices)))

(defn redistribution-cycle-length [banks]
  (loop [banks  banks
         states #{banks}]
    (let [new-state (redistribute banks)]
      (if (contains? states new-state)
        (count states)
        (recur new-state (conj states new-state))))))

(defn solution-part-one []
  (redistribution-cycle-length initial-state))

