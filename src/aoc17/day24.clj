(ns aoc17.day24
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn load-components [fname]
  (apply vector (for [line (str/split-lines (slurp fname))]
             (map #(Integer/parseInt %) (str/split line #"/")))))

(defn find-matches [bridge components]
  (let [pin (last (last bridge))]
    (filter #(or (= pin (first %))
                 (= pin (second %)))
            (set/difference components
                            (set bridge)
                            (set (map reverse bridge))))))

(defn collapse-retval [retval new-bridges]
  (let [nb (set (for [b new-bridges] (drop-last b)))]
    (set/union (set/difference retval nb)
               (set new-bridges))))

(defn find-roots [components]
  (filter #(= (first %) 0) components))

(defn extend-bridge [bridge components]
  (for [c components]
    (if (= (last (last bridge)) (first c))
      (conj bridge c)
      (conj bridge (reverse c)))))

(defn select-best-bridge [bridges]
  (when-not (nil? bridges)
    (reduce max (for [b bridges] (reduce + (flatten b))))))

(defn solve [fname selector]
  (let [all-cs (load-components fname)
        roots (map vector (find-roots all-cs))
        cs (set/difference (set all-cs) (set roots))]
    (loop [bridges roots
           retval (set bridges)]
      (if (empty? bridges) (selector retval)
          (let [new-bridges (apply concat (filter some? (for [b bridges]
                                                          (if-let [comps (find-matches b cs)]
                                                            (extend-bridge b comps)))))]
            (recur new-bridges
                   (collapse-retval retval new-bridges)))))))

(defn select-best-bridge2 [bridges]
  (when-not (nil? bridges)
    (first (last (sort-by (juxt second first) (for [b bridges] [(reduce + (flatten b))
                                                                 (count b)]))))))

(comment
  (solve "resources/day24_input.txt" select-best-bridge)
  ;; correct answer is 1940
  ;; "Elapsed time: 90357.462899 msecs"

  (solve "resources/day24_input.txt" select-best-bridge2)
  ;; correct answer is 1928
  ;; "Elapsed time: 92617.476434 msecs"
  )
