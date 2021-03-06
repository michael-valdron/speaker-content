(ns leetcode.inverse-tree.utils)

(defprotocol INode
  (set-left! [this node])
  (set-right! [this node]))

(defrecord Node [value left right]
  INode
  (set-left! [this node]
    (reset! (:left this) node))
  (set-right! [this node]
    (reset! (:right this) node)))

(defn empty-node
  [value]
  (->Node value (atom nil) (atom nil)))

;; Referenced from: binarytree.build (Python)
(defn build-tree
  [labels]
  (let [nodes (mapv empty-node labels)]
    (doseq [index (range 1 (count labels))
            :let [node (nth nodes index)]]
      (when-not (nil? node)
        (let [parent-idx (-> (- index 1)
                             (/ 2)
                             (Math/floor))
              parent (nth nodes parent-idx)]
          (when (nil? parent)
            (println (format "parent node missing at index %d" parent-idx)))
          (if (zero? (mod index 2))
            (set-left! parent node)
            (set-right! parent node)))))
    (first nodes)))

;; For printing, referenced at: https://codereview.stackexchange.com/questions/120369/printing-binary-trees ;;

(defn- end-col
  "Returns one plus the maximum column occupied by the sparse string entry x."
  [x]
  (let [[[_ col] s] x]
    (+ col (count s))))

(defn- min-corner
  "Returns a vector of the minimum non-empty row and column in sparse-string."
  [sparse-string]
  (mapv #(apply min (map % (keys sparse-string)))
        [first second]))

(defn- max-corner
  "Returns a vector of one plus the maximum non-empty row and column in
  sparse-string."
  [sparse-string]
  (mapv #(apply max (map % sparse-string))
        [(comp inc first key) end-col]))

(defn- fill
  "Returns a string of newlines and spaces to fill the gap between entries x and
  y in a sparse string whose minimum corner is corner."
  [corner x y]
  (let [[_ min-col] corner
        [[prev-row _] _] x
        [[next-row next-col] _] y]
    (apply str (concat (repeat (- next-row prev-row) \newline)
                       (repeat (- next-col
                                  (if (== prev-row next-row)
                                    (end-col x)
                                    min-col))
                               \space)))))

(defn- sparse-str
  "Converts sparse-string to a string."
  [sparse-string]
  (let [corner (min-corner sparse-string)]
    (->> sparse-string
         (sort-by key)
         (cons [corner ""])
         (partition 2 1)
         (map (fn [[x y]] (str (fill corner x y) (val y))))
         (apply str))))

(defn- shift
  "Creates and returns a sparse string by adding offset to the position of each
  entry in sparse-string."
  [offset sparse-string]
  (into {} (map (fn [[pos s]]
                  [(mapv + pos offset) s])
                sparse-string)))

(defn- vert-gap
  "Returns the minimum vertical gap that can be used in combining the left and
  right tree strings."
  [left right]
  (if (and left right)
    (max 1 (quot (- (second (max-corner left))
                    (second (min-corner right)))
                 2))
    1))

(def ^:private directions {:left - :right +})

(defn- diagonal
  "Returns a diagonal sparse string with the top end located at corner."
  [direction corner length character]
  (let [[first-row first-col] corner]
    (into {} (map (fn [n]
                    [[(+ first-row n)
                      ((directions direction) first-col n)]
                     (str character)])
                  (range length)))))

(defn- leg
  "Returns a sparse string from shifting tree-string according to direction,
  vert-gap, and value-height, merged with a diagonal strut."
  [direction tree-string vert-gap value-height]
  (merge (shift [(+ value-height vert-gap)
                 ((directions direction) (inc vert-gap))]
                tree-string)
         (diagonal direction
                   [value-height ((directions direction) 1)]
                   vert-gap
                   ({:left \/ :right \\} direction))))

(defn- assemble
  "Assembles a complete tree string from the tree strings of a value, left
  subtree, and right subtree."
  [value left right]
  (if (or left right)
    (let [[value-height _] (max-corner value)
          vert-gap (vert-gap left right)]
      (merge value
             (when left
               (leg :left left vert-gap value-height))
             (when right
               (leg :right right vert-gap value-height))))
    value))

(defn- tree-string
  "Creates and returns a tree string from node."
  [node]
  (let [{:keys [value left right]} node
        s (str value)]
    (apply assemble
           {[0 (- (quot (count s) 2))] s}
           (map #(when % (tree-string %))
                [@left @right]))))

(defn print-tree 
  [node]
  (-> (tree-string node)
      (sparse-str)
      (println)))
