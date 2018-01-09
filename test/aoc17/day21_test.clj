(ns aoc17.day21-test
  (:require [aoc17.day21 :refer :all]
            [clojure.test :refer :all]))

(deftest day21-test
  (testing "testing day 21"
    (is (= (to-pattern (to-matrix start-pattern)) start-pattern))))
