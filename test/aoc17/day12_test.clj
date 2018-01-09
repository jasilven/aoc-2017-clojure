(ns aoc17.day12-test
  (:require [aoc17.day12 :refer :all]
            [clojure.test :refer :all]))

(deftest day12-test-part1
  (testing "testing day 12, part 1"
    (is (= (solve (get-data "resources/day12_testdata.txt")) 6))))

(deftest day12-test-part2
  (testing "testing day 12, part 2"
    (is (= (solve2 (get-data "resources/day12_testdata.txt")) 2))
    (is (= (solve2 (get-data "resources/day12_testdata2.txt")) 2))))

