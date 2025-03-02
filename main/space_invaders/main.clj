(ns space-invaders.main
  (:gen-class)
  (:require [com.widdindustries.slf4clj.core :as log]
            [space-invaders.model.args :as args]
            [space-invaders.model.canvas :as canvas]
            [space-invaders.model.matrix :as matrix]
            [space-invaders.model.match :as match]
            [space-invaders.schema :as schema])
  (:import (clojure.lang ExceptionInfo)))

(defn -main [& args]
  (if (= (first args) "--help")
    (println schema/documentation)
    (try
      (let [{:keys [known-invaders radar-sample tolerance]} (args/coerce args)
            known-invader-sizes (map matrix/size known-invaders)
            radar-sample-size (matrix/size radar-sample)
            max-invader-width (apply max (map :w known-invader-sizes))
            max-invader-height (apply max (map :h known-invader-sizes))
            ;'canvas-padding' can accommodate any invader with a single pixel/row/column overlap with 'radar-sample'
            canvas-padding {:x (dec max-invader-width)
                            :y (dec max-invader-height)}
            canvas-size {:w (+ (:x canvas-padding) (:w radar-sample-size) (:x canvas-padding))
                         :h (+ (:y canvas-padding) (:h radar-sample-size) (:y canvas-padding))}
            padded-radar-sample (matrix/pad-submat canvas-size radar-sample canvas-padding)]
        (->> known-invaders
             (transduce (comp (mapcat (fn [invader]
                                        (->> (matrix/submat-positions canvas-size (matrix/size invader))
                                             (map (fn [pos]
                                                    {:invader invader
                                                     :pos pos
                                                     :radar-sample-submat (matrix/submat padded-radar-sample (matrix/size invader) pos)
                                                     :max-pixel-mismatches (match/max-pixel-mismatches invader tolerance)})))))
                              (remove (fn [{:keys [invader radar-sample-submat max-pixel-mismatches]}]
                                        (match/mismatch? invader radar-sample-submat max-pixel-mismatches)))
                              (map (fn [{:keys [invader pos]}]
                                     (matrix/pad-submat canvas-size invader pos))))
                        (completing canvas/draw-invader)
                        (canvas/canvas canvas-size canvas-padding))
             (canvas/render)
             (println)))
      (catch ExceptionInfo e
        (log/error (ex-message e) (ex-data e) e)
        (println schema/documentation))
      (catch Exception e
        (log/error (ex-message e) (ex-data e) e)
        (println schema/documentation)))))