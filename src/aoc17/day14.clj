(ns aoc17.day14
  (:require [aoc17.day10 :as day10]
            [clojure.string :as str]))

(defn hex-to-bin2 [hex]
  (as-> hex $
    (str "16r" $)
    (read-string $)
    (biginteger $)
    (.toString $ 2)))

(defn pad-zeros [bstr]
  (cond
    (= 1 (count bstr)) (str "000" bstr)
    (= 2 (count bstr)) (str "00" bstr)
    (= 3 (count bstr)) (str "0" bstr)
    :else bstr))

(defn hex-to-bin-bydigit [hex]
  (->> (for [c hex]
         (as-> c $
           (str "16r" $)
           (read-string $)
           (int $)
           (Integer/toBinaryString $)
           (pad-zeros $)))
       (apply str)))

(defn solve [input]
  (->> (for [row (range 128)]
         (->> (hex-to-bin-bydigit (day10/solve2 (str input "-" row)))
              (map #(Integer/parseInt (str %)))
              (reduce +)))
       (reduce +)))

(defn get-north [row col grid]
  (if (= row 0) nil (get-in grid[(dec row) col])))

(defn get-west [row col grid]
  (if (< col (- (count (first grid)) 1))
    (get-in grid [row (inc col)])
    nil))

(defn get-south [row col grid]
  (if (< row (- (count grid) 1))
    (get-in grid [(inc row) col])))

(defn get-east [row col grid]
  (if (= col 0) nil (get-in grid [row (dec col)])))

(defn update-grid [row col grid]
  (if (nil? (get-in grid [row col])) grid))

(defn create-grid [input]
  (->> (for [row (range 128)]
         (->> (hex-to-bin-bydigit (day10/solve2 (str input "-" row)))
              (map #(Integer/parseInt (str %)))
              (map #(if (= 0 %) nil 0))
              (apply vector)))
       (apply vector)))

(defn get-min-nwse [row col grid]
  (->> [(get-north row col grid)
        (get-west row col grid)
        (get-south row col grid)
        (get-east row col grid)]
       (filter some?)
       (reduce min (get-in grid [row col]))))

(defn init-groups [grid]
  (->> (for [row (range (count grid))]
         (->> (for [col (range (count (first grid)))]
                (if (= 0 (get-in grid [row col]))
                  (+ (* row (count (first grid))) col 1)))
              (apply vector)))
       (apply vector)))

(defn update-groups [grid]
  (->> (for [row (range (count grid))]
         (->> (for [col (range (count (first grid)))]
                (if (some? (get-in grid [row col]))
                  (get-min-nwse row col grid)))
              (apply vector)))
       (apply vector)))

(defn calc-result [grid]
  (->> grid 
       flatten
       (filter some?)
       frequencies
       keys
       count))

(defn solve2 [input]
  (loop [grid (init-groups (create-grid input))
         prev-grid nil]
    (if (= grid prev-grid) (calc-result grid)
        (recur (update-groups grid)
               grid))))

(comment 
  (solve "uugsqrei")
  ;; correct answer is 8194
  ;; "Elapsed time: 13477.978623 msecs"

  (solve2 "uugsqrei")
  ;; correct answer is 1141
  ;; "Elapsed time: 14571.006343 msecs"
  )
