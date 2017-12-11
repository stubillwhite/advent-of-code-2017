(ns advent-of-code-2017.day-9-test
  (:require [advent-of-code-2017.day-9 :refer :all]
            [clojure.test :refer :all]))

(deftest total-score-given-example-input-then-example-score
  (is (= 1  (total-score "{}")))
  (is (= 6  (total-score "{{{}}}")))
  (is (= 5  (total-score "{{}{}}")))
  (is (= 16 (total-score "{{{},{},{{}}}}")))
  (is (= 1  (total-score "{<a>,<a>,<a>,<a>}")))
  (is (= 9  (total-score "{{<ab>},{<ab>},{<ab>},{<ab>}}")))
  (is (= 9  (total-score "{{<!!>},{<!!>},{<!!>},{<!!>}}")))
  (is (= 3  (total-score "{{<a!>},{<a!>},{<a!>},{<ab>}}"))))
