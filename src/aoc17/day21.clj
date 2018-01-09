(ns aoc17.day21
  (:require [clojure.core.matrix :as m]
            [clojure.string :as str]))

(declare rules)

(def start-pattern ".#./..#/###")

(defn flip [m] (map reverse m))

(defn rotate [m] (m/transpose m))

(defn to-matrix [pat]
  (for [row (str/split pat #"/")]
    (str/split row #"")))

(defn to-pattern [mat]
  (as-> (for [row mat]
          (str (apply str row) "/")) $
    (flatten $)
    (apply str $)
    (str/replace $ #"/$" "" )))

(defn get-variations [rule]
  (let [mat (to-matrix rule)]
    (->> (list (rotate mat) (flip mat)
           (rotate (flip mat)) (flip (rotate mat))
           (flip (rotate (flip mat))) (rotate (flip (rotate mat))))
      (map to-pattern)
      (distinct))))

(defn get-rules [fname]
  (for [line (str/split-lines (slurp fname))]
    (as-> (apply list (str/split line #" => ")) $
      (reverse $)
      (apply vector $)
      (apply conj $ (get-variations (second $))))))

(defn find-target-pattern [rules pat]
  (loop [rules rules]
    (when (empty? rules) nil)
    (if (.contains (rest (first rules)) pat)
      (ffirst rules)
      (recur (rest rules)))))

(defn create-lines [parts]
  (let [parts (if (string? parts) (list parts) parts)]
    (as-> (map #(str/split % #"/") parts) $
      (apply map str $)
      (map vector $)
      (map #(str/split (first %) #"") $))))

(defn apply-concat-if-needed [coll]
  (if (> (count coll) 1) (apply concat coll) coll))

(defn only-first-if-only [coll]
  (if (= (count coll) 1) (first coll) coll))

(defn create-matrix [matrix]
  (let [shape (first (m/shape matrix))
        mmat-size (if (odd? shape) 3 2)
        mmat-in-row (/ shape mmat-size)]
    (->> (for [mini-r (range 0 (if (= shape 3) 1 shape) mmat-size)]
           (-> (for [mini-c (range 0 (if (= shape 3) 1 shape) mmat-size)]
                 (->> (m/submatrix matrix mini-r mmat-size mini-c mmat-size)
                   (to-pattern)
                   (find-target-pattern rules)))
             (create-lines)))
      (apply-concat-if-needed)
      (only-first-if-only))))

(defn solve [n]
  (->> (nth (iterate create-matrix (to-matrix ".#./..#/###")) n)
    flatten
    (filter #(= "#" %))
    count))

(defn solve2 [] (solve 18))

(comment 
  (def rules (get-rules "resources/day21_input.txt"))  
  (solve 5)
  ;; correct answer is 152
  ;; "Elapsed time: 96.941699 msecs"

  (solve2)
  ;; correct answer is 1956174
  ;; "Elapsed time: 147348.645913 msecs"
  )
