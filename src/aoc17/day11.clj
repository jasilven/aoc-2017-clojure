(ns aoc17.day11
  (:require [clojure.string :as str]))

(defn get-data [fname]
  (-> (slurp fname)
      (str/trim)
      (str/split #",")))

(defn init-freqs [data]
  (-> (frequencies data)
      (update "n" #(if (nil? %) 0 %))
      (update "ne" #(if (nil? %) 0 %))
      (update "se" #(if (nil? %) 0 %))
      (update "s" #(if (nil? %) 0 %))
      (update "sw" #(if  (nil? %) 0 %))
      (update "nw" #(if (nil? %) 0 %))))

(defn eliminate-opposites [freq]
  (let [ns-min (min (freq "n") (freq "s"))
        nesw-min (min (freq "ne") (freq "sw"))
        nwse-min (min (freq "nw") (freq "se"))]
    (-> freq
        (assoc "n" (- (freq "n") ns-min) "s" (- (freq "s") ns-min))
        (assoc "ne" (- (freq "ne") nesw-min) "sw" (- (freq "sw") nesw-min))
        (assoc "nw" (- (freq "nw") nwse-min) "se" (- (freq "se") nwse-min)))))

(defn update-shortcuts [freq]
  (loop [f freq
         scuts [["ne" "nw" "n"]
                ["se" "sw" "s"]
                ["n" "se" "ne"]
                ["n" "sw" "nw"]
                ["s" "ne" "se"]
                ["s" "nw" "sw"]]]
    (if (empty? scuts) f
        (let [d1 (ffirst scuts)
              d2 (second (first scuts))
              min-d1d2 (min (f d1) (f d2))
              sc (last (first scuts))]
          (recur (assoc f d1 (- (f d1) min-d1d2)
                        d2 (- (f d2) min-d1d2)
                        sc (+ (f sc) min-d1d2))
                 (rest scuts))))))

(defn solve [data]
  (as-> data $
    (init-freqs $)
    (eliminate-opposites $)
    (update-shortcuts $)
    (vals $)
    (reduce + $)))

(defn solve2 [data]
  (loop [d data
         res 0]
    (if (empty? d) res
        (recur (pop d)
               (max (solve d) res)))))
(comment
  (solve (get-data "resources/day11_input.csv"))
  ;; correct answer is 824
  ;; "Elapsed time: 20.614848 msecs"

  (solve2 (get-data "resources/day11_input.csv"))
  ;; correct answer is 1548
  ;; "Elapsed time: 15617.499066 msecs"
  )
