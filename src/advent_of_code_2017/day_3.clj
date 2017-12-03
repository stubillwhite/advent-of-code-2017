(ns advent-of-code-2017.day-3)

;; Hmm. This one has me a little stumped. I can't see an easier way than just walking the spiral, keeping track of position.
;;
;; 37  36  35  34  33  32  31
;; 38  17  16  15  14  13  30
;; 39  18   5   4   3  12  29
;; 40  19   6   1   2  11  28
;; 41  20   7   8   9  10  27
;; 42  21  22  23  24  25  26
;; 43  44  45  46  47---> ...
;;
;; Unrolling the spiral, it looks like we're just moving anticlockwise, incrementing the side length every other time we turn (so, when we turn left or right).
;;
;; Side:   [1] [2] [3] [4 5] [6 7] [8 9 10] [11 12 13] [14 51 16 17] [18 19 20 21] [22 23 24 25 26] [27 28 29 30 31] [32 33 34 35 36 37] [38 39 40 41 42 43] [44 45 46 47 48 49 50] ...
;; Length:  0   1   1    2     2      3          3           4             4               5                5                 6                   6                     7           ...
;; Steps:   -  R1  U1   L2    D2     R3         U3          L4            D5              R5               U5                L6                  D6                    R7           ...
;;
;; So we just need a little statemachine that walks the stream, keeping track of current position, side length, and steps to the end of the side.

(defn- rotate-anticlockwise [{:keys [facing side-length steps-left] :as state}]
  (let [rotations       {:down  :right
                         :right :up
                         :up    :left
                         :left  :down}
        new-facing      (get rotations facing)
        new-side-length (if (contains? #{:left :right} new-facing) (inc side-length) side-length)]
    (-> state
        (assoc :facing      new-facing)
        (assoc :side-length new-side-length)
        (assoc :steps-left  new-side-length))))

(defn- new-location [facing [x y]]
  (let [deltas   {:down  [ 0 -1]
                  :right [ 1  0]
                  :up    [ 0  1]
                  :left  [-1  0]}
        [dx dy] (get deltas facing)]
       [(+ x dx) (+ y dy)]))

(defn- move-along-side [{:keys [facing] :as state}]
  (-> state
      (update :curr     inc)
      (update :location (partial new-location facing))
      (update :steps-left dec)))

(defn- move [{:keys [steps-left] :as state}]
  (if (zero? steps-left)
    (rotate-anticlockwise state)
    (move-along-side state)))

(defn- coordinates-of-value [n {:keys [curr] :as state}]
  (if (= n curr)
    (state :location)
    (recur n (move state))))

(defn distance-to-origin [n]
  (if (= n 1)
    0
    (let [[x y] (coordinates-of-value n {:curr        1
                                         :location    [0 0]
                                         :facing      :right
                                         :side-length 1
                                         :steps-left  1})]
      (+ (Math/abs x) (Math/abs y)))))

(defn solution-part-one []
  (distance-to-origin 361527))

