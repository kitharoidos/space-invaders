(ns space-invaders.model.canvas)

(defn canvas [known-invader-sizes radar-size]
  (let [max-invader-width (apply max (map :w known-invader-sizes))
        max-invader-height (apply max (map :h known-invader-sizes))
        padding {:x (dec max-invader-width)
                 :y (dec max-invader-height)}
        size {:w (+ (dec max-invader-width) (:w radar-size) (dec max-invader-width))
              :h (+ (dec max-invader-height) (:h radar-size) (dec max-invader-height))}]
    {:padding padding
     :size size
     :radar-size radar-size
     :img (->> (concat (repeat (:y padding)
                               (repeat (:w size) \space))
                       (repeat (:h radar-size)
                               (concat
                                 (repeat (:x padding) \space) (repeat (:w radar-size) \-) (repeat (:x padding) \space)))
                       (repeat (:y padding)
                               (repeat (:w size) \space)))
               (into [] (comp (interpose [\newline])
                              (mapcat identity))))}))

(defn padding [canvas]
  (:padding canvas))

(defn size [canvas]
  (:size canvas))

(defn draw-padded-invader [canvas padded-invader]
  (-> canvas
      (update :img (fn [img]
                     (->> padded-invader
                          (sequence (comp (interpose [\newline])
                                          (mapcat identity)
                                          (map #{\o \newline})))
                          (mapv #(or %2 %1) img))))))

(defn render [canvas]
  (apply str (:img canvas)))