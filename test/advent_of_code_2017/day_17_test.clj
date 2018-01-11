(ns advent-of-code-2017.day-17-test
  (:require [advent-of-code-2017.day-17 :refer :all]
            [advent-of-code-2017.utils :refer [def-]]
            [clojure.test :refer :all]))

(deftest spinlock-given-example-input-then-example-result
  (is (= [0 1]     (spinlock 3 1)))
  (is (= [0 2 1]   (spinlock 3 2)))
  (is (= [0 2 3 1] (spinlock 3 3))))

(deftest value-after-spinlock-given-example-input-then-example-result
  (is (= 638 (value-after-spinlock 3 2017))))
