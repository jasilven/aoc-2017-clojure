(ns aoc17.day18
  (:require [clojure.string :as str]))

(defn get-data [fname]
  (as-> (for [line (str/split-lines (slurp fname))]
          (str/split line #" ")) $
    (apply vector $)
    (assoc {} :comms $ :cur 0 :sound nil :retval nil :regs {})))

(defn sound [data x]
  (if-let [s (get-in data [:regs x])]
    (do (-> (assoc data :sound s)
            (update :cur inc)))))

(defn get-val [data x]
  (cond
    (contains? (get data :regs) x) (get-in data [:regs x])
    (integer? x) x
    (integer? (read-string x)) (Integer/parseInt x) 
    :else 0))

(defn assign [data x y]
  (-> (assoc-in data [:regs x] (get-val data y))
      (update :cur inc)))

(defn addition [data x y]
  (-> (assoc-in data [:regs x]
                (+ (get-val data x)
                   (get-val data y)))
      (update :cur inc)))

(defn mul [data x y]
  (-> (assoc-in data [:regs x]
                (* (get-val data x)
                   (get-val data y)))
      (update :cur inc)))

(defn modulo [data x y]
  (-> (assoc-in data [:regs x]
                (mod (get-val data x)
                     (get-val data y)))
      (update :cur inc)))

(defn recover [data x]
  (if (not= 0 (get-val data x))
    (-> (assoc data :retval (get data :sound))
        (update  :cur inc))
    (update data :cur inc)))

(defn jump [data x y]
  (if (pos? (get-val data x))
    (assoc data :cur (+ (get data :cur) (get-val data y)))
    (update data :cur inc)))

(defn execute [data]
  (let [com (get-in data [:comms (get data :cur)])
        oper (first com)
        arg1 (second com)
        arg2 (when (= 3 (count com)) (last com))]
    (cond
      (= "set" oper) (assign data arg1 arg2)
      (= "add" oper) (addition data arg1 arg2)
      (= "mul" oper) (mul data arg1 arg2)
      (= "mod" oper) (modulo data arg1 arg2)
      (= "snd" oper) (sound data arg1)
      (= "jgz" oper) (jump data arg1 arg2)
      (= "rcv" oper) (recover data arg1)
      :else (do
              (println "Unknown command:" oper)
              (assoc data :retval "Terminated")))))
(defn run [data]
  (loop [data data]
    (let [cur (get data :cur)]
      (cond
        (and (neg? cur)
             (>= cur (count (get data :comms))))
        "Terminated"
        (nil? (get data :retval))
        (recur (execute data))
        :else (get data :retval)))))

(defn solve [fname]
  (loop [data (get-data fname)
         cur (get data :cur)]
    (cond
      (and (neg? cur)
           (>= cur (count (get data :comms))))
      "Terminated"
      (nil? (get data :retval))
      (recur (execute data) cur)
      :else (get data :retval))))

(defn sendm [data x]
  (let [ch (get data :send)]
    (-> (assoc data :send (conj ch (str (get-val data x))))
        (update :scnt inc)
        (update :cur inc))))

(defn receive [data x]
  (let [ch (get data :recv)]
    (if-not (empty? ch)
      (-> (assign data (str x) (first ch))
          (assoc :recv (vec (rest ch)))
          (assoc :rec false))
      (if (get data :rec) data
        (assoc data :rec true)))))

(defn execute2 [data]
  (let [com (get-in data [:comms (get data :cur)])
        oper (first com)
        arg1 (second com)
        arg2 (if (= 3 (count com)) (last com) nil)]
    (cond
      (= "set" oper) (assign data arg1 arg2)
      (= "add" oper) (addition data arg1 arg2)
      (= "mul" oper) (mul data arg1 arg2)
      (= "mod" oper) (modulo data arg1 arg2)
      (= "snd" oper) (sendm data arg1)
      (= "jgz" oper) (jump data arg1 arg2)
      (= "rcv" oper) (receive data arg1)
      :else (do
              (println "Unknown command:" oper)
              (assoc data :terminated true)))))

(defn terminated? [data]
  (let [cur (get data :cur)]
    (or (neg? cur) (>= cur (count (get data :comms))))))

(defn deadlock? [data0 data1]
  (and (get data0 :rec) (get data1 :rec)))

(defn deliver-messages [data0 data1]
  (let [recv0 (get data0 :recv)
        recv1 (get data1 :recv)
        send0 (get data0 :send)
        send1 (get data1 :send)
        data0 (assoc data0 :send (vec (rest send0)))
        data1 (assoc data1 :send (vec (rest send1)))]
    [(if-not (empty? send1)
       (assoc data0 :recv (conj recv0 (first send1))) data0)
     (if-not (empty? send0)
       (assoc data1 :recv (conj recv1 (first send0))) data1)]))

(defn run2 [data0 data1]
  (loop [data0 data0
         data1 data1]
    (let [[data0 data1] (deliver-messages data0 data1)]
      (cond
        (or (deadlock? data0 data1)
            (and (terminated? data0) (terminated? data1)))
        (get data1 :scnt)
        (terminated? data0)
        (do (println "data0 terminated") (recur data0 (execute2 data1)))
        (terminated? data1)
        (do (println "data1 terminated") (recur (execute2 data0) data1))
        :else (recur (execute2 data0) (execute2 data1))))))

(defn solve2 [fname] 
  (let [data0 (assoc-in (assoc (get-data fname)
                               :prog 0 :rec false :scnt 0 :recv [] :send []) [:regs "p"] 0)
        data1 (assoc-in (assoc data0
                               :prog 1 :rec false :scnt 0 :recv [] :send []) [:regs "p"] 1)]
    (run2 data0 data1)))

(comment
  (solve "resources/day18_input.txt")
  ;; correct answer is 1187
  ;; "Elapsed time: 36.205816 msecs"
  (solve2 "resources/day18_input.txt")
  ;; correct answer is 5969
  ;; "Elapsed time: 597.691182 msecs"
  )
