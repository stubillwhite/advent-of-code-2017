(ns advent-of-code-2017.day-7-test
  (:require [advent-of-code-2017.day-7 :refer :all]
            [clojure.test :refer :all]
            [clojure.set :as set]))

(deftest parse-line-given-line-then-extracts-fields
  (is (= {:name "parent" :weight 23 :children #{}}                  (parse-line "parent (23)")))
  (is (= {:name "parent" :weight 23 :children #{"child1"}}          (parse-line "parent (23) -> child1")))
  (is (= {:name "parent" :weight 23 :children #{"child1" "child2"}} (parse-line "parent (23) -> child1, child2"))))

(deftest topological-sort-given-acyclic-graph-then-sorted-order
  (let [graph {:a #{:b :d}
               :b #{:c}
               :c #{}
               :d #{:c}}]
    (is (= [:c :b :d :a] (topological-sort graph)))))

(deftest topological-sort-given-cyclic-graph-then-throws
  (let [graph {:a #{:b :c}
               :b #{:c}
               :c #{:a}}]
    (is (thrown-with-msg? RuntimeException #"Graph contains cycles or is incomplete" (topological-sort graph)))))

(deftest topological-sort-given-incomplete-graph-then-throws
  (let [graph {:a #{:b :c}
               :c #{}}]
    (is (thrown-with-msg? RuntimeException #"Graph contains cycles or is incomplete" (topological-sort graph)))))

(def example-lines
  ["pbga (66)"
   "xhth (57)"
   "ebii (61)"
   "havc (66)"
   "ktlj (57)"
   "fwft (72) -> ktlj, cntj, xhth"
   "qoyq (66)"
   "padx (45) -> pbga, havc, qoyq"
   "tknk (41) -> ugml, padx, fwft"
   "jptl (61)"
   "ugml (68) -> gyxo, ebii, jptl"
   "gyxo (61)"
   "cntj (57)"])

(def example-programs
  (->> example-lines
       (map parse-line)))

(deftest root-program-given-example-input-then-example-result
  (is (= "tknk" (root-program example-programs))))
