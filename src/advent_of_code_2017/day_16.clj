(ns advent-of-code-2017.day-16
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- split-comma [s]
  (string/split s #","))

(def- actions
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

(defn- execute-action [state s]
  (let [to-int            (fn [x] (Integer/parseInt x))
        pattern           #"^(.)(.+)$"
        [_ action params] (re-find pattern s)]
    (case action
      "s" (spin state (to-int params))
      "x" (let [[a b] (map to-int (string/split params #"/"))]
            (exchange state a b))
      "p" (let [[a b] (map first (string/split params #"/"))]
            (partner state a b)))))

(defn execute-actions [state actions]
  (apply str (reduce execute-action (into [] (seq state)) actions)))

(defn solution-part-one []
  (execute-actions "abcdefghijklmnop" actions))
