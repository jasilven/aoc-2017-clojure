(ns aoc17.day22-test
  (:require [aoc17.day22 :refer :all]
            [clojure.test :refer :all]))

(deftest day22-test
  (testing "testing day 22, data loading"
    (is (= (load-data "resources/day22_testdata.txt" )
           {:current [0 0],
            :dir :up,
            :inf-cnt 0,
            :infected #{[1 1] [0 -1]}})))
  (testing "testing day 22, solve testcases"
    (is (= (solve "resources/day22_testdata.txt" 7) 5))
    (is (= (solve "resources/day22_testdata.txt" 10000) 5587 )))
    )

(deftest day22-test-part2
  (testing "testing day 22, part2 data loading"
    (is (= (load-data2 "resources/day22_testdata.txt" )
           {:current [0 0],
            :dir :up,
            :inf-cnt 0,
            :weakened #{},
            :flagged #{},
            :infected #{[1 1] [0 -1]}})))
  (testing "testing day 22, part 2 solve testcases"
    (is (= (solve2 "resources/day22_testdata.txt" 100) 26))
    (is (= (solve2 "resources/day22_testdata.txt" 10000000) 2511944 )))
    )
