(ns space-invaders.model.match)

(defn mismatch? [invader radar-sample-submat tolerance]
  (->> (map (fn [invader-pixel radar-pixel]
              (if (or (and (= invader-pixel \o) (not= radar-pixel \o))
                      (and (= invader-pixel \-) (= radar-pixel \o)))
                1
                0))
            (flatten invader)
            (flatten radar-sample-submat))
       (reductions + 0)
       (some (partial < tolerance))))