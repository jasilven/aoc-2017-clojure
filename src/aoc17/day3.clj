(ns aoc17.day3)

(declare get-value)

(defn get-matrix-rank [input]
  (last (doall
         (for [x (range)
               :when (odd? x)
               :while (< (* x x) input)]
           (+ x 2)))))

(defn solve [n]
  (if (<= n 1) 0
      (let [rank (get-matrix-rank n)]
        (nth
         (->>
          (concat
           (range (dec rank) (int (/ rank 2)) -1)
           (range (int (/ rank 2)) rank 1))
          (rest)
          (repeat 4)
          (cons (dec rank))
          (flatten)
          (reverse)
          (drop 1))
         (- n (+ (* (- rank 2) (- rank 2)) 1))))))

(defn calc-right [curpos positions]
  (+ (get-value [(- (curpos 0) 1) (curpos 1)] positions)
     (get-value [(- (curpos 0) 1) (+ (curpos 1) 1)] positions)
     (get-value [(+ (curpos 0) 1) (+ (curpos 1) 1)] positions)
     (get-value [(curpos 0) (+ (curpos 1) 1)] positions)))

(defn calc-up [curpos positions]
  (+ (get-value [(curpos 0) (- (curpos 1) 1)] positions)
     (get-value [(- (curpos 0) 1) (- (curpos 1) 1)] positions)
     (get-value [(- (curpos 0) 1) (+ (curpos 1) 1)] positions)
     (get-value [(- (curpos 0) 1) (curpos 1)] positions )))

(defn calc-left [curpos positions]
  (+ (get-value [(+ (curpos 0) 1) (curpos 1)] positions)
     (get-value [(curpos 0) (- (curpos 1) 1)] positions)
     (get-value [(- (curpos 0) 1) (- (curpos 1) 1)] positions)
     (get-value [(+ (curpos 0) 1) (- (curpos 1) 1)] positions)))

(defn calc-down [curpos positions]
  (+ (get-value [(curpos 0) (+ (curpos 1) 1)] positions)
     (get-value [(+ (curpos 0) 1) (+ (curpos 1) 1)] positions)
     (get-value [(+ (curpos 0) 1) (- (curpos 1) 1)] positions)
     (get-value [(+ (curpos 0) 1) (curpos 1)] positions)))

(defn get-value [pos positions]
  (let [value (first (vals (first (filter #(= pos (first (keys %)))
                                          positions))))]
    (if (nil? value ) 0 value)))

(defn turn-if-needed [data]
  (if (= (data :count) (data :steps))
    (cond (= (data :move) :right) (-> data
                                      (assoc :move :up)
                                      (assoc :count 0))
          (= (data :move) :up) (-> data
                                   (assoc :move :left)
                                   (assoc :count 0)
                                   (update :steps inc))
          (= (data :move) :left) (-> data
                                     (assoc :move :down)
                                     (assoc :count 0))
          (= (data :move) :down) (-> data
                                     (assoc :move :right)
                                     (assoc :count 0)
                                     (update :steps inc)))
    data))

(defn move-to-next-position [data]
  (-> (cond
        (= (data :move) :right)
        (assoc data :curpos [(inc ((data :curpos) 0))
                             ((data :curpos) 1)])
        (= (data :move) :up) 
        (assoc data :curpos [((data :curpos) 0)
                             (inc ((data :curpos) 1))])
        (= (data :move) :left)
        (assoc data :curpos [(dec ((data :curpos) 0))
                             ((data :curpos) 1)])
        (= (data :move) :down)
        (assoc data :curpos [((data :curpos) 0)
                             (dec ((data :curpos) 1))]))
      (update :count inc)))

(defn calc-position-val [data]
  (cond
    (= (data :move) :right)
    (assoc data :positions
           (cons {(data :curpos)
                  (calc-right (data :curpos) (data :positions))}
                 (data :positions)))
    (= (data :move) :up) 
    (assoc data :positions
           (cons {(data :curpos)
                  (calc-up (data :curpos) (data :positions))}
                 (data :positions)))
    (= (data :move) :left)
    (assoc data :positions
           (cons {(data :curpos)
                  (calc-left (data :curpos) (data :positions))}
                 (data :positions)))
    (= (data :move) :down)
    (assoc data :positions
           (cons {(data :curpos)
                  (calc-down (data :curpos) (data :positions))}
                 (data :positions)))))

(defn solve2 [input]
  (loop [data {:positions [{[0 0] 1}]
               :curpos [0 0]
               :count 0
               :steps 1
               :move :right
               :limit input}]
    (let [last-val (first (vals (first (data :positions))))]
      (if (> last-val (data :limit))
        last-val
        (->> data
             (turn-if-needed)
             (move-to-next-position)
             (calc-position-val)
             (recur))))))

(comment 
  (solve 289326)
  ;; correct answer is 419
  ;; "Elapsed time: 17.360398 msecs"
  (solve2 289326)
  ;; correct answer is 295229
  ;; "Elapsed time: 8.368565 msecs"
  )
