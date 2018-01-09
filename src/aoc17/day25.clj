(ns aoc17.day25)

(defn stateA [{:keys [state curpos tape]}]
  (if (nil? (tape curpos))
    {:state :B :curpos (inc curpos) :tape (assoc tape curpos 1)}
    {:state :C :curpos (dec curpos) :tape (dissoc tape curpos)}))

(defn stateB [{:keys [state curpos tape]}]
  (if (nil? (tape curpos))
    {:state :A :curpos (dec curpos) :tape (assoc tape curpos 1)}
    {:state :C :curpos (inc curpos) :tape tape}))

(defn stateC [{:keys [state curpos tape]}]
  (if (nil? (tape curpos))
    {:state :A :curpos (inc curpos) :tape (assoc tape curpos 1)}
    {:state :D :curpos (dec curpos) :tape (dissoc tape curpos)}))

(defn stateD [{:keys [state curpos tape]}]
  (if (nil? (tape curpos))
    {:state :E :curpos (dec curpos) :tape (assoc tape curpos 1)}
    {:state :C :curpos (dec curpos) :tape tape}))

(defn stateE [{:keys [state curpos tape]}]
  (if (nil? (tape curpos))
    {:state :F :curpos (inc curpos) :tape (assoc tape curpos 1)}
    {:state :A :curpos (inc curpos) :tape tape}))

(defn stateF [{:keys [state curpos tape]}]
  (if (nil? (tape curpos))
    {:state :A :curpos (inc curpos) :tape (assoc tape curpos 1)}
    {:state :E :curpos (inc curpos) :tape tape}))

(defn solve []
  (loop [count 0
         data {:state :A :curpos 0 :tape {}}]
    (if (= count 12134527)
      (apply + (vals (:tape data)))
      (recur (inc count)
             (condp = (:state data)
               :A (stateA data)
               :B (stateB data)
               :C (stateC data)
               :D (stateD data)
               :E (stateE data)
               :F (stateF data))))))

(comment
  (solve)
  ;; correct answer is 5593
  ;; "Elapsed time: 4915.904649 msecs"
  )
