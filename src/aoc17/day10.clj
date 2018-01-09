(ns aoc17.day10)

(defn make-newline [line len curpos]
  (let [reversed (reverse (take len (drop curpos (cycle line))))
        newline (concat (take curpos line) reversed)
        overflow (- (count newline) (count line))]
    (cond (> overflow 0)
          (take (count line) (concat (drop (count line) newline)
                                     (drop overflow newline)))
          (< overflow 0)
          (concat newline (drop (count newline) line))
          :else newline)))

(defn solve [lens-in line-in]
  (loop [lens lens-in
         skip 0
         curpos 0
         line line-in]
    (if (empty? lens) (* (first line) (second line))
        (recur (rest lens)
               (inc skip)
               (mod (+ (first lens) skip curpos) (count line))
               (make-newline line (first lens) curpos)))))

(def fill-seq '(17, 31, 73, 47, 23))

(defn get-lens [s]
  (-> (for [c s] (int c))
      (concat fill-seq)))

(defn knot-hash-single [lens-in line-in skip-in curpos-in ]
  (loop [lens lens-in
         line line-in
         skip skip-in
         curpos curpos-in]    
    (if (empty? lens) {:skip skip :curpos curpos :line line}
        (recur (rest lens)
               (make-newline line (first lens) curpos)
               (inc skip)
               (mod (+ (first lens) skip curpos) (count line))))))

(defn knot-hash-sparse [lens-in line-in]
  (loop [lens lens-in
         line line-in
         skip 0
         curpos 0
         count 0]
    (let [res (knot-hash-single lens line skip curpos)]
      (if (= count 63) (res :line)
          (recur
           lens
           (res :line)
           (res :skip)
           (res :curpos)
           (inc count))))))

(defn knot-hash-dense [sparse]
  (->> (for [line (partition 16 sparse)]
         (reduce bit-xor line))
       (flatten)))

(defn hexify [iseq]
  (apply str (for [i iseq] (format "%02x" i))))

(defn solve2 [lens]
  (->> (knot-hash-sparse (get-lens lens) (range 256))
       (knot-hash-dense)
       (hexify)))

(comment 
  (solve '(197 97 204 108 1 29 5 71 0 50 2 255 248 78 254 63) (range 256))
  ;; correct answer is 40132
  ;; "Elapsed time: 14.610573 msecs"
  (solve2 "197,97,204,108,1,29,5,71,0,50,2,255,248,78,254,63" )
  ;; correct answer is 35b028fe2c958793f7d5a61d07a008c8
  ;; "Elapsed time: 428.57963 msecs"

  ;;(println (solve2 "flqrgnkx-0" ))
  )
