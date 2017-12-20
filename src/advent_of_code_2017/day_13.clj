(ns advent-of-code-2017.day-13
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- split-newline [s]
   (string/split s #"\n"))
   
(def- scanners
  (->> (io/resource "day-13-input.txt")
       (slurp)
       (string/trim)
       (split-newline)))

(defn- parse-line [s]
  (map #(Integer/parseInt (string/trim %))
       (string/split s #":")))

;; Each scanner moves the full length of the range without stopping at start or end, so the period is (2 * range-1). We
;; move incrementally from depth to depth so anywhere where depth mod period is zero is where we are caught.

(defn- severity-per-layer [layers]
 (map (fn [[d r]]
         (if (zero? (mod d (* 2 (dec r))))
           (* d r)
           0))
      layers))

(defn total-severity [scanners]
  (->> (map parse-line scanners)
       (severity-per-layer)
       (apply +)))

(defn solution-part-one []
  (total-severity scanners))
