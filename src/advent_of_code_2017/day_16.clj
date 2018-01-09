(ns advent-of-code-2017.day-16
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- split-comma [s]
  (string/split s #","))

(def- steps
  (into [] 
     (->> (io/resource "day-16-input.txt")
          (slurp)
          (string/trim)
          (split-comma))))

(defn- first-index-of [x xs]
  (first (keep-indexed #(when (= %2 x) %1) xs)))

(defn- spin [state n]
  (let [length (count state)]
    (into [] (concat (drop (- length n) state)
                     (take (- length n) state)))))

(defn- exchange [state a b]
  (let [elem-a (nth state a)
        elem-b (nth state b)]
    (-> state
        (assoc a elem-b)
        (assoc b elem-a))))

(defn- partner [state a b]
  (exchange state (first-index-of a state) (first-index-of b state)))

(defn- take-step [state s]
  (let [to-int            (fn [x] (Integer/parseInt x))
        pattern           #"^(.)(.+)$"
        [_ step params] (re-find pattern s)]
    (case step
      "s" (spin state (to-int params))
      "x" (let [[a b] (map to-int (string/split params #"/"))]
            (exchange state a b))
      "p" (let [[a b] (map first (string/split params #"/"))]
            (partner state a b)))))

(defn dance [state steps]
  (apply str (reduce take-step (into [] (seq state)) steps)))

(defn solution-part-one []
  (dance "abcdefghijklmnop" steps))

;; Part two

;; For a billion iterations brute force isn't going to cut it. However, the input for the example for part two does
;; repeat after four iterations so I think it's just a case of finding the repetition period and using that to reduce
;; the computation time. It's not obvious to me that all inputs would have a period, so this feels a little fake to me,
;; but that the example for part two repeats seems like a big hint that it's true for these inputs.

(defn- period [xs]
  (letfn [(period-iter [seen [x & xs]]
            (cond
              (contains? seen x) (count seen)
              (nil? x)           (count seen)
              :else              (recur (conj seen x) xs)))]
    (period-iter #{} xs)))

(defn iterated-dance [state steps n]
  (let [states       (iterate (fn [x] (dance x steps)) state)
        dance-period (period states)
        remaining    (mod n dance-period)]
    (nth states remaining)))

(defn solution-part-two []
  (iterated-dance "abcdefghijklmnop" steps 100000))





