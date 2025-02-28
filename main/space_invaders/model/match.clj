(ns space-invaders.model.match)

(defn new-match [pattern]
  {:mismatches 0
   :pattern pattern
   :suffix (apply concat pattern)})

(defn update-match [match radar-pixel]
  (let [pattern-pixel (first (:suffix match))]
    (cond-> match
      (or (and (= pattern-pixel \o) (not= radar-pixel \o))
          (and (not= pattern-pixel \o) (= radar-pixel \o)))
      (update :mismatches inc)

      :always
      (update :suffix rest))))

(defn mismatches [match]
  (:mismatches match))

(defn pattern [match]
  (:pattern match))