(ns aoc17.day13
  (:require [clojure.string :as str])
  (:import (java.io BufferedReader StringReader)))

(defn get-input-data [fname]
  (->> (for [line (line-seq (BufferedReader.
                             (StringReader.
                              (slurp fname))))]
         (as-> line $
           (str/replace $ #" " "")
           (str/split $ #":")
           (map #(Integer/parseInt %) $)))))

(defn initialize-layers [input]
  (loop [ret {}
         input input]
    (if (empty? input)
      ret
      (recur (assoc ret (ffirst input) {:position 0,
                                        :range (second (first input))
                                        :move :down})
             (rest input)))))

(defn calc-severity [layer position layers]
  (let [spos (get-in layers [layer :position])]
    (cond (nil? spos) 0
          (= position spos) (* layer (get-in layers [layer :range]))
          :else 0)))

(defn update-scanner [layer]
  (let [move (layer :move)
        pos (layer :position)
        range (layer :range)]
    (cond (and (= move :down)
               (< pos (dec range)))
          (assoc layer :position (inc pos))
          (and (= move :up)
               (> pos 0))
          (assoc layer :position (dec pos))
          :else (if (= move :down)
                  (update-scanner (assoc layer :move :up))
                  (update-scanner (assoc layer :move :down))))))

(defn update-scanners [layers]
  (loop [keys (keys layers)
         layers layers]
    (if (empty? keys) layers
        (let [key (first keys)
              layer (layers key)]
          (recur (rest keys)
                 (assoc layers key (update-scanner layer)))))))

(defn solve [fname]
  (loop [layers (initialize-layers (get-input-data fname))
         max-layer (apply max (keys layers))
         layer -1
         position 0
         severity 0]
    (if (> layer max-layer) severity
        (recur (update-scanners layers)
               max-layer
               (inc layer)
               position
               (+ severity (calc-severity (inc layer) position layers))))))

(defn scanner-at-zero? [time picos layers]
  (let [depth (get-in layers [picos :range])]
    (cond
      (= 0 time) true
      (nil? depth) false
      (= 0 (mod time (* 2 (- depth 1)))) true
      :else false)))

(defn index-of [item coll]
  (loop [i 0
         c coll]
    (cond (= item (first c)) i
          (empty? c) nil
          :else (recur (inc i) (rest c)))))

(defn solve2 [fname]
  (let [layers (initialize-layers (get-input-data fname))]
    (->>  (for [delay (range)]
            (->> (for [picos (range (+ 1 (apply max (keys layers))))]
                   (scanner-at-zero? (+ picos delay) picos layers))
                 (reduce #(or %1 %2))))

          (index-of false))))
(comment
  (solve "resources/day13_input.txt")
  ;; correct answer is 2384
  ;; "Elapsed time: 15.236304 msecs"

  (solve2 "resources/day13_input.txt")
  ;; correct answer is 3921270
  ;; "Elapsed time: 108500.627301 msecs"
  )
