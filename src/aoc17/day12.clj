(ns aoc17.day12
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :refer :all]))

(defn create-vector [v]
  (vector (first v) (rest v)))

(defn get-data [fname]
  (with-open [rdr (io/reader fname)]
    (doall
     (->> (for [line (line-seq rdr)]
            (-> line
                (str/trim)
                (str/replace #" <-> " " ")
                (str/replace #"," "")
                (str/split #"\s")
                (create-vector)))
          (into (sorted-map))))))

(defn solve [data-in]
  (loop [data data-in
         visited #{"0"} 
         nlist (into #{} (data "0"))
         res #{"0"}]
    (let [current (first nlist)
          children (into #{} (data current))
          visited (conj visited current)]
      (if (empty? nlist) (count res)
          (recur data
                 visited
                 (difference (union nlist children) visited)
                 (union children res))))))

(defn find-next-group [data-in key]
  (loop [data data-in
         visited #{key} 
         nlist (into #{} (data key))
         res #{key}]
    (let [current (first nlist)
          children (into #{} (data current))
          visited (conj visited current)]
      (if (empty? nlist) res
          (recur data
                 visited
                 (difference (union nlist children) visited)
                 (conj (union children res) current))))))

(defn solve2 [data-in]
  (loop [data data-in
         res 0]
    (if (empty? data) res
        (let [next-group (find-next-group data (key (first data)))]
          (recur (apply dissoc data next-group)
                 (inc res))))))
(comment
  (solve (get-data "resources/day12_input.txt"))
  ;; correct answer is 115
  ;; "Elapsed time: 37.987447 msecs"

  (solve2 (get-data "resources/day12_input.txt"))
  ;; correct answer is 221
  ;; "Elapsed time: 74.93452 msecs"
  )
