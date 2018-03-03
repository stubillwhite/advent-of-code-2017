(ns advent-of-code-2017.day-18-test
  (:require [advent-of-code-2017.day-18 :refer :all]
            [advent-of-code-2017.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

;; snd X plays a sound with a frequency equal to the value of X.
;; set X Y sets register X to the value of Y.
;; add X Y increases register X by the value of Y.
;; mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
;; mod X Y sets register X to the remainder of dividing the value contained in register X by the value of Y (that is, it sets X to the result of X modulo Y).
;; rcv X recovers the frequency of the last sound played, but only when the value of X is not zero. (If it is zero, the command does nothing.)
;; jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero. (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)

(deftest execute-instruction-given-snd-then-updates-snd-register
  (let [test-cpu {:registers {"a" 1} :pc 2 :snd nil}]
    (is (= {:registers {"a" 1} :pc 3 :snd 23} (execute-instruction test-cpu ["snd" "23"])))
    (is (= {:registers {"a" 1} :pc 3 :snd 1}  (execute-instruction test-cpu ["snd" "a"])))))

(deftest execute-instruction-given-set-then-sets-register
  (let [test-cpu {:registers {"a" nil "b" 1} :pc 2}]
    (is (= {:registers {"a" 23 "b" 1} :pc 3} (execute-instruction test-cpu ["set" "a" "23"])))))

(deftest execute-instruction-given-add-then-adds-to-register
  (let [test-cpu {:registers {"a" 5} :pc 2}]
    (is (= {:registers {"a" 7}  :pc 3}  (execute-instruction test-cpu ["add" "a" "2"])))
    (is (= {:registers {"a" 10} :pc 3} (execute-instruction test-cpu ["add" "a" "a"])))))

(deftest execute-instruction-given-mul-then-multiplies-register
  (let [test-cpu {:registers {"a" 5} :pc 2}]
    (is (= {:registers {"a" 10} :pc 3} (execute-instruction test-cpu ["mul" "a" "2"])))
    (is (= {:registers {"a" 25} :pc 3} (execute-instruction test-cpu ["mul" "a" "a"])))))

(deftest execute-instruction-given-mod-then-modulus-register
  (let [test-cpu {:registers {"a" 5} :pc 2}]
    (is (= {:registers {"a" 2} :pc 3} (execute-instruction test-cpu ["mod" "a" "3"])))
    (is (= {:registers {"a" 0} :pc 3} (execute-instruction test-cpu ["mod" "a" "a"])))))

(deftest execute-instruction-given-zero-rcv-then-does-not-update-last-sound
  (let [test-cpu {:registers {"a" 0} :last-snd nil :snd 2 :pc 3}
        no-op    (assoc test-cpu :pc 4)]
    (is (= no-op (execute-instruction test-cpu ["rcv" "0"])))
    (is (= no-op (execute-instruction test-cpu ["rcv" "a"])))))

(deftest execute-instruction-given-zero-rcv-then-updates-last-sound
  (let [test-cpu {:registers {"a" 1} :last-snd nil :snd 2 :pc 3}
        expected {:registers {"a" 1} :last-snd 2   :snd 2 :pc 4}]
    (is (= expected (execute-instruction test-cpu ["rcv" "1"])))
    (is (= expected (execute-instruction test-cpu ["rcv" "a"])))))

(deftest execute-instruction-given-negative-jgz-then-does-not-update-pc
  (let [test-cpu {:registers {"a" -3} :pc 3}
        no-op    (assoc test-cpu :pc 4)]
    (is (= no-op (execute-instruction test-cpu ["jgz" "-2" "5"])))
    (is (= no-op (execute-instruction test-cpu ["jgz" "a"  "5"])))))

(deftest execute-instruction-given-positive-rcv-then-updates-pc
  (let [test-cpu {:registers {"a" 1 "b" 4} :pc 3}]
    (is (= (assoc test-cpu :pc 9) (execute-instruction test-cpu ["jgz" "4" "6"])))
    (is (= (assoc test-cpu :pc 7) (execute-instruction test-cpu ["jgz" "a" "b"])))))

(def- example-program
  ["set a 1"
   "add a 2"
   "mul a a"
   "mod a 5"
   "snd a"
   "set a 0"
   "rcv a"
   "jgz a -1"
   "set a 1"
   "jgz a -2"])

(deftest execute-program-given-example-program-then-example-end-state
  (let [test-cpu {:registers {"a" 1 "b" 4} :pc 3}]
    (is (= 4 (:last-snd (execute-program (parse-program example-program) received-non-zero?))))))

