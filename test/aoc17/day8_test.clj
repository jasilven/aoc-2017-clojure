(ns aoc17.day8-test
  (:require  [aoc17.day8 :refer :all]
             [clojure.test :refer :all]))

(deftest day8-test-part1
  (testing "testing day 8, part 1"
    (is (= (solve "resources/day8_testdata.txt") 1))
    (is (= (solve "resources/day8_testdata2.txt") 1))))

(deftest day8-test-part2
  (testing "testing day 8, part 2"
    (is (= (solve2 "resources/day8_testdata.txt") 10))))
