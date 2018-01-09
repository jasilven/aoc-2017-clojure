(ns aoc17.day4-test
  (:require [aoc17.day4 :refer :all]
            [clojure.test :refer :all]))

(deftest day4-test-part1
  (testing "testing day 4, part 1"
    (is (= (solve "resources/day4_testdata.csv") 5))))

(deftest day4-test-part2
  (testing "testing day 4, part 2"
    (is (= (solve2 "resources/day4_testdata2.csv") 3))))
