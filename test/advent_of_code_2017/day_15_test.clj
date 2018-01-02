(ns advent-of-code-2017.day-15-test
  (:require [advent-of-code-2017.day-15 :refer :all]
            [advent-of-code-2017.utils :refer [def-]]
            [clojure.test :refer :all]))

(def- generator-a-example-seed 65)
(def- generator-b-example-seed 8921)

(deftest generator-given-example-input-then-example-values
  (let [generator-a-expected [1092455 1181022009 245556042 1744312007 1352636452]
        generator-b-expected [430625591 1233683848 1431495498 137874439 285222916]]
    (is (= generator-a-expected (take 5 (generator-a generator-a-example-seed))))
    (is (= generator-b-expected (take 5 (generator-b generator-b-example-seed))))))

(deftest judge-n-values-given-example-input-then-example-result
  (let [gen-a (generator-a generator-a-example-seed)
        gen-b (generator-b generator-b-example-seed)]
    (is (= 1   (judge-n-values gen-a gen-b 3)))
    (is (= 588 (judge-n-values gen-a gen-b (* 40 1000 1000))))))
