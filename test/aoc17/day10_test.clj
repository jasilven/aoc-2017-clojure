(ns aoc17.day10-test
  (:require [aoc17.day10 :refer :all]
            [clojure.test :refer :all]))

(deftest day10-test-part1
  (testing "testing day 10, part 1"
    (is (= (solve '(3 4 1 5) '(0 1 2 3 4)) 12))))

(deftest day10-test-part2
  (testing "testing day 10, part 2"
    (is (= (get-lens "1,2,3") '(49 44 50 44 51 17 31 73 47 23)))
    (is (= (knot-hash-dense '(65 27 9 1 4 3 40 50 91 7 6 0 2 5 68 22)) '(64) ))
    (is (= (solve2 "") "a2582a3a0e66e6e86e3812dcb672a272"))
    (is (= (solve2 "AoC 2017") "33efeb34ea91902bb2f59c9920caa6cd"))
    (is (= (solve2 "1,2,3") "3efbe78a8d82f29979031a4aa0b16a9d"))
    (is (= (solve2 "1,2,4") "63960835bcdc130f0b66d7ff4f6a5a8e")) 
    (is (= (hexify '(64 7 255)) "4007ff" ))))
