(ns aoc17.day2-test
  (:require [aoc17.day2 :refer :all]
            [clojure.test :refer :all]))

(deftest day2-test-part1
  (testing "testing day 2, part 1"
    (is (= (solve "resources/day2_testdata.csv") 20))))

(deftest day2-test-part2
  (testing "testing day 2, part 2"
    (is (= (solve2 "resources/day2_testdata2.csv") 9))))
