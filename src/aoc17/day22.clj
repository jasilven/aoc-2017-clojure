(ns aoc17.day22
  (:require [clojure.string :as str]))

(defn load-matrix [fname]
  (apply vector (for [line (str/split-lines (slurp fname))]
                  (str/split line #""))))

(defn find-infected [m center]
  (let [rind (range (dec (first center)) (- (first center)) -1)
        cind (range (inc (- (second center))) (second center) 1)]
    (->> (for [row (range (count m))]
           (for [col (range (count (first m)))]
             (when (= (get-in m [row col]) "#")
               [(nth rind row) (nth cind col)])))
         (apply concat)
         (filter some?))))

(defn load-data [fname]
  (let [matrix (load-matrix fname)
        rows (count matrix)
        cols (count (first matrix))
        center [(+ 1 (int (/ rows 2))) (int (+ 1 (/ cols 2)))]]
    (-> {}
        (assoc :current [0 0])
        (assoc :dir :up)
        (assoc :inf-cnt 0)
        (assoc :infected (into #{} (find-infected matrix center))))))

(defn turn-right [data]
  (condp = (get data :dir)
      :up (assoc data :dir :right)
      :right (assoc data :dir :down)
      :down (assoc data :dir :left)
      :left (assoc data :dir :up)))

(defn turn-left [data]
  (condp = (get data :dir)
      :up (assoc data :dir :left)
      :right (assoc data :dir :up)
      :down (assoc data :dir :right)
      :left (assoc data :dir :down)))

(defn turn [data]
  (if (contains? (get data :infected) (get data :current))
    (turn-right data)
    (turn-left data)))

(defn infect-or-clean [data]
  (if-not (contains? (get data :infected) (get data :current))
    (doall (-> data
            (assoc :infected (conj (get data :infected)
                                   (get data :current)))
            (assoc :inf-cnt (inc (get data :inf-cnt)))))
    (doall (-> data
               (assoc :infected (disj (get data :infected)
                                        (get data :current)))))))

(defn move [data]
  (condp = (get data :dir)
    :up (assoc data :current [(inc (get-in data [:current 0]))
                              (get-in data [:current 1])])
    :down (assoc data :current [(dec (get-in data [:current 0]))
                                (get-in data [:current 1])])
    :right (assoc data :current [(get-in data [:current 0])
                                 (inc (get-in data [:current 1]))])
    :left (assoc data :current [(get-in data [:current 0])
                                (dec (get-in data [:current 1]))])))

(defn burst [data]
  (-> data (turn) (infect-or-clean) (move)))

(defn solve [fname cnt]
  (-> (nth (iterate burst (load-data fname)) cnt)
      (get :inf-cnt)))

(defn turn [data]
  (if (contains? (get data :infected) (get data :current))
    (turn-right data)
    (turn-left data)))

(defn load-data2 [fname]
  (let [matrix (load-matrix fname)
        rows (count matrix)
        cols (count (first matrix))
        center [(+ 1 (int (/ rows 2))) (int (+ 1 (/ cols 2)))]]
    (-> {}
        (assoc :current [0 0])
        (assoc :dir :up)
        (assoc :inf-cnt 0)
        (assoc :weakened #{})
        (assoc :flagged #{})
        (assoc :infected (into #{} (find-infected matrix center))))))

(defn turn2 [data]
  (cond (contains? (get data :infected) (get data :current))
        (turn-right data)
        (contains? (get data :flagged) (get data :current))
        (turn-right (turn-right data))
        (contains? (get data :weakened) (get data :current))
        data
        :else (turn-left data)))

(defn change-node-state [data]
  (cond 
   (contains? (get data :infected) (get data :current))
   (do (-> data
               (assoc :infected (disj (get data :infected)
                                        (get data :current)))
               (assoc :flagged (conj (get data :flagged)
                                        (get data :current)))))
   (contains? (get data :weakened) (get data :current))
   (do (-> data
               (assoc :weakened (disj (get data :weakened)
                                      (get data :current)))
               (assoc :infected (conj (get data :infected)
                                      (get data :current)))
               (assoc :inf-cnt (inc (get data :inf-cnt)))))
   (contains? (get data :flagged) (get data :current))
   (assoc data :flagged (disj (get data :flagged)
                                (get data :current)))
   :else
   (assoc data :weakened (conj (get data :weakened)
                                 (get data :current)))))

(defn burst2 [data]
  (-> data (turn2) (change-node-state) (move)))

(defn solve2 [fname cnt]
  (-> (nth (iterate burst2 (load-data2 fname)) cnt)
      (get :inf-cnt)))


(comment
  (solve "resources/day22_input.txt" 10000)
  ;; correct answer is 5256
  ;; "Elapsed time: 106.66023 msecs"
  (solve2 "resources/day22_input.txt" 10000000)
  ;; correct answer is 2511345
  ;; "Elapsed time: 29456.641553 msecs"
  )


