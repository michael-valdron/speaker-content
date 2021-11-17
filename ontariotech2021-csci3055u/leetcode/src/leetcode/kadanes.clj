(ns leetcode.kadanes)

(defn update-sums
  [sums value]
  (let [running-sum (max value (+ (:running-sum sums) value))]
   (-> (assoc sums :running-sum running-sum)
       (update :max-sum (partial max running-sum)))))

(defn kadanes-algorithm
  [array]
  (if (empty? array) 0
      (let [sums {:running-sum (first array)
                  :max-sum (first array)}]
        (-> (reduce update-sums sums (rest array)) 
            :max-sum))))

;; (kadanes-algorithm [-5 4 1 -6 5 -1 4 3 2])
;; => 13 ([5 -1 4 3 2])
