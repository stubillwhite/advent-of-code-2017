(ns advent-of-code-2017.day-11-test
  (:require [advent-of-code-2017.day-11 :refer :all]
            [clojure.test :refer :all]))

(deftest steps-from-origin-given-example-input-then-example-output
  (is (= 3 (steps-from-origin "ne,ne,ne")))
  (is (= 0 (steps-from-origin "ne,ne,sw,sw")))
  (is (= 2 (steps-from-origin "ne,ne,s,s")))
  (is (= 3 (steps-from-origin "se,sw,se,sw,sw"))))

;; My first attempt at the distance function had a bug and passed for test input but failed for the problem input. I
;; threw in some tests to flush out the bug and verify that distance was being correctly calculated.

(deftest steps-from-origin-given-zero-moves-away-then-correct-distance
  (is (= 0 (steps-from-origin "n,se,s,sw,nw,n,ne,s"))))

(deftest steps-from-origin-given-vertical-moves-then-correct-distance
  (is (= 3 (steps-from-origin "n,n,n")))
  (is (= 3 (steps-from-origin "s,s,s"))))

(deftest steps-from-origin-given-horizontal-moves-then-correct-distance
  (is (= 6 (steps-from-origin "ne,se,ne,se,ne,se")))
  (is (= 6 (steps-from-origin "nw,sw,nw,sw,nw,sw"))))

(deftest steps-from-origin-given-one-move-away-then-correct-distance
  (is (= 1 (steps-from-origin "n")))
  (is (= 1 (steps-from-origin "ne")))
  (is (= 1 (steps-from-origin "se")))
  (is (= 1 (steps-from-origin "s")))
  (is (= 1 (steps-from-origin "sw")))
  (is (= 1 (steps-from-origin "nw"))))

(deftest steps-from-origin-given-two-moves-away-then-correct-distance
  (is (= 2 (steps-from-origin "n,n")))
  (is (= 2 (steps-from-origin "ne,ne")))
  (is (= 2 (steps-from-origin "ne,se")))
  (is (= 2 (steps-from-origin "se,se")))
  (is (= 2 (steps-from-origin "s,s")))
  (is (= 2 (steps-from-origin "sw,sw")))
  (is (= 2 (steps-from-origin "nw,nw"))))

(deftest steps-from-origin-given-three-moves-away-then-correct-distance
  (is (= 3 (steps-from-origin "n,n,n")))
  (is (= 3 (steps-from-origin "ne,ne,ne")))
  (is (= 3 (steps-from-origin "se,se,se")))
  (is (= 3 (steps-from-origin "s,s,s")))
  (is (= 3 (steps-from-origin "sw,sw,sw")))
  (is (= 3 (steps-from-origin "nw,nw,nw"))))

