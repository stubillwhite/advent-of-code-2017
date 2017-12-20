(ns advent-of-code-2017.day-14
  (:require [advent-of-code-2017.day-10 :as day-10]
            [advent-of-code-2017.utils :refer [def-]]))

(def key-string "wenycdww")

(defn- pad-left [ch width s]
  (str (apply str (repeat (- width (count s)) ch)) s))

(defn- hex-to-binary [s]
  (letfn [(from-hex  [s] (Integer/parseInt (str s) 16))
          (to-binary [x] (pad-left "0" 4 (Integer/toBinaryString x)))]
    (->> (seq s)
         (map (comp to-binary from-hex))
         (apply str))))

(defn binary-grid [key-string]
  (let [hashes (for [x (range 128)] (day-10/knot-hash (str key-string "-" x)))]
    (map hex-to-binary hashes)))

(defn count-used-squares [grid]
  (->> (seq (apply concat grid))
       (filter (fn [x] (= x \1)))
       (count)))

(defn solution-part-one []
  (count-used-squares (binary-grid key-string)))
