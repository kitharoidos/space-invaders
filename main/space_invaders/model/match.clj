(ns space-invaders.model.match)

(defn mismatch? [pattern-pixel radar-pixel]
  (or (and (= pattern-pixel \o) (not= radar-pixel \o))
      (and (= pattern-pixel \-) (= radar-pixel \o))))