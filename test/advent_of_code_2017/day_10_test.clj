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

(deftest to-byte-stream-given-example-input-then-example-result
  (is (= [49 44 50 44 51] (to-byte-stream "1,2,3"))))

(deftest dense-hash-given-example-input-then-example-result
  (is (= [64] (dense-hash [65 27 9 1 4 3 40 50 91 7 6 0 2 5 68 22]))))

(deftest to-hexadecimal-string-given-example-input-then-example-result
  (is (= "4007ff" (to-hexadecimal-string [64 7 255]))))

(deftest knot-hash-given-example-input-then-example-result
  (is (= "a2582a3a0e66e6e86e3812dcb672a272" (knot-hash "")))
  (is (= "33efeb34ea91902bb2f59c9920caa6cd" (knot-hash "AoC 2017")))
  (is (= "3efbe78a8d82f29979031a4aa0b16a9d" (knot-hash "1,2,3")))
  (is (= "63960835bcdc130f0b66d7ff4f6a5a8e" (knot-hash "1,2,4"))))
