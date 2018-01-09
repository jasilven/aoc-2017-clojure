(ns aoc17.day6
  (:require [clojure.string :as str]))

(defn get-input-data [fname]
  (->> (str/split (slurp fname) #"\s+")
       (mapv #(Integer/parseInt %))))

(defn find-largest [banks]
  (loop [cnt 1 largest [0 (first banks)] ]
    (if (= cnt (count banks))
      largest
      (recur (inc cnt)
             (if (> (nth banks cnt)
                    (last largest))
               [cnt (nth banks cnt)]
               largest)))))

(defn reallocate-banks [banks]
  (if (<= (count banks) 1)
    banks
    (let [largest (find-largest banks)] 
      (loop [index (mod (inc (first largest)) (count banks))
             bs (assoc banks (first largest) 0)
             cnt (dec (last largest))]
        (if (< cnt 0)
          bs
          (recur (mod (inc index) (count bs))
                 (update bs index #(inc %))
                 (dec cnt)))))))

(defn solve [fname]
  (loop [cycles 0
         banks (get-input-data fname)
         history {banks 0}]
    (if (and (> cycles 0) 
             (contains? history banks))
      cycles
      (recur (inc cycles)
             (reallocate-banks banks)
             (assoc history banks cycle)))))

(defn solve2-find-loop-start [fname]
  (loop [cycles 0
         banks (get-input-data fname)
         history {banks 0}]
    (if (and (> cycles 0) 
             (contains? history banks))
      banks
      (recur (inc cycles)
             (reallocate-banks banks)
             (assoc history banks cycle)))))

(defn solve2 [fname]
  (loop [cycles 0
         banks (solve2-find-loop-start fname)
         history {banks 0}]
    (if (and (> cycles 0) 
             (contains? history banks))
      cycles
      (recur (inc cycles)
             (reallocate-banks banks)
             (assoc history banks cycle)))))

(comment  
  (solve "resources/day6_input.txt")
  ;; correct answer is 12841
  ;; "Elapsed time: 237.888171 msecs"
  (solve2 "resources/day6_input.txt")
  ;; correct answer is 8038
  ;; "Elapsed time: 263.617232 msecs"
  )
