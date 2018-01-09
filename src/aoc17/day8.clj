(ns aoc17.day8
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-input-file [fname]
  (with-open [reader (io/reader fname)]
    (->> (doall (line-seq reader))
         (apply vector)))) 

(defn init-registers [lines]
  (->> (for [line lines]
         [(first (str/split line #" ")) 0])
       (flatten)
       (apply hash-map)))

(defn parse-int [number-string]
  (try (Integer/parseInt number-string)
       (catch Exception e nil)))

(defn parse-instruction [counter lines regs]
  (let [line (str/split (lines counter) #" ")]
    (when (<= counter (count lines))
      {:reg (line 0)
       :com (line 1)
       :carg (if (nil? (regs (line 2))) (parse-int (line 2)) (regs (line 2)))
       :if (line 3)
       :oarg1 (if (nil? (regs (line 4))) (line 4) (regs (line 4)))
       :oper (if (= "!=" (line 5)) "not=" (line 5))
       :oarg2 (if (nil? (regs (line 6))) (line 6) (regs (line 6)))})))

(defn calc-result [regs]
  (reduce max (vals regs)))

(defn execute [ins regs]
  (if (eval (read-string (format "(%s %s %s)" (ins :oper) (ins :oarg1) (ins :oarg2))))
    (do
      (cond
        (= "inc" (ins :com)) (assoc regs (ins :reg)
                                    (+ (regs (ins :reg)) (ins :carg)))
        (= "dec" (ins :com)) (assoc regs (ins :reg)
                                    (- (regs (ins :reg)) (ins :carg)))
        :else nil))
    regs))


(defn solve [fname]
  (loop [lines (apply vector (read-input-file fname))
         regs (init-registers lines)
         counter 0]
    (if (= counter (count lines))
      (calc-result regs)
      (recur lines
             (execute (parse-instruction counter lines regs) regs)
             (inc counter)))))

(def max-register "__MAX__")

(defn init-registers2 [lines]
  (as-> (for [line lines]
          [(first (str/split line #" ")) 0]) $
    (flatten $)
    (apply hash-map $)
    (assoc $ max-register 0)))

(defn update-max-value [regs]
  (->> regs
       vals
       (reduce max (regs max-register))
       (assoc regs max-register)))

(defn calc-result2 [regs]
  (regs max-register))

(defn execute2 [ins regs]
  (->> (if (eval (read-string (format "(%s %s %s)" (ins :oper) (ins :oarg1) (ins :oarg2))))
         (do
           (cond
             (= "inc" (ins :com)) (assoc regs (ins :reg)
                                         (+ (regs (ins :reg)) (ins :carg)))
             (= "dec" (ins :com)) (assoc regs (ins :reg)
                                         (- (regs (ins :reg)) (ins :carg)))
             :else nil))
         regs)
       (update-max-value)))

(defn solve2 [fname]
  (loop [lines (apply vector (read-input-file fname))
         regs (init-registers2 lines)
         counter 0]
    (if (= counter (count lines))
      (calc-result2 regs)
      (recur lines
             (execute2 (parse-instruction counter lines regs) regs)
             (inc counter))
      )))

(comment
  (solve "resources/day8_input.txt")
  ;; correct answer is 4448
  ;; "Elapsed time: 504.544371 msecs"

  (solve2 "resources/day8_input.txt")
  ;; correct answer is 6582
  ;; "Elapsed time: 542.04844 msecs"
  )
