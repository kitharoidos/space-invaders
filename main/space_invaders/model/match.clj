(ns space-invaders.model.match)

(defn max-pixel-mismatches
  "Given 'tolerance' in percents of 'invader' pixel count, returns the maximum number of allowed pixel mismatches for 'invader'."
  [invader tolerance]
  (let [pixel-count (transduce (map count) + 0 invader)]
    (quot (* tolerance pixel-count) 100)))

(defn mismatch?
  "Compare 'invader' with 'radar-sample-submat' pixel-by-pixel and return true iff the mismatches exceed 'max-pixel-mismatches'."
  [invader radar-sample-submat max-pixel-mismatches]
  (->> (map (fn [invader-pixel radar-pixel]
              (if (or (and (= invader-pixel \o) (not= radar-pixel \o))
                      (and (= invader-pixel \-) (= radar-pixel \o)))
                1
                0))
            (flatten invader)
            (flatten radar-sample-submat))
       (reductions + 0)
       (some (partial < max-pixel-mismatches))))