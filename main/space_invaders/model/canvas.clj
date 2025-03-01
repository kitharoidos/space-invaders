(ns space-invaders.model.canvas)

(defn canvas [size padding]
  (let [{:keys [w h]} size
        {:keys [x y]} padding]
    (->> (concat (repeat y (repeat w \space))
                 (repeat (- h y y) (concat (repeat x \space) (repeat (- w x x) \-) (repeat x \space)))
                 (repeat y (repeat w \space)))
         (into [] (comp (interpose [\newline])
                        (mapcat identity))))))

(defn draw-padded-invader [canvas padded-invader]
  (->> padded-invader
       (sequence (comp (interpose [\newline])
                       (mapcat identity)
                       (map #{\o \newline})))
       (mapv #(or %2 %1) canvas)))

(defn render [canvas]
  (apply str canvas))