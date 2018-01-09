(ns aoc17.day11-test
  (:require [aoc17.day11 :refer :all]
            [clojure.test :refer :all]))

(deftest day11-test-part1
  (testing "testing day 11, part 1"
    (is (= (solve ["ne" "ne" "ne"]) 3))
    (is (= (solve ["ne" "ne" "sw" "sw"]) 0))
    (is (= (solve ["ne" "ne" "s" "s"]) 2))
    (is (= (solve ["se" "sw" "se" "sw" "sw"]) 3))
    ))

(deftest day11-test-part2
  (testing "testing day 11, part 2"
    (is (= (solve2 ["ne" "ne" "ne"]) 3))
    (is (= (solve2 ["ne" "ne" "sw" "sw"]) 2))
    (is (= (solve2 ["ne" "ne" "s" "s"]) 2))
    (is (= (solve2 ["se" "sw" "se" "sw" "sw"]) 3))
    ))
