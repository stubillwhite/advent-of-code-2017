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

(deftest judge-n-values-given-example-generators-then-example-result
  (let [gen-a (generator-a generator-a-example-seed)
        gen-b (generator-b generator-b-example-seed)]
    (is (= 1   (judge-n-values gen-a gen-b 3)))
    (is (= 588 (judge-n-values gen-a gen-b (* 40 1000 1000))))))

(deftest picky-generator-given-example-input-then-example-values
  (let [generator-a-expected [1352636452 1992081072 530830436 1980017072 740335192]
        generator-b-expected [1233683848 862516352 1159784568 1616057672 412269392]]
    (is (= generator-a-expected (take 5 (picky-generator-a generator-a-example-seed))))
    (is (= generator-b-expected (take 5 (picky-generator-b generator-b-example-seed))))))

(deftest judge-n-values-given-example-picky-generators-then-example-result
  (let [gen-a (picky-generator-a generator-a-example-seed)
        gen-b (picky-generator-b generator-b-example-seed)]
    (is (= 1   (judge-n-values gen-a gen-b 1056)))
    (is (= 309 (judge-n-values gen-a gen-b (* 5 1000 1000))))))
