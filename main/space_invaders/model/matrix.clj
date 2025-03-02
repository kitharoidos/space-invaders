(ns space-invaders.model.matrix)

(defn- submat-margin [mat-size submat-size submat-pos]
  {:l (:x submat-pos)
   :t (:y submat-pos)
   :r (- (:w mat-size) (:x submat-pos) (:w submat-size))
   :b (- (:h mat-size) (:y submat-pos) (:h submat-size))})

(defn size [mat]
  {:w (count (first mat))
   :h (count mat)})

(defn pad-submat
  "Pad 'submat' positioned at 'submat-pos' within a matrix of 'mat-size' so that it has 'mat-size'."
  [mat-size submat submat-pos]
  (let [{:keys [w]} mat-size
        {:keys [l t r b]} (submat-margin mat-size (size submat) submat-pos)]
    (concat (repeat t (repeat w nil))
            (map #(concat (repeat l nil) % (repeat r nil)) submat)
            (repeat b (repeat w nil)))))

(defn submat-positions
  "All possible positions of a submatrix of 'submat-size' in a matrix of 'mat-size'."
  [mat-size submat-size]
  (for [x (range (inc (- (:w mat-size) (:w submat-size))))
        y (range (inc (- (:h mat-size) (:h submat-size))))]
    {:x x, :y y}))

(defn submat
  "Submatrix of 'submat-size' positioned at 'submat-pos' within the matrix 'mat'."
  [mat submat-size submat-pos]
  (let [{:keys [w h]} submat-size
        {:keys [l t]} (submat-margin (size mat) submat-size submat-pos)]
    (sequence (comp (drop t)
                    (take h)
                    (map (partial sequence (comp (drop l) (take w)))))
              mat)))