(ns aoc17.day23
  (:require [clojure.string :as str]))

(defn get-data [fname]
  (as-> (for [line (str/split-lines (slurp fname))]
          (str/split line #" ")) $
    (apply vector $)
    (assoc {} :comms $ :cur 0 :retval 0 :regs {})))

(defn get-val [data x]
  (cond
    (contains? (get data :regs) x) (get-in data [:regs x])
    (integer? x) x
    (integer? (read-string x)) (Integer/parseInt x) 
    :else 0))

(defn cmd-set [data x y]
  (-> (assoc-in data [:regs x] (get-val data y))
      (update-in [:cur] inc))) 

(defn cmd-sub [data x y]
  (-> (assoc-in data [:regs x]
                (- (get-val data x)
                   (get-val data y)))
      (update-in [:cur] inc)))

(defn cmd-mul [data x y]
  (-> (assoc-in data [:regs x]
                (* (get-val data x)
                   (get-val data y)))
      (update-in [:cur] inc)
      (update-in [:retval] inc)))

(defn cmd-jnz [data x y]
  (if-not (zero? (get-val data x))
    (assoc data :cur (+ (get data :cur) (get-val data y)))
    (update-in data [:cur] inc)))

(defn execute [data]
  (let [com (get-in data [:comms (get data :cur)])
        oper (first com)
        arg1 (second com)
        arg2 (when (= 3 (count com)) (last com))]
    (condp = oper
      nil nil
      "set" (cmd-set data arg1 arg2)
      "sub" (cmd-sub data arg1 arg2)
      "mul" (cmd-mul data arg1 arg2)
      "jnz" (cmd-jnz data arg1 arg2)
      :else (do
              (println "Unknown command:" oper)
              nil))))

(defn solve [fname]
  (get (->> (iterate execute (get-data fname))
             (take-while some?)
             (last))
       :retval))

(defn divisible? [a b]
  (zero? (mod a b)))

(defn prime? [n]
  (and (> n 1) (not-any? (partial divisible? n) (range 2 n))))

(defn solve2 []
  (count (filter some? (for [i (range 109900 126901 17)]
                         (when-not (prime? i) 1)))))

(comment
  (solve "resources/day23_input.txt")
  ;; correct answer is 9409
  ;; "Elapsed time: 310.571969 msecs"
  (solve2)
  ;; correct answer is 913
  ;; "Elapsed time: 851.599005 msecs"
   )
