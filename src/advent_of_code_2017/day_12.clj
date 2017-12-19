(ns advent-of-code-2017.day-12
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :as set]))

(defn- split-newline [s]
   (string/split s #"\n"))
   
(def- programs
  (->> (io/resource "day-12-input.txt")
       (slurp)
       (string/trim)
       (split-newline)))

(defn- parse-line [s]
  (let [to-int             (fn [x] (Integer/parseInt x))
        pattern            #"^(\d+) <-> (.+)$"
        [_ node nodes-csv] (re-find pattern s)]
    [(to-int node)
     (into #{} (map to-int (string/split nodes-csv #", ")))]))

(defn construct-graph [lines]
  (into {} (map parse-line lines)))

(defn connected-to [graph node]
  (letfn [(connected-to-iter [group conns]
            (if (empty? conns)
              group
              (let [new-group (set/union group conns)
                    new-conns (reduce set/union #{} (vals (select-keys graph conns)))]
                (recur new-group (set/difference new-conns new-group)))))]
    (connected-to-iter #{node} (graph node))))

(defn solution-part-one []
  (count (connected-to (construct-graph programs) 0)))

;; Part two

;; Just run over the graph pulling out groups and removing each from the graph until empty

(defn groups [graph]
  (letfn [(groups-iter [graph groups]
            (if (empty? graph)
              groups
              (let [new-group (connected-to graph (first (keys graph)))]
                (recur (apply dissoc graph new-group)
                       (conj groups new-group)))))]
    (groups-iter graph #{})))

(defn solution-part-two []
  (count (groups (construct-graph programs))))
