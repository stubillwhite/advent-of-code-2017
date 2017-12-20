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

(defn- severity-per-layer [time layers]
 (map (fn [[d r]]
         (if (zero? (mod (+ d time) (* 2 (dec r))))
           (* d r)
           nil))
      layers))

(defn total-severity [lines]
  (->> (map parse-line lines)
       (severity-per-layer 0)
       (filter identity)
       (apply +)))

(defn solution-part-one []
  (total-severity scanners))

;; Part two

;; I'm sure there's a mathematical solution for this or at least a better algorithm. We're looking for n such that n mod
;; xs is non-zero, which feels like there should be some formula or we can use or at least some sort of sieve of
;; Eratosthenes approach...
;;
;; But I can't remember enough and don't want to just look it up. So, brute force, ensuring we stop as soon as the alarm
;; triggers. Refactor to include delay in the computation, and to know the difference between not being caught and being
;; caught with zero severity.
;;
;; EDIT: Not fast. Finishes in 73s on my machine. May come back to this to speed it up later.

(defn- indexes-of [x coll]
  (map first
       (filter (fn [[idx item]] (= item x))
               (map-indexed vector coll))))

(defn- crosses-safely? [scanners time]
  (not-any? identity (severity-per-layer time scanners)))

(defn safe-packet-delay [lines]
  (let [scanners       (map parse-line lines)
        safe-crossings (map (partial crosses-safely? scanners) (range))]
    (first (indexes-of true safe-crossings))))

(defn solution-part-two []
  (safe-packet-delay scanners))
