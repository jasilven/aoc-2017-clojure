(ns aoc17.day19-test
  (:require [aoc17.day19 :refer :all]
            [clojure.test :refer :all]))

(deftest day19-test
  (testing "testing day 19"
    (is (= (solve "resources/day19_testdata.txt") "ABCDEF"))))

(deftest day19-test-part2
  (testing "testing day 19, part2"
    (is (= (solve2 "resources/day19_testdata.txt") 38))))
