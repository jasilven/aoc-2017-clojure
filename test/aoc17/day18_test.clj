(ns aoc17.day18-test
  (:require [aoc17.day18 :refer :all]
            [clojure.test :refer :all]))

(deftest day18-test
  (testing "testing day 18"
    (is (= (solve "resources/day18_testdata.txt") 4))))

(deftest day18-test-part2
  (testing "testing day 18, part2"
    (is (= (solve2 "resources/day18_testdata2.txt") 3))))
