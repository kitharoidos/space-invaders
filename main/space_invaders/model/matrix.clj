(ns space-invaders.model.matrix)

(defn height [matrix]
  (count matrix))

(defn width [matrix]
  (count (first matrix)))

(defn size [matrix]
  {:w (width matrix)
   :h (height matrix)})

(defn lazy-pad [{:keys [w h] :as matrix-size} submatrix {:keys [x y] :as submatrix-position}]
  (let [left x
        top y
        right (- w x (width submatrix))
        bottom (- h y (height submatrix))]
    (lazy-cat (repeat top (repeat w nil))
              (map #(lazy-cat (repeat left nil) % (repeat right nil)) submatrix)
              (repeat bottom (repeat w nil)))))

(defn positions [{:keys [w h] :as size}]
  (for [x (range w), y (range h)]
    {:x x, :y y}))