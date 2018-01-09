(ns aoc17.day16-test
  (:require [aoc17.day16 :refer :all]
            [clojure.test :refer :all]))

(deftest day16-test
  (testing "testing day 16"
    (is (= (solve (get-input "resources/day16_testdata.txt") "abcde") "baedc"))))

(deftest day16-test-part2
  (testing "testing day 16, part2"
    (is (= (find-loop (get-input "resources/day16_testdata.txt") "abcde") 4))
    #_(is (= (solve2 (get-input "resources/day16_testdata.txt") "abcde") "baedc"))))
