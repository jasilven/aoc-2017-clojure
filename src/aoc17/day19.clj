(ns aoc17.day19
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn get-map [fname]
  (with-open [reader (io/reader fname)]
    (doall (apply vector (for [line (line-seq reader)]
                           (str/split line #""))))))

(defn find-root [data]
  [0 (.indexOf (get data 0) "|")])

(defn get-data [fname]
  (let [map (get-map fname)
        root (find-root map)]
    {:map map
     :curpos root
     :count 1 
     :letters [] 
     :direction :down}))

(defn isLetter [s]
  (Character/isLetter (first s)))

(defn peek-direction [data curpos dir]
  (cond
    (= dir :down) (get-in data [:map (inc (first curpos)) (second curpos)])
    (= dir :up) (get-in data [:map (dec (first curpos)) (second curpos)])
    (= dir :left) (get-in data [:map (first curpos) (dec (second curpos))])
    (= dir :right) (get-in data [:map (first curpos) (inc (second curpos))])))

(defn update-letters [data]
  (let [cur-ch (get-in data [:map (first (get data :curpos)) (second (get data :curpos))])]
    (if (isLetter cur-ch)
      (assoc data :letters (conj (get data :letters) cur-ch))
      data)))

(defn move-forward [data]
  (let [curpos (get data :curpos)
        dir (get data :direction)]
    (-> (cond
          (= dir :down) (assoc data :curpos [(inc (first curpos)) (second curpos)])
          (= dir :up) (assoc data :curpos [(dec (first curpos)) (second curpos)])
          (= dir :left) (assoc data :curpos [(first curpos) (dec (second curpos))])
          (= dir :right) (assoc data :curpos [(first curpos) (inc (second curpos))]))
      (assoc :count (inc (get data :count)))
      (update-letters))))

(defn peek-right [data curpos dir]
  (cond
    (= dir :down) (peek-direction data curpos :right)
    (= dir :left) (peek-direction data curpos :up) 
    (= dir :up) (peek-direction data curpos :right)
    (= dir :right) (peek-direction data curpos :down)))

(defn peek-left [data curpos dir]
  (cond
    (= dir :down) (peek-direction data curpos :left)
    (= dir :left) (peek-direction data curpos :down) 
    (= dir :up) (peek-direction data curpos :left)
    (= dir :right) (peek-direction data curpos :up)))

(defn turn-left [data curpos dir]
  (-> (cond
        (= dir :down) (assoc data :direction :left) 
        (= dir :left)  (assoc data :direction :down)
        (= dir :up) (assoc data :direction :left)
        (= dir :right) (assoc data :direction :up))
    (move-forward)))

(defn turn-right [data curpos dir]
  (-> (cond
        (= dir :down) (assoc data :direction :right) 
        (= dir :left)  (assoc data :direction :up)
        (= dir :up) (assoc data :direction :right)
        (= dir :right) (assoc data :direction :down))
    (move-forward)))

(defn make-turn [data curpos dir]
  (let [ch-at-right (peek-right data curpos dir)
        ch-at-left (peek-left data curpos dir)]
    (cond
      (and
        (not= " " ch-at-right)
        (not= nil ch-at-right)) 
      (turn-right data curpos dir)
      (and
        (not= " " ch-at-left)
        (not= nil ch-at-left))
      (turn-left data curpos dir)
      :else [(apply str (get data :letters)) (get data :count)])))

(defn move [data]
  (let [curpos (get data :curpos)
        dir (get data :direction)
        cur-ch (get-in data [:map (first curpos) (second curpos)])
        next-ch (peek-direction data curpos dir)]
    (cond
      (= cur-ch "+") (make-turn data curpos dir)
      (and
        (not= " " next-ch)
        (not= nil next-ch))
      (move-forward data)
      :else (make-turn data curpos dir))))

(defn solve [fname]
  (loop [data (get-data fname)]
    (if (vector? data) (first data) 
        (recur (move data)))))

(defn solve2 [fname]
  (loop [data (get-data fname)]
    (if (vector? data) (second data) 
        (recur (move data)))))

(comment 
  (solve "resources/day19_input.txt")
  ;; correct answer is "VEBTPXCHLI"
  ;; "Elapsed time: 277.329787 msecs"

  (solve2 "resources/day19_input.txt")
  ;; correct answer is 18702
  ;; "Elapsed time: 163.97038 msecs"
  )
