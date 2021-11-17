(ns leetcode.two-sum)

(defn two-sum
  [array target-sum]
  (loop [idx 0
         calc {}]
    (when (< idx (count array))
      (let [comp (- target-sum (nth array idx))]
        (if (contains? calc comp)
          #{idx (get calc comp)}
          (recur (inc idx) (assoc calc (nth array idx) idx)))))))

;; (defn calc-comp
;;   [target-sum result calc idx value]
;;   (if (nil? result)
;;     (let [comp (- target-sum value)]
;;       (if (contains? calc comp)
;;         [#{idx (get calc comp)} calc]
;;         [nil (assoc calc value idx)]))
;;     [result calc]))

;; (defn two-sum
;;   [array target-sum]
;;   (let [func (fn [[result calc] idx value] 
;;                (calc-comp target-sum result calc idx value))]
;;     (-> (reduce-kv func [nil {}] array)
;;         (first))))

;; (two-sum [4 2 1 5 6 7 -3 2] 13)
;; ;; => #{4 5}
;; (two-sum [4 2 1 5 6 7 -3 2] 6)
;; ;; => #{0 1}
;; (two-sum [4 2 1 5 6 7 -3 2] 9)
;; ;; => #{0 3}
