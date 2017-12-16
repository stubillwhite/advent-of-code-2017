(ns advent-of-code-2017.day-10-test
  (:require [advent-of-code-2017.day-10 :refer :all]
            [clojure.test :refer :all]))

(deftest apply-to-sublist-then-transformed-sublist
  (let [xs        (range 5)
        stringify #(map str %)]
    (is (= ["0" "1" "2" 3   4 ] (apply-to-sublist stringify xs 0 3)))
    (is (= ["0"  1   2 "3" "4"] (apply-to-sublist stringify xs 3 3)))))

(defn- twist-n-times [xs lengths n]
  (twist xs (take n lengths)))

(def example-string  (range 5))
(def example-lengths [3 4 1 5])

(deftest twist-given-example-input-then-example-result
  (is (= [2 1 0 3 4] (:xs (twist-n-times example-string example-lengths 1))))
  (is (= [4 3 0 1 2] (:xs (twist-n-times example-string example-lengths 2))))
  (is (= [4 3 0 1 2] (:xs (twist-n-times example-string example-lengths 3))))
  (is (= [3 4 2 1 0] (:xs (twist-n-times example-string example-lengths 4)))))



