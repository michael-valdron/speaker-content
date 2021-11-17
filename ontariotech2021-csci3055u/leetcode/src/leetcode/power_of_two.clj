(ns leetcode.power-of-two)

(defn power-of-two?
  [num]
  (cond
    (= num 0) true
    (< num 1) false
    :else (let [k (/ (Math/log num) (Math/log 2))]
            (<= (- k (Math/floor (/ k 1))) 1E-10))))

;; true
;; (power-of-two? 0)
;; (power-of-two? 1)
;; (power-of-two? 2)
;; (power-of-two? 4)
;; (power-of-two? 8)
;; (power-of-two? 16)
;; (power-of-two? 32)
;; ...
;; (power-of-two? 512)
;; (power-of-two? 1024)

;; false
;; (power-of-two? 3)
;; (power-of-two? -1)
;; (power-of-two? 17)
;; (power-of-two? 26)
