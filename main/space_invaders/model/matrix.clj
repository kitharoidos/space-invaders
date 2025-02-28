(ns space-invaders.model.matrix)

(defn size [mat]
  {:w (count (first mat))
   :h (count mat)})

(defn pad-submat [mat-size submat submat-pos]
  (let [submat-size (size submat)
        left (:x submat-pos)
        top (:y submat-pos)
        right (- (:w mat-size) (:x submat-pos) (:w submat-size))
        bottom (- (:h mat-size) (:y submat-pos) (:h submat-size))]
    (concat (repeat top (repeat (:w mat-size) nil))
            (map #(concat (repeat left nil) % (repeat right nil)) submat)
            (repeat bottom (repeat (:w mat-size) nil)))))

(defn submat-positions [mat-size submat-size]
  (for [x (range (inc (- (:w mat-size) (:w submat-size))))
        y (range (inc (- (:h mat-size) (:h submat-size))))]
    {:x x, :y y}))