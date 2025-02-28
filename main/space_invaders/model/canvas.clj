(ns space-invaders.model.canvas)

(defn new-canvas [known-invader-sizes {:keys [w h] :as radar-size}]
  (let [max-invader-width (apply max (map :w known-invader-sizes))
        max-invader-height (apply max (map :h known-invader-sizes))]
    {:padding {:x (dec max-invader-width)
               :y (dec max-invader-height)}
     :size {:w (+ (dec max-invader-width) w (dec max-invader-width))
            :h (+ (dec max-invader-height) h (dec max-invader-height))}
     :radar-size radar-size
     :layers []}))

(defn size [canvas]
  (:size canvas))

(defn add-background [canvas]
  (let [{:keys [padding size radar-size]} canvas]
    (-> canvas
        (update :layers conj
                (lazy-cat (repeat (:y padding)
                                  (repeat (:w size) \space))
                          (repeat (:h radar-size)
                                  (lazy-cat
                                    (repeat (:x padding) \space) (repeat (:w radar-size) \-) (repeat (:x padding) \space)))
                          (repeat (:y padding)
                                  (repeat (:w size) \space)))))))

(defn add-invaders [canvas invaders]
  (->> invaders
       (reduce (fn [canvas invader]
                 (-> canvas
                     (update :layers conj (map (partial map #{\o}) invader))))
               canvas)))

(defn render [canvas]
  (->> (:layers canvas)
       (map #(->> % (interpose [\newline]) (apply concat)))
       (reduce (fn [lower upper]
                 (map #(or %2 %1) lower upper)))
       (apply str)))