(ns advent-of-code-2017.day-4
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- split-space [s]
  (string/split s #" "))

(defn- split-newline [s]
  (string/split s #"\n"))

(def- passphrases
  (->> (io/resource "day-4-input.txt")
       (slurp)
       (string/trim)
       (split-newline)))

(defn valid-passphrase? [s]
  (->> s
       (split-space)
       (group-by identity)
       (vals)
       (every? #(= (count %) 1))))

(defn solution-part-one []
  (->> passphrases
       (filter valid-passphrase?)
       (count)))
