(ns advent-of-code-2017.day-5
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- split-newline [s]
  (string/split s #"\n"))

(def- maze
  (->> (io/resource "day-5-input.txt")
       (slurp)
       (string/trim)
       (split-newline)
       (map (fn [x] (Integer/parseInt x)))))

(defn steps-to-exit [maze]
  (letfn [(jump [steps maze idx]
            (if (or (< idx 0) (>= idx (count maze)))
              steps
              (let [offset (maze idx)]
                (recur (inc steps) (update maze idx inc) (+ offset idx)))))]
    (jump 0 (into [] maze) 0)))

(defn solution-part-one []
  (steps-to-exit maze))
