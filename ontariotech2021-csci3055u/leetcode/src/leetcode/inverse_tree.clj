(ns leetcode.inverse-tree
  (:require [leetcode.inverse-tree.utils :refer :all]))

(defn swap-nodes!
  [root]
  (let [temp (-> root :left (deref))]
    (->> (:right root)
         (deref)
         (set-left! root))
    (set-right! root temp)))

(defn inverse-binary-tree
  [root]
  (when-not (nil? root)
    (swap-nodes! root)
    (-> root :left (deref) (inverse-binary-tree))
    (-> root :right (deref) (inverse-binary-tree))))


;; (let [labels [27 14 15 10 11 4 3 2]
;;       root (build-tree labels)]
;;   (print-tree root)
;;   (inverse-binary-tree root)
;;   (print-tree root)))
