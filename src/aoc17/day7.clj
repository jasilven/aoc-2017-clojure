(ns aoc17.day7
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :refer :all]))

(defn read-input-file [fname]
  (with-open [reader (io/reader fname)]
    (for [line (doall (line-seq reader))]
      (-> line
          (str/replace #"[->,]" "")
          (str/replace #"\((.+)\)" "$1")
          (str/split #"\s+")
          (update 1 #(Integer/parseInt %))))))

(defn find-roots [nodes]
  (->> (for [node nodes]
         (if (> (count node) 2)
           (first node)))
       (filter some?)))

(defn find-children [nodes]
  (->> (for [node nodes]
         (if (> (count node) 2)
           (drop 2 node)))
       (filter some?)
       (flatten)))

(defn solve [fname]
  (let [roots (find-roots (read-input-file fname))
        children (find-children (read-input-file fname))]
    (first (difference (set roots) (set children)))))

(defn find-roots-with-children-cnt [nodes]
  (->> (for [node nodes]
         (if (> (count node) 2)
           [(first node) (count (drop 2 node))]))
       (filter some?)
       (filter #(= (last %) 2))))

(defn make-map [fname]
  (->> (for [node (read-input-file fname) ] 
         [(first node) (rest node)])
       (apply concat)
       (apply hash-map)))

(defn calc-tree [nmap key]
  (loop [jono (cons key '())
         nodes nmap
         result 0]
    (if (empty? jono)
      result
      (recur (concat (rest (nodes (first jono)))
                     (rest jono))
             nodes
             (+ result (first (nodes (first jono))))))))

(defn find-next [nmap key]
  (as-> (for [key (rest (nmap key))]
          [key (calc-tree nmap key)]) $
    (group-by last $)
    (group-by #(count (val %)) $)
    (get-in $ [1 0 1])
    (ffirst $)))

(defn find-unbalanced-node [nmap-in key]
  (loop [nmap nmap-in 
         root key 
         prev root]
    (if (nil? root)
      prev
      (recur nmap (find-next nmap root) root))))

(defn find-delta [nmap key]
  (->> (for [key (rest (nmap key))]
         [key (calc-tree nmap key)])
       (group-by last)
       (group-by #(count (val %)))
       (reduce #(- (get-in %2 [1 0 0]) (get-in %1 [1 0 0])))))

(defn solve2 [fname]
  (let [nmap (make-map fname)
        root (solve fname)
        delta (find-delta nmap root)
        unbalanced (find-unbalanced-node nmap root)]
    (cond (= 0 delta) nil
          (< delta 0) (+ (first (nmap unbalanced)) delta)
          :else (- (first (nmap unbalanced)) delta))))

(comment
  (solve  "resources/day7_input.txt")
  ;; correct answer is dgoocsw
  ;; "Elapsed time: 48.704749 msecs"

  (solve2  "resources/day7_input.txt")
  ;; correct answer is 1275
  ;; "Elapsed time: 73.619182 msecs"
  )
