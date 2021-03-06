(ns advent-of-code-2017.day-3-test
  (:require [advent-of-code-2017.day-3 :refer :all]
            [clojure.test :refer :all]))

(deftest distance-to-origin-given-example-input-then-example-result
  (is (= 0 (distance-to-origin 1)))
  (is (= 3 (distance-to-origin 12)))
  (is (= 2 (distance-to-origin 23)))
  (is (= 31 (distance-to-origin 1024))))

;; Numbers
;; 
;; 17  16  15  14  13
;; 18   5   4   3  12
;; 19   6   1   2  11
;; 20   7   8   9  10
;; 21  22  23---> ...
;;
;; Manhattan distances
;;
;;  4   3   2   3   4
;;  3   2   1   2   3
;;  2   1   0   1   2
;;  3   2   1   2   3
;;  4   3   2---> ...

(deftest distance-to-origin-given-test-input-then-manhattan-distance
  (is (= 0 (distance-to-origin 1)))
  (is (= 1 (distance-to-origin 2)))
  (is (= 2 (distance-to-origin 3)))
  (is (= 1 (distance-to-origin 4)))
  (is (= 2 (distance-to-origin 5)))
  (is (= 1 (distance-to-origin 6)))
  (is (= 2 (distance-to-origin 7)))
  (is (= 1 (distance-to-origin 8)))
  (is (= 2 (distance-to-origin 9)))
  (is (= 3 (distance-to-origin 10)))
  (is (= 2 (distance-to-origin 11)))
  (is (= 3 (distance-to-origin 12)))
  (is (= 4 (distance-to-origin 13)))
  (is (= 3 (distance-to-origin 14)))
  (is (= 2 (distance-to-origin 15)))
  (is (= 3 (distance-to-origin 16)))
  (is (= 4 (distance-to-origin 17)))
  (is (= 3 (distance-to-origin 18)))
  (is (= 2 (distance-to-origin 19)))
  (is (= 3 (distance-to-origin 20)))
  (is (= 4 (distance-to-origin 21)))
  (is (= 3 (distance-to-origin 22)))
  (is (= 2 (distance-to-origin 23))))

;; 147  142  133  122   59
;; 304    5    4    2   57
;; 330   10    1    1   54
;; 351   11   23   25   26
;; 362  747  806--->   ...

(deftest value-greater-than-example-input-then-example-result
  (is (= 2   (value-greater-than  1)))
  (is (= 4   (value-greater-than  3)))
  (is (= 5   (value-greater-than  4)))
  (is (= 10  (value-greater-than  9)))
  (is (= 11  (value-greater-than 10)))
  (is (= 23  (value-greater-than 22)))
  (is (= 25  (value-greater-than 24))))
