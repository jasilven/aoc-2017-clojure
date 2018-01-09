(ns aoc17.day3-test
  (:require [aoc17.day3 :refer :all]
            [clojure.test :refer :all]))

(deftest day3-test-part1
  (testing "testing day 3, part 1"
    (is (= (solve 1) 0))
    (is (= (solve 12) 3))
    (is (= (solve 23) 2))
    (is (= (solve 1024) 31))))

(deftest day3-test-part2
  (testing "testing day 3, part 2"
    (is (= (solve2 2) 4))
    (is (= (solve2 4) 5))
    (is (= (solve2 10) 11))
    (is (= (solve2 11) 23))
    (is (= (solve2 147) 304))
    (is (= (solve2 351) 362))
    (is (= (solve2 747) 806))))
