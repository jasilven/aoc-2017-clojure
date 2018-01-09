(ns aoc17.day6-test
  (:require [aoc17.day6 :refer :all]
            [clojure.test :refer :all]))


(deftest day6-test-part1-find-largest
  (testing "testing day 6, part 1 (find-largest)"
    (is (= (find-largest [1 3 4 7]) [3 7]))
    (is (= (find-largest [0]) [0 0]))
    (is (= (find-largest [1 1]) [0 1]))
    (is (= (find-largest [9 8 -1 -5]) [0 9]))
    (is (= (find-largest [-1 -3 -6 0]) [3 0]))
    ))

(deftest day6-test-part1-reallocate
  (testing "testing day 6, part 1 (reallocate-banks)"
    (is (= (reallocate-banks [0 2 7 0]) [2 4 1 2]))
    (is (= (reallocate-banks [2 4 1 2]) [3 1 2 3]))
    (is (= (reallocate-banks [3 1 2 3]) [0 2 3 4]))
    (is (= (reallocate-banks [0 2 3 4]) [1 3 4 1]))
    (is (= (reallocate-banks [1 3 4 1]) [2 4 1 2]))
    ))

(deftest day6-test-part1
  (testing "testing day 6, part 1"
    (is (= (solve "resources/day6_testdata.txt") 5))))

(deftest day6-test-part2
  (testing "testing day 6, part 2"
    (is (= (solve2 "resources/day6_testdata.txt") 4))))

