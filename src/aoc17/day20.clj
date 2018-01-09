(ns aoc17.day20
  (:require [clojure.string :as str]))

(defn abs [n] (max n (- n)))

(defn load-data [fname]
  (apply vector (for [line (str/split-lines (slurp fname))]
                  (as-> line $
                    (str/replace $ #".=<" "")
                    (str/replace $ #">" "")
                    (str/replace $ #" " "")
                    (str/split $ #",")
                    (map #(Integer/parseInt %) $)
                    (apply vector $)))))

(defn update-linenums [data]
  (apply vector (for [num (range (count data))]
                  (->> (cons num (get data num))
                    (apply vector)))))

(defn calc-distance [particle]
  (assoc particle 10 (+ (abs (get particle 0))
                   (abs (get particle 1)) 
                   (abs (get particle 2)))))

(defn calc-velocity [particle]
  (assoc particle 11 (Math/sqrt (+ (* (get particle 4) (get particle 4))
                                  (* (get particle 5) (get particle 5)) 
                                  (* (get particle 6) (get particle 6))))))

(defn calc-acceleration [particle]
  (assoc particle 12 (Math/sqrt (+ (* (get particle 7) (get particle 7))
                                  (* (get particle 8) (get particle 8)) 
                                  (* (get particle 9) (get particle 9))))))

(defn distance [particle] (nth particle 10))

(defn velocity [particle] (nth particle 11))

(defn acceleration [particle] (nth particle 12))

(defn update-velocity [data]
  (for [particle data]
    (as-> particle $
      (assoc $ 4 (+ (get particle 7) (get particle 4)))
      (assoc $ 5 (+ (get particle 8) (get particle 5)))
      (assoc $ 6 (+ (get particle 9) (get particle 6)))
      (apply vector $))))

(defn update-position [data]
  (for [particle data]
    (as-> particle $
      (assoc $ 1 (+ (get particle 4) (get particle 1)))
      (assoc $ 2 (+ (get particle 5) (get particle 2)))
      (assoc $ 3 (+ (get particle 6) (get particle 3)))
      (apply vector $))))

(defn collisions [data]
  (as-> (for [line data]
          [(first line) (take 3 (drop 1 line))]) $
    (group-by second $)
    (vals $)
    (filter #(> (count %) 1) $)))

(defn get-colliding-nums [data]
  (let [line (flatten (collisions data))]
    (apply vector (for [index (range 0 (* 4 (/ (count line) 4)) 4)]
                    (nth line index)))))

(defn remove-colliding [data]
  (let [nums (get-colliding-nums data)]
    (remove #(.contains nums (first %)) data)))

(defn tick [data]
  (as-> data $
    (update-velocity $)
    (update-position $)))

(defn solve [fname]
  (->> (for [particle (update-linenums (load-data fname))]
         (as-> particle $
           (calc-distance $)
           (calc-velocity $)
           (calc-acceleration $)
           (apply vector $)))
    (apply vector)
    (sort-by (juxt acceleration velocity distance))
    (ffirst)))

(defn solve2 [fname]
  (loop [data-before (update-linenums (load-data fname))
         data-after (remove-colliding (tick data-before))
         counter 1]
    (if (= counter 1000)
      (count data-after)
      (recur
        data-after
        (remove-colliding (tick data-after))
        (inc counter)))))

(comment 
  (solve "resources/day20_input.txt")
  ;; correct answer is 300
  ;; "Elapsed time: 27.179924 msecs"

  (solve2 "resources/day20_input.txt")
  ;; correct answer is 502
  ;; "Elapsed time: 4333.376252 msecs"
  )
