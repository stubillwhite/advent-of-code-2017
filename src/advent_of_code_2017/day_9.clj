(ns advent-of-code-2017.day-9
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def- stream
  (->> (io/resource "day-9-input.txt")
       (slurp)
       (string/trim)))

(defn- consume-character [state x]
  (cond
    (state :escape-next)  (assoc state :escape-next false)
    (= x \!)              (assoc state :escape-next true)
    (= x \>)              (assoc state :in-garbage false)
    (state :in-garbage)   (update state :consumed-garbage inc)
    (= x \<)              (assoc state :in-garbage true)
    (= x \{)              (update state :depth inc)
    (= x \})              (-> state
                              (update :depth dec)
                              (update :score (partial + (state :depth))))
    :else                 state))

(defn total-score [s]
  (get (reduce consume-character {:depth 0 :score 0 :consumed-garbage 0} (seq s))
       :score))

(defn solution-part-one []
  (total-score stream))

;; Part two

(defn consumed-garbage [s]
  (get (reduce consume-character {:depth 0 :score 0 :consumed-garbage 0} (seq s))
       :consumed-garbage))

(defn solution-part-two []
  (consumed-garbage stream))
