(ns aoc17.day5
  (:require [clojure.java.io :as io]))

(defn read-input-file [fname]
  (with-open [reader (io/reader fname)]
    (->> (doall (line-seq reader))
         (map #(Integer/parseInt %))
         (apply vector))))

(defn solve [fname]
  (loop [instructions (read-input-file fname)
         index 0
         steps 0]
    (if (or (>= index (count instructions))
            (< index 0))
      (if (= 0 steps) 1 steps)
      (recur (update instructions index #(inc %))
             (+ index (nth instructions index))
             (inc steps)))))

(defn calc-new-instruction [old]
  (if (>= old 3)
    (dec old)
    (inc old)))

(defn solve2 [fname]
  (loop [instructions (read-input-file fname)
         index 0
         steps 0]
    (if (or (>= index (count instructions))
            (< index 0))
      (if (= 0 steps) 1 steps)
      (recur (update instructions index #(calc-new-instruction %))
             (+ index (nth instructions index))
             (inc steps)))))

(comment 
  (solve "resources/day5_input.txt")
  ;; correct answer is 343467
  ;; "Elapsed time: 219.703792 msecs"

  (solve2 "resources/day5_input.txt")
  ;; correct answer is 24774780
  ;; "Elapsed time: 7277.358637 msecs"
  )
