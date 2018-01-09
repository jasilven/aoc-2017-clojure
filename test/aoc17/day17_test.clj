(ns aoc17.day17-test
  (:require [aoc17.day17 :refer :all]
            [clojure.test :refer :all]))

(deftest day17-test
  (testing "testing day 17"
    (is (= (solve 3) 638))))

(deftest day17-test-part2
  (testing "testing day 17, part2"
    (is (= (solve2 3 10) 9))))
