(ns aoc17.day4
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(defn read-input-file [fname]
  (with-open [reader (io/reader fname)]
    (doall
     (csv/read-csv reader :separator \ ))))

(defn create-occur-map [words]
  (loop [ret {} ws words]
    (let [w (first ws)]
      (if (nil? w) ret 
          (-> (if (contains? ret w)
                (assoc ret w (inc (ret w)))
                (assoc ret w 1))
              (recur (rest ws)))))))

(defn solve [fname]
  (->> (for [line (read-input-file fname)]
         (let [occur-cnts (vals (create-occur-map line))]
           (if (= (count (filter #(> % 1) occur-cnts)) 0)
             1
             0)))
       (reduce +)))

(defn solve2 [fname]
  (->> (for [line (read-input-file fname)]
         (->> (for [word line]
                (->> word
                     (sort)
                     (map #(str %))
                     (reduce str)))
              (create-occur-map)
              (vals)
              (filter #(> % 1))
              (count)
              (conj ())
              (map #(if (= % 0) 1 0))
              (first)))
       (reduce +)))


(comment 
  (solve "resources/day4_input.csv")
  ;; correct answer is 477
  ;; "Elapsed time: 46.02132 msecs"
  (solve2 "resources/day4_input.csv")
  ;; correct answer is 167
  ;; "Elapsed time: 63.02932 msecs"
  )
