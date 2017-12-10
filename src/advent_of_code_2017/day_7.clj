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
  (into {} (for [node programs] [(node :name) (node :children)])))

(defn- first-satisfied-node [graph]
  (some (fn [[k v]] (when (empty? v) k)) graph))

(defn- map-kv [f m]
  (reduce-kv (fn [acc k v] (assoc acc k (f k v))) m m))

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

;; Part two

;; Given the sorted tree, we can just process each node in turn to find the total weights. Build a map of node name to
;; node with total subtree weight.

(def- select-vals
  (comp vals select-keys))

(defn- sorted-map-with-order [order m]
  (let [idx (apply hash-map (interleave order (range)))]
    (into 
     (sorted-map-by (fn [x y] (compare (idx x) (idx y))))
     (select-keys m order))))

(defn- children-weight [nodes name]
  (let [get-weight (fn [x] (or (x :total-weight) (x :weight)))
        node       (nodes name)
        children   (select-vals nodes (node :children))]
    (apply + (map get-weight children))))

(defn- total-weight [nodes name]
  (+ (get-in nodes [name :weight])
     (children-weight nodes name)))

(defn- weight-and-order-nodes [programs]
  (let [dep-order (topological-sort (dependency-graph programs))
        unordered (into {} (for [n programs] [(n :name) n]))
        ordered   (sorted-map-with-order dep-order unordered)]
    (reduce
     (fn [acc k] (assoc-in acc [k :total-weight] (total-weight acc k)))
     ordered
     (keys ordered))))

;; Given the weighted nodes, find the unbalanced node and rebalance it

(defn- unbalanced? [nodes name]
  (let [node     (nodes name)
        children (select-vals nodes (node :children))]
    (when children
      (apply not= (map :total-weight children)))))

(defn- first-unbalanced-node [nodes]
  (first (filter (fn [name] (unbalanced? nodes name)) (keys nodes))))

(defn- rebalance [nodes name]
  (let [node                        (nodes name)
        children                    (select-vals nodes (node :children))
        [[incorrect] [correct & _]] (->> (group-by :total-weight children)
                                         (vals)
                                         (sort-by count))
        desired-weight              (correct :total-weight)]
    (assoc incorrect :weight (- desired-weight (children-weight nodes (incorrect :name))))))

(defn weight-of-rebalanced-node [programs]
  (let [nodes      (weight-and-order-nodes programs)
        unbalanced (first-unbalanced-node nodes)]
    (get (rebalance nodes unbalanced) :weight)))

(defn solution-part-two []
  (weight-of-rebalanced-node programs))
