(ns aoc17.day24-test
  (:require [aoc17.day24 :refer :all ]
            [clojure.test :as t]))


(t/deftest day24-test-part1
  (t/testing "testing day 24, part 1"
    (t/is (= (solve "resources/day24_testdata.txt" select-best-bridge) 31))))

(t/deftest day24-test-part2
  (t/testing "testing day 24, part 2"
    (t/is (= (solve "resources/day24_testdata.txt" select-best-bridge2) 19))))
