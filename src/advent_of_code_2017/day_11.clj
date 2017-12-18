(ns advent-of-code-2017.day-11
  (:require [advent-of-code-2017.utils :refer [def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

;; Hmm. Haven't used hex grids before. I'm sure there are lots of algorithms for handling this, but I deliberately want
;; to just see if I can figure something out. I think I can just treat this as a normal grid with diagonals being a
;; half-step. Rather than deal with half-steps, I'll use a double-step for vertical movement, a single step for
;; diagonals, and halve the final distance.

;; Grid for reference
;;
;;    +----+        +----+        +----+  
;;   /      \      /      \      /      \
;; -+        +----+        +----+        +-
;;   \      /      \      /      \      /  
;;    +----+        +----+        +----+  
;;   /      \      /      \      /      \
;; -+        +----+        +----+        +-
;;   \      /      \      /      \      /  
;;    +----+        +----+        +----+  
;;   /      \      /      \      /      \
;; -+        +----+        +----+        +-
;;   \      /      \      /      \      /  
;;    +----+        +----+        +----+  
  

(defn- parse-directions [s]
   (string/split s #","))
   
(def- child-path
  (->> (io/resource "day-11-input.txt")
       (slurp)
       (string/trim)))

(defn- distance [[x y]]
  (cond
    (or (< x 0)
        (< y 0)) (distance [(Math/abs x) (Math/abs y)])
    (zero? x)    (/ y 2)
    (zero? y)    x
    :else        (inc (distance [(dec x) (dec y)]))))

(defn- new-position [[x y] dir]
  (let [deltas  {"n"  [ 0  2]
                 "ne" [ 1  1]
                 "se" [ 1 -1]
                 "s"  [ 0 -2]
                 "sw" [-1 -1]
                 "nw" [-1  1]}
        [dx dy] (get deltas dir)]
    [(+ x dx) (+ y dy)]))

(defn- follow-directions [dirs]
  (reduce
   (fn [path dir]
     (cons (new-position (first path) dir) path))   
   [[0 0]]
   (parse-directions dirs)))

(defn steps-from-origin [dirs]
  (let [endpoint (first (follow-directions dirs))]
    (distance endpoint)))

(defn solution-part-one []
  (steps-from-origin child-path))

;; Part two

;; Refactor to build a list of positions, then just map distance and max across that

(defn solution-part-two []
  (apply max (map distance (follow-directions child-path))))
