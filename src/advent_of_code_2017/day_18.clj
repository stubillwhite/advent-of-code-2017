(ns advent-of-code-2017.day-18
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- split-newline [s]
   (string/split s #"\n"))
   
(def- program
  (->> (io/resource "day-18-input.txt")
       (slurp)
       (string/trim)
       (split-newline)))

;; snd X plays a sound with a frequency equal to the value of X.
;; set X Y sets register X to the value of Y.
;; add X Y increases register X by the value of Y.
;; mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
;; mod X Y sets register X to the remainder of dividing the value contained in register X by the value of Y (that is, it sets X to the result of X modulo Y).
;; rcv X recovers the frequency of the last sound played, but only when the value of X is not zero. (If it is zero, the command does nothing.)
;; jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero. (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)

(def registers
  (into {} (for [ch (range (int \a) (inc (int \z)))] [(str (char ch)) 0])))

(def cpu
  {:registers registers
   :pc        0
   :snd       nil
   :last-snd  nil})

(defn- inc-pc      [cpu]       (update cpu :pc inc))
(defn- execute-snd [cpu [x]]   (assoc cpu :snd x))
(defn- execute-set [cpu [x y]] (assoc-in cpu [:registers x] y))
(defn- execute-add [cpu [x y]] (update-in cpu [:registers x] (partial + y)))
(defn- execute-mul [cpu [x y]] (update-in cpu [:registers x] (partial * y)))
(defn- execute-mod [cpu [x y]] (update-in cpu [:registers x] #(mod % y)))
(defn- execute-rcv [cpu [x]]   (if (zero? x) cpu (assoc cpu :last-snd (cpu :snd))))
(defn- execute-jgz [cpu [x y]] (if (<= x 0) (inc-pc cpu) (update cpu :pc (partial + y))))

(defn- value-of [cpu x]
  (if-let [value (get-in cpu [:registers x])]
    value
    (Integer/parseInt x)))

(defn execute-instruction [cpu [instr a b]]
  (case instr
    "snd" (-> cpu (execute-snd [(value-of cpu a)])   (inc-pc))
    "set" (-> cpu (execute-set [a (value-of cpu b)]) (inc-pc))
    "add" (-> cpu (execute-add [a (value-of cpu b)]) (inc-pc))
    "mul" (-> cpu (execute-mul [a (value-of cpu b)]) (inc-pc))
    "mod" (-> cpu (execute-mod [a (value-of cpu b)]) (inc-pc))
    "rcv" (-> cpu (execute-rcv [(value-of cpu a)])   (inc-pc))
    "jgz" (-> cpu (execute-jgz [(value-of cpu a) (value-of cpu b)]))))

(defn parse-program [prg]
  (into []
        (map (fn [s] (string/split s #" ")) prg)))

;; The example program does not terminate, so we can't just run until the program ends and then inspect the state. Allow
;; termination conditions to be supplied.

(defn execute-program [prg terminate?]
  (letfn [(pc-invalid? [{:keys [pc]}]
            (or (neg? pc) (> pc (count prg))))

          (execute-program-iter [{:keys [registers pc curr-freq last-freq] :as cpu}]
            (if (or (terminate? cpu) (pc-invalid? cpu))
              cpu
              (-> cpu
                  (execute-instruction (nth prg pc))
                  (recur))))]

    (execute-program-iter cpu)))

(defn received-non-zero? [{:keys [last-snd]}]
  (not (nil? last-snd)))

(defn solution-part-one []
  (:last-snd (execute-program (parse-program program) received-non-zero?)))
