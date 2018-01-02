(ns advent-of-code-2017.day-14
  (:require [advent-of-code-2017.day-10 :as day-10]
            [advent-of-code-2017.utils :refer [def-]]
            [clojure.set :as set]))

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

;; Part two

;; Convert to a map and flood-fill each used square

(defn index-grid [grid]
  (into {}
        (for [x (range (.length (first grid)))
              y (range (count grid))]
          [[x y] (nth (nth grid y) x)])))

(defn index-binary-grid [grid]
  (->> (index-grid grid)
       (filter (fn [[k v]] (= v \1)))
       (into {})))

(defn- neighbours [points]
  (into #{}
        (for [[x y]   points
              [dx dy] [[0 1] [-1 0] [1 0] [0 -1]]]
          [(+ x dx) (+ y dy)])))

(defn- drop-keys [map ks]
  (reduce dissoc map ks))

(defn- flood-fill [points grid]
  (let [new-points (into #{} (keys (select-keys grid (neighbours points))))]
    (if (empty? new-points)
      points
      (flood-fill (set/union points new-points)
                  (drop-keys grid new-points)))))

(defn connected-groups [grid]
  (letfn [(groups-iter [groups grid]
            (if (empty? grid)
              groups
              (let [new-group (flood-fill #{(first (keys grid))} grid)]
                (recur (conj groups new-group)
                       (drop-keys grid new-group)))))]
    (groups-iter #{} grid)))

(defn count-of-connected-groups [key-string]
  (->> (binary-grid key-string)
       (index-binary-grid)
       (connected-groups)
       (count)))

(defn solution-part-two []
  (count-of-connected-groups key-string))


