(ns advent-of-code-2017.day-15
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

;; It feels like there's definitely optimisations to be had here. Let's brute force for now.
;;
;; This isn't fast on my machine; 40 million pairs takes 70 seconds. May return to this to optimise.

(def- generator-a-factor 16807)
(def- generator-b-factor 48271)

(defn generator [factor seed]
  (drop 1 (iterate (fn [x] (mod (* x factor) 2147483647)) seed)))

(def generator-a (partial generator generator-a-factor))
(def generator-b (partial generator generator-b-factor))

(defn- equal-lower-16-bits [x y]
  (= (bit-and x 0xFFFF) (bit-and y 0xFFFF)))

(defn- judge [gen-a gen-b]
  (reduce (fn [acc [a b]] (if (equal-lower-16-bits a b) (inc acc) acc))
          0
          (partition 2 (interleave gen-a gen-b))))

(defn judge-n-values [gen-a gen-b n]
  (judge
   (take n gen-a)
   (take n gen-b)))

(defn solution-part-one []
  (judge-n-values (generator-a 699)
                  (generator-b 124)
                  (* 40 1000 1000)))

;; Part two

(defn picky-generator-a [seed]
  (->> (generator generator-a-factor seed)
       (filter (fn [x] (zero? (mod x 4))))))

(defn picky-generator-b [seed]
  (->> (generator generator-b-factor seed)
       (filter (fn [x] (zero? (mod x 8))))))

(defn solution-part-two []
  (judge-n-values (picky-generator-a 699)
                  (picky-generator-b 124)
                  (* 5 1000 1000)))
