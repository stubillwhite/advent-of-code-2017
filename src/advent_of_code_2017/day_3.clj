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
;; Steps:   -  R1  U1   L2    D2     R3         U3          L4            D4              R5               U5                L6                  D6                    R7           ...
;;
;; So we just need a little statemachine that walks the stream, keeping track of current position, side length, and steps to the end of the side.
;;
;; EDIT: Now that I've done all that, I've noticed that the bottom right corner is the sequence 1, 9, 25, 49, ..., which is the squares of odd numbers, so there was probably a
;;       neat optimisation or smarter way to do this. Oh, well... :)

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
      (update :coordinates (partial new-location facing))
      (update :steps-left  dec)))

(defn- move [{:keys [steps-left] :as state}]
  (if (zero? steps-left)
    (-> state (rotate-anticlockwise) (move-along-side))
    (-> state (move-along-side))))

(defn- update-accumulator [{:keys [f-next coordinates] :as state}]
  (update-in state [:acc] (fn [acc] (f-next acc coordinates))))

(defn- navigate-spiral [acc f-next f-done]
  (letfn [(navigate-spiral-iter [{:keys [f-done] :as state}]
            (if (f-done state)
              state
              (recur (-> state (move) (update-accumulator)))))]
    (navigate-spiral-iter {:acc         acc
                           :f-next      f-next
                           :f-done      f-done
                           :coordinates [0 0]
                           :facing      :right
                           :side-length 1
                           :steps-left  1})))

(defn- current-value-equals? [n]
  (fn [{:keys [acc]}] (= n acc)))

(defn- increment-count [acc coordinates]
  (inc acc))

(defn distance-to-origin [n]
  (let [end-state (navigate-spiral 1 increment-count (current-value-equals? n))
        [x y]     (end-state :coordinates)]
    (+ (Math/abs x) (Math/abs y))))

(defn solution-part-one []
  (distance-to-origin 361527))

;; I can't see any other way to solve part two apart from brute force, unless I'm missing something. Also, refactoring
;; the first solution so I can reuse navigation in the second part has made a bit of a mess. Not sure how best to
;; structure this to be reusable between both parts.

(defn greater-than? [n]
  (fn [{:keys [acc coordinates]}] (> (acc coordinates) n)))

(defn- neighbours [[x y]]
  (let [deltas [[-1  1] [0  1] [1  1]
                [-1  0]        [1  0]
                [-1 -1] [0 -1] [1 -1]]]
    (map (fn [[dx dy]] [(+ x dx) (+ y dy)]) deltas)))

(defn- sum-neighbours [acc coordinates]
  (let [curr-value (->> (neighbours coordinates)
                        (map (fn [x] (or (acc x) 0)))
                        (apply +))]
    (assoc acc coordinates curr-value)))

(defn value-greater-than [n]
  (let [final-state               (navigate-spiral {[0 0] 1} sum-neighbours (greater-than? n))
        {:keys [acc coordinates]} final-state]
    (acc coordinates)))

(defn solution-part-two []
  (value-greater-than 361527))

