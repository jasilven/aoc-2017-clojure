(ns aoc17.day20-test
  (:require [aoc17.day20 :refer :all]
            [clojure.test :refer :all]))

(deftest day20-test
  (testing "testing day 20"
    (is (= (solve "resources/day20_testdata.txt") 0))))
