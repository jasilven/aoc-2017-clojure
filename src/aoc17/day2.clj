(ns aoc17.day2
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(defn read-csv-parse-ints [fname]
  (with-open [reader (io/reader fname)]
    (doall (for [x (csv/read-csv reader)]
             (map #(Integer/parseInt %) x)))))

(defn solve [fname]
  (->> (for [x (read-csv-parse-ints fname)
             :let [y (sort x)]
             :let [nmax (last y)]
             :let [nmin (first y)]]
         (- nmax nmin))
       (reduce +)))

(defn solve2 [fname]
  (-> (->> (for [line (read-csv-parse-ints fname)]
             (for [i (range (count line))
                   j (range (count line))]
               (let [x (nth line i) y (nth line j)]
                 (cond
                   (or (= x 0) (= y 0)) 0
                   (= x y) 0
                   (= 0 (mod (max x y) (min x y))) (/ (max x y) (min x y))
                   :else 0)))) 
           (flatten)
           (reduce +))
      (/ 2)))

(comment 
  (solve "resources/day2_input.csv")
  ;; correct answer is 58975
  ;; "Elapsed time: 21.232938 msecs"

  (solve2 "resources/day2_input.csv"))
  ;; correct answer is 308
  ;; "Elapsed time: 40.912055 msecs"
