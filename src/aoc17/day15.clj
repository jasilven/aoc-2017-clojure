(ns aoc17.day15)

(defn bin-32 [bi]
  (let [bin (.toString bi 2)]
    (-> (apply str (take (- 32 (count bin))
                         (iterate str "0")))
        (str bin))))

(defn generateA [prev]
  (biginteger (mod (* 16807 prev) 2147483647)))

(defn generateB [prev]
  (biginteger (mod (* 48271 prev) 2147483647)))

(defn update-res [res a b]
  (let [bina (apply str (drop 16 (bin-32 a)))
        binb (apply str (drop 16 (bin-32 b)))]
    (if (= bina binb) (inc res) res)))

(defn solve [start-A start-B limit]
  (loop [count 0
         a (drop 1 (iterate generateA start-A))
         b (drop 1 (iterate generateB start-B))
         res 0]
    (if (= count limit) res
        (recur (inc count)
               (rest a)
               (rest b)
               (update-res res (first a) (first b))))))

(defn generateA2 [prev]
  (loop [prev prev]
    (let [n (biginteger (mod (* 16807 prev) 2147483647))]
      (if (= (mod n 4) 0) n 
          (recur n)))))

(defn generateB2 [prev]
  (loop [prev prev]
    (let [n (biginteger (mod (* 48271 prev) 2147483647))]
      (if (= (mod n 8) 0) n 
          (recur n)))))

(defn solve2 [start-A start-B limit]
  (loop [count 0
         a (drop 1 (iterate generateA2 start-A))
         b (drop 1 (iterate generateB2 start-B))
         res 0]
    (if (= count limit) res
        (recur (inc count)
               (rest a)
               (rest b)
               (update-res res (first a) (first b))))))
(comment 
  (solve 277 349 40000000)
  ;; correct answer is 592
  ;; "Elapsed time: 369381.775355 msecs"
  (solve2 277 349 5000000)
  ;; correct answer is 320
  ;; "Elapsed time: 56488.188918 msecs"
  )
