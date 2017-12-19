(ns advent-of-code-2017.day-12-test
  (:require [advent-of-code-2017.day-12 :refer :all]
            [advent-of-code-2017.utils :refer [def-]]
            [clojure.test :refer :all]))

(def- example-input
  ["0 <-> 2"
   "1 <-> 1"
   "2 <-> 0, 3, 4"
   "3 <-> 2, 4"
   "4 <-> 2, 3, 6"
   "5 <-> 6"
   "6 <-> 4, 5"])

(def- example-graph
  {0 #{2}
   1 #{1}
   2 #{0 3 4}
   3 #{2 4}
   4 #{2 3 6}
   5 #{6}
   6 #{4 5}})

(deftest construct-graph-given-example-input-then-example-graph
  (is (= example-graph (construct-graph example-input))))

(deftest connected-to-given-example-graph-then-connected-nodes
  (let [graph (construct-graph example-input)]
    (is (= #{1}           (connected-to graph 1)))
    (is (= #{0 2 3 4 5 6} (connected-to graph 0)))))

(deftest groups-given-example-input-then-example-output
  (is (= #{#{1} #{0 2 3 4 5 6}} (groups example-graph))))
