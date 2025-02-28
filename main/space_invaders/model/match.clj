(ns space-invaders.model.match)

(defn mismatch? [invader-pixel radar-pixel]
  (or (and (= invader-pixel \o) (not= radar-pixel \o))
      (and (= invader-pixel \-) (= radar-pixel \o))))