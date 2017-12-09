(ns advent-of-code-2017.day-7
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- split-newline [s]
  (string/split s #"\n"))

(defn parse-line [s]
  (let [[_ name weight csv] (re-find #"^([a-z]+) \((\d+)\)(?: -> (.+))?" s)]
    {:name     name
     :weight   (Integer/parseInt weight)
     :children (into #{} (if csv (string/split csv #", ") []))}))

(def- programs
  (->> (io/resource "day-7-input.txt")
       (slurp)
       (string/trim)
       (split-newline)
       (map parse-line)))

;; Part one

;; Might be a neater way to do this, but a simple topological sort will give us the root

(defn- dependency-graph [programs]
  (into {} (for [prg programs] [(prg :name) (prg :children)])))

(defn- first-satisfied-node [graph]
  (some (fn [[k v]] (when (empty? v) k)) graph))

(defn- map-kv [f m]
  (reduce-kv (fn [acc k v] (assoc acc k (f k v))) {} m))

(defn- remove-node [graph node]
  (map-kv (fn [k v] (disj v node))
          (dissoc graph node)))

(defn topological-sort [graph]
  (loop [graph  graph
         sorted []]
    (if (empty? graph)
      sorted
      (let [node (first-satisfied-node graph)]
        (if-not node
          (throw (RuntimeException. "Graph contains cycles or is incomplete"))
          (recur (remove-node graph node)
                 (conj sorted node)))))))

(defn root-program [programs]
  (let [sorted (topological-sort (dependency-graph programs))]
    (last sorted)))

(defn solution-part-one []
  (root-program programs))
