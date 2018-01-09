(ns aoc17.day9
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-input-file [fname]
  (with-open [reader (io/reader fname)]
    (-> (doall (line-seq reader))
        (first)
        (str/trim))))

(defn strip-input [input]
  (-> input
      (str/trim) 
      (clojure.string/replace #"!!" "")
      (clojure.string/replace #"!>" "")
      (clojure.string/replace #"!<" "")
      (clojure.string/replace #"<[^>]*[^!]>" "")
      (clojure.string/replace #"," "")
      (clojure.string/replace #"<>" "")
      ))

(defn solve [line]
  (loop [input (strip-input line) 
         result 0
         char (first input)
         jono '()]
    (cond (nil? char) result
          (= char \{) (recur (rest input)
                             result
                             (second input)
                             (cons char jono))
          :else (recur (rest input)
                       (+ result (count jono))
                       (second input)
                       (rest jono)))))

(defn strip-input2 [input]
  (-> input
      (str/trim) 
      (clojure.string/replace #"!!" "")
      (clojure.string/replace #"!>" "")
      (clojure.string/replace #"!<" "")))

(defn solve2 [line]
  (->> (for [line (re-seq #"<[^>]*[^!]>" (strip-input2 line))]
         (-> line
             (clojure.string/replace #"!." "")
             (count)
             (- 2)))
       (reduce +)))

(comment
  (solve (read-input-file "resources/day9_input.txt"))
  ;; correct answer is 14204
  ;; "Elapsed time: 19.469419 msecs"

  (solve2 (read-input-file "resources/day9_input.txt"))
  ;; correct answer is 6622
  ;; "Elapsed time: 11.625811 msecs"
  )
