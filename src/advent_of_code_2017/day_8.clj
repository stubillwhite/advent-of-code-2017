(ns advent-of-code-2017.day-8
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- split-newline [s]
  (string/split s #"\n"))

(def- lines
  (->> (io/resource "day-8-input.txt")
       (slurp)
       (string/trim)
       (split-newline)))

(defn- operator [s]
  (let [operators {"inc" +
                   "dec" -}]
    (operators s)))

(defn- conditional [s]
  (let [conditionals {"<"  <
                      "<=" <=
                      "==" =
                      "!=" not=
                      ">=" >=
                      ">" >}]
    (conditionals s)))

(defn- parse-line [s]
  (let [pattern #"^(\S+) (inc|dec) ([-0-9]+) if (\S+) (<|<=|==|!=|>=|>) ([-0-9]+)$"
        [_ op-reg op op-arg cnd-reg cnd cnd-arg] (re-find pattern s)]
    {:op-reg op-reg
     :op      (operator op)
     :op-arg  (Integer/parseInt op-arg)
     :cnd-reg cnd-reg
     :cnd     (conditional cnd)
     :cnd-arg (Integer/parseInt cnd-arg)}))

(defn- update-max-value [regs]
  (let [max-value (apply max (vals regs))]
    (assoc regs :max-value max-value)))

(defn- execute [instructions]
  (reduce (fn [regs {:keys [op-reg op op-arg cnd-reg cnd cnd-arg]}]
            (if (cnd (or (regs cnd-reg) 0) cnd-arg)
              (-> regs
                  (update op-reg (fn [x] (op (or (regs op-reg) 0) op-arg)))
                  (update-max-value))
              regs))
          {}
          instructions))

(defn parse-and-execute-instructions [lines]
  (->> lines
       (map parse-line)
       (execute)))

(defn solution-part-one []
  (->> (parse-and-execute-instructions lines)
       (vals)
       (apply max)))

;; Part two

;; Just a small refactor to keep track of the highest value. This needs to be done after the registers have been
;; updated, in case the largest value is hit on the final operation.

(defn solution-part-two []
  (-> (parse-and-execute-instructions lines)
      (:max-value)))
