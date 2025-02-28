(ns space-invaders.model.matrix)

(defn height [matrix]
  (count matrix))

(defn width [matrix]
  (count (first matrix)))

(defn size [matrix]
  {:w (width matrix)
   :h (height matrix)})

(defn pad [{:keys [w h] :as matrix-size} submatrix {:keys [x y] :as submatrix-position}]
  (let [left x
        top y
        right (- w x (width submatrix))
        bottom (- h y (height submatrix))]
    (concat (repeat top (repeat w nil))
            (map #(concat (repeat left nil) % (repeat right nil)) submatrix)
            (repeat bottom (repeat w nil)))))

(defn positions [matrix-size submatrix-size]
  (for [x (range (inc (- (:w matrix-size) (:w submatrix-size))))
        y (range (inc (- (:h matrix-size) (:h submatrix-size))))]
    {:x x, :y y}))