(ns aoc17.day5-test
  (:require [aoc17.day5 :refer :all]
            [clojure.test :refer :all]))

(deftest day5-test-part1
  (testing "testing day 5, part 1"
    (is (= (solve "resources/day5_testdata.txt") 5))
    (is (= (solve "resources/day5_testdata_2.txt") 2))))

(deftest day5-test-part2
  (testing "testing day 5, part 2"
    (is (= (solve2 "resources/day5_testdata.txt") 10))))
