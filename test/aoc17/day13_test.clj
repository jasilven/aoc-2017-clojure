(ns aoc17.day13-test
  (:require [aoc17.day13 :refer :all]
            [clojure.test :refer :all]))

(deftest day13-test-part1
  (testing "testing day 13, part 1"
    (is (= (solve "resources/day13_testdata.txt") 24))))

(deftest day13-test-part2
  (testing "testing day 13, part 2"
    (is (= (solve2 "resources/day13_testdata.txt") 10))))
