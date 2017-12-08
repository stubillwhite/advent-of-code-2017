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

(defn redistribute-until-cycle-detected [banks]
  (loop [banks  banks
         states {banks 0}]
    (let [new-state (redistribute banks)]
      (if (contains? states new-state)
        {:states         states
         :repeated-state new-state}
        (recur new-state (assoc states new-state (count states)))))))

(defn redistribution-cycle-length [banks]
  (let [{:keys [states]} (redistribute-until-cycle-detected)]
    (count states)))

(defn solution-part-one []
  (redistribution-cycle-length initial-state))

;; Part two

;; I guess we could either just iterate the loop twice, or else keep track of the index of each state in a map of state
;; to index. I'm going to go for the latter.

(defn cycle-length [banks]
  (let [{:keys [states repeated-state]} (redistribute-until-cycle-detected banks)]
    (- (count states) (states repeated-state))))

(defn solution-part-two []
  (cycle-length initial-state))
