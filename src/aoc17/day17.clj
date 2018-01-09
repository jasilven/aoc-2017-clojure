(ns aoc17.day17)

(defn insert-at [index item vec]
  (let [splitted (split-at index vec)]
    (as-> (concat (first splitted) [item]) $
      (concat $ (second splitted))
      (apply vector $))))

(defn get-newindex [steps current len]
  (if (= len 1) 1
      (+ 1 (mod (+ current steps) len))))

(defn solve [steps]
  (loop [cnt 0
         buf [0] 
         cindex 0]
    (if (= cnt 2017) (get buf (mod (+ cindex 1)
                                   (+ (count buf) 1)))
        (let [newindex (get-newindex steps cindex (count buf))]
          (recur (inc cnt)
                 (insert-at newindex 
                            (inc cnt)
                            buf)
                 newindex)))))

(defn solve2 [steps limit]
  (loop [cnt 1
         cindex 0
         current-at-1 -1]
    (if (> cnt limit) current-at-1
        (let [newindex (get-newindex steps cindex cnt)]
          (recur (inc cnt)
                 newindex
                 (if (= newindex 1) cnt current-at-1))))))
(comment 
  (solve 355)
  ;; correct answer is 1912
  ;; "Elapsed time: 400.549834 msecs"
  (solve2 355 50000000)
  ;; correct answer is 21066990
  ;; "Elapsed time: 3669.919355 msecs"
  )
