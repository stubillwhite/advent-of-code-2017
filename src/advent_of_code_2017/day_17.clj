(ns advent-of-code-2017.day-17)

(defn spinlock [period limit]
  (letfn [(spinlock-iter [xs len pos n]
            (if (> n limit)
              xs
              (let [split-point    (mod (+ pos period) len)
                    [before after] (split-at (inc split-point) xs)
                    new-xs         (into [] (concat before [n] after))]
                (recur new-xs (inc len) (inc split-point) (inc n)))))]
    (spinlock-iter [0] 1 0 1)))

(defn- first-index-of [x xs]
  (first (keep-indexed #(when (= %2 x) %1) xs)))

(defn value-after-spinlock [xs value]
  (nth xs (inc (first-index-of value xs))))

(defn solution-part-one []
  (value-after-spinlock (spinlock 329 2017) 2017))

;; Part two

;; Again, the naive version isn't going to cut it in execution time. Profiling with tufte shows that most of the time is
;; in the split-at/concat section, so that needs to be faster. I think it's probably faster to just add to the head of
;; the sequence and rotate it repeatedly, so let's try that first.
;;
;; That gets us about 5 times faster. Still not fast enough. Adding type hints doesn't help much either.
;;
;; Ugh. I'm being dumb. We only need the value after zero, which is the first value we add, so we just need to keep
;; track of the second cell.

(defn- value-at-second-pos [period limit]
  (letfn [(value-at-second-pos-iter [curr-val len pos n]
            (if (> n limit)
              curr-val
              (let [split-point (mod (+ pos period) len)
                    new-val     (if (zero? split-point) n curr-val)]
                (recur new-val (inc len) (inc split-point) (inc n)))))]
    (value-at-second-pos-iter [0] 1 0 1)))

(defn solution-part-two []
  (value-at-second-pos 329 50000000))

