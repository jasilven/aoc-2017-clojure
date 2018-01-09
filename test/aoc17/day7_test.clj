(ns aoc17.day7-test
  (:require  [aoc17.day7 :refer :all]
             [clojure.test :refer :all]))

(deftest day7-test-part1
  (testing "testing day 7, part 1"
    (is (= (solve "resources/day7_testdata.txt") "tknk"))))


(deftest day7-test-part2
  (testing "testing day 7, part 2"
    (is (= (solve2 "resources/day7_testdata.txt") 60))
    (is (= (solve2 "resources/day7_testdata2.txt") 66))))
