(ns advent-of-code-2017.day-10
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- split-comma [s]
  (string/split s #","))

(def- problem-input
  (->> (io/resource "day-10-input.txt")
       (slurp)
       (string/trim)))

(def- lengths
  (->> problem-input
       (split-comma)
       (map #(Integer/parseInt %))))

;; Make life a little easier by transforming the list so that sublists can be selected from zero, then rotated back to
;; the right position.

(defn- rotate-to-position [xs pos]
  (let [length (count xs)]
    (take length (drop pos (cycle xs)))))

(defn- rotate-from-position [xs pos]
  (let [length (count xs)]
    (take length (drop (- length pos) (cycle xs)))))

(defn apply-to-sublist [f xs start n]
  (let [normalised (rotate-to-position xs start)]
    (-> (concat (f (take n normalised))
                (drop n normalised))
        (rotate-from-position start))))

(defn- twist-iter [{:keys [xs skip-size pos]} length]
  {:xs        (apply-to-sublist reverse xs pos length)
   :skip-size (inc skip-size)
   :pos       (mod (+ length pos skip-size) (count xs))})

(defn twist [xs lengths]
  (reduce twist-iter
          {:xs           xs
           :skip-size    0
           :pos          0}
          lengths))

(defn solution-part-one []
  (let [result       (twist (range 256) lengths)
        [x1 x2 & _]  (result :xs)]
    (* x1 x2)))

;; Part two

(defn to-byte-stream [s]
  (map int (seq s)))

(defn- append-suffix [xs]
  (concat xs [17 31 73 47 23]))

(defn- twist-repeated [xs lengths rounds]
  (let [final-state (twist xs (apply concat (repeat rounds lengths)))]
    (final-state :xs)))

(defn dense-hash [xs]
  (map (fn [x] (apply bit-xor x)) (partition 16 xs)))

(defn to-hexadecimal-string [xs]
  (->> xs
       (map (fn [x] (format "%02x" x)))
       (apply str)))

(defn knot-hash [s]
  (let [lengths (-> s (to-byte-stream) (append-suffix))]
    (-> (twist-repeated (range 256) lengths 64)
        (dense-hash)
        (to-hexadecimal-string))))

(defn solution-part-two []
  (knot-hash problem-input))
