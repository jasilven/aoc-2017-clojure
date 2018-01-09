(ns aoc17.day9-test
  (:require [aoc17.day9 :refer :all]
            [clojure.test :refer :all]))

(deftest day9-test-part1
  (testing "testing day 9, part 1"
    (is (= (solve "{}") 1))
    (is (= (solve "{{{}}}") 6))
    (is (= (solve "{{},{}}") 5))
    (is (= (solve "{{{},{},{{}}}}") 16))
    (is (= (solve "{<a>,<a>,<a>,<a>}") 1))
    (is (= (solve "{{<ab>},{<ab>},{<ab>},{<ab>}}") 9))
    (is (= (solve "{{<!!>},{<!!>},{<!!>},{<!!>}}") 9))
    (is (= (solve "{{<a!>},{<a!>},{<a!>},{<ab>}}") 3))))

(deftest day9-test-part2
  (testing "testing day 9, part 2"
    (is (= (solve2 "<>") 0))
    (is (= (solve2 "<random characters>") 17))
    (is (= (solve2 "<<<<>") 3))
    (is (= (solve2 "<{!>}>") 2))
    (is (= (solve2 "<!!>") 0))
    (is (= (solve2 "<!!!>>") 0))
    (is (= (solve2 "<{o\"i!a,<{i<a>") 10))))
