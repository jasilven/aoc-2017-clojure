(ns aoc17.day15-test
  (:require [aoc17.day15 :refer :all]
            [clojure.test :refer :all]))

(deftest day15-test
  (testing "testing day 15"
    (is (= (solve 65 8921 5) 1))
    (is (= (generateA2 65) 1352636452))
    (is (= (generateB2 8921) 1233683848))
    (is (= (solve2 65 8921 5000000) 309))))
