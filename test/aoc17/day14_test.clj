(ns aoc17.day14-test
  (:require [aoc17.day14 :refer :all]
            [clojure.test :refer :all]))

(deftest day14-test
  (testing "testing day 14"
    (is (= (hex-to-bin-bydigit "1") "0001"))
    (is (= (hex-to-bin-bydigit "d4f76bdcbf838f8416ccfa8bc6d1f9e6" )
           "11010100111101110110101111011100101111111000001110001111100001000001011011001100111110101000101111000110110100011111100111100110" ))
    (is (= (solve "flqrgnkx") 8108))
    (is (= (solve2 "flqrgnkx") 1242))))
