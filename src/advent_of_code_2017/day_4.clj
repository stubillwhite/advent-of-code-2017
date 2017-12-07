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

(defn- all-unique? [coll]
  (->> coll
       (group-by identity)
       (vals)
       (every? #(= (count %) 1))))

(defn no-duplicate-words-passphrase? [s]
  (->> s
       (split-space)
       (all-unique?)))

(defn solution-part-one []
  (->> passphrases
       (filter no-duplicate-words-passphrase?)
       (count)))

;; Part two

(defn no-anagrams-passphrase? [s]
  (->> s
       (split-space)
       (map #(-> % (seq) (sort) (str)))
       (all-unique?)))

(defn solution-part-two []
  (->> passphrases
       (filter no-anagrams-passphrase?)
       (count)))
