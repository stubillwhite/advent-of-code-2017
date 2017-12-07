(ns advent-of-code-2017.day-4-test
  (:require [advent-of-code-2017.day-4 :refer :all]
            [clojure.test :refer :all]))

(deftest no-duplicate-words-passphrase?-given-example-input-then-example-result
  (is (= true  (no-duplicate-words-passphrase? "aa bb cc dd ee")))
  (is (= false (no-duplicate-words-passphrase? "aa bb cc dd aa")))
  (is (= true  (no-duplicate-words-passphrase? "aa bb cc dd aaa"))))

(deftest no-anagrams-passphrase?-given-example-input-then-example-result
  (is (= true  (no-anagrams-passphrase? "abcde fghij")))
  (is (= false (no-anagrams-passphrase? "abcde xyz ecdab")))
  (is (= true  (no-anagrams-passphrase? "a ab abc abd abf abj")))
  (is (= true  (no-anagrams-passphrase? "iiii oiii ooii oooi oooo")))
  (is (= false (no-anagrams-passphrase? "oiii ioii iioi iiio"))))
