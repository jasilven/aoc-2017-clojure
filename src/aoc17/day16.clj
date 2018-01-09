(ns aoc17.day16
  (:require [clojure.string :as str]))

(defn init-line [line]
  (str/split line #""))

(defn get-input [fname]
  (str/split (str/trim (slurp fname)) #","))

(defn spin [line n]
  (let [splitted (split-at (- (count line) n) line)]
    (as-> (conj (first splitted) (second splitted)) $
      (flatten $)
      (apply str $)
      (str/split $ #""))))

(defn exchange [line i j]
  (let [ival (nth line i)]
    (-> line
        (assoc i (nth line j))
        (assoc j ival))))

(defn partner [line a b]
  (let [s (apply str line)
        index-a (str/index-of s a)
        index-b (str/index-of s b)]
    (exchange line index-a index-b)))

(defn pInt [s]
  (Integer/parseInt s))

(defn execute-command [com line]
  (let [c (str (first com))
        arg (apply str (rest com))]
    (cond 
      (= c "s") (spin line (pInt arg))
      (= c "x") (exchange line
                          (pInt (first (str/split arg #"/")))
                          (pInt (second (str/split arg #"/"))))
      (= c "p") (partner line
                         (first (str/split arg #"/"))
                         (second (str/split arg #"/")))
      :else (println "Unknown command:" com))))

(defn solve [coms line]
  (->> (loop [coms coms
              l (init-line line)] 
         (if (empty? coms) l
             (recur (rest coms)
                    (execute-command (first coms) l))))
       (apply str))) 

(defn find-loop [coms line]
  (loop [count 0
         l line]
    (if (and (= line l) (not= count 0)) count 
        (recur (inc count)
               (solve coms l)))))

(defn solve2 [coms line]
  (loop [count 0 
         l line
         limit (mod 1000000000 (find-loop coms line))]
    (if (= count limit) l
        (recur (inc count)
               (solve coms l)
               limit))))
(comment
  (solve (get-input "resources/day16_input.txt") "abcdefghijklmnop")
  ;; correct answer is "pkgnhomelfdibjac"
  ;; "Elapsed time: 182.222683 msecs"
  (solve2 (get-input "resources/day16_input.txt") "abcdefghijklmnop")
  ;; correct answer is "pogbjfihclkemadn"
  ;; "Elapsed time: 6278.88059 msecs"
  )
