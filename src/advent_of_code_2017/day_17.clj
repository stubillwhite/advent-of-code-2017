(ns advent-of-code-2017.day-17
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn spinlock [period limit]
  (letfn [(spinlock-iter [xs len pos n]
            (if (> n limit)
              xs
              (let [split-point    (mod (+ pos period) len)
                    [before after] (split-at (inc split-point) xs)
                    new-xs         (into [] (concat before [n] after))]
                (recur new-xs (inc len) (inc split-point) (inc n)))))]
    (spinlock-iter [0] 1 0 1)))

(defn- first-index-of [x xs]
  (first (keep-indexed #(when (= %2 x) %1) xs)))

(defn value-after-spinlock [period limit]
  (let [xs  (spinlock period limit)
        idx (first-index-of limit xs)]
    (nth xs (inc idx))))

(defn solution-part-one []
  (value-after-spinlock 329 2017))

