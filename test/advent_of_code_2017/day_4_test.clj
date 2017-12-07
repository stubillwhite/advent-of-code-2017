(ns advent-of-code-2017.day-4-test
  (:require [advent-of-code-2017.day-4 :refer :all]
            [clojure.test :refer :all]))

(deftest valid-passphrase?-given-expected-input-then-expected-result
  (is (= true  (valid-passphrase? "aa bb cc dd ee")))
  (is (= false (valid-passphrase? "aa bb cc dd aa")))
  (is (= true  (valid-passphrase? "aa bb cc dd aaa"))))
