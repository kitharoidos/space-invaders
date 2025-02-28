(ns space-invaders.main
  (:gen-class)
  (:require [com.widdindustries.slf4clj.core :as log]
            [space-invaders.model.ctx :as ctx]
            [space-invaders.model.canvas :as canvas]
            [space-invaders.model.matrix :as matrix]
            [space-invaders.model.match :as match]
            [space-invaders.schema :as schema])
  (:import (clojure.lang ExceptionInfo)))

(defn -main [& args]
  (if (= (first args) "--help")
    (println schema/documentation)
    (try
      (let [{:keys [known-invaders radar-sample tolerance]} (ctx/ctx args)
            canvas (canvas/canvas (map matrix/size known-invaders) (matrix/size radar-sample))
            padded-radar-sample (matrix/pad-submat (canvas/size canvas) radar-sample (canvas/padding canvas))]
        (->> known-invaders
             (transduce (comp (mapcat (fn [invader]
                                        (->> (matrix/submat-positions (canvas/size canvas) (matrix/size invader))
                                             (map (partial matrix/pad-submat (canvas/size canvas) invader)))))
                              (remove (fn [padded-invader]
                                        (->> (map #(if (match/mismatch? %1 %2) 1 0)
                                                  (flatten padded-invader)
                                                  (flatten padded-radar-sample))
                                             (reductions + 0)
                                             (some (partial < tolerance))))))
                        (completing canvas/draw-padded-invader)
                        canvas)
             (canvas/render)
             (println)))
      (catch ExceptionInfo e
        (log/error (ex-message e) (ex-data e) e)
        (println schema/documentation))
      (catch Exception e
        (log/error (ex-message e) (ex-data e) e)
        (println schema/documentation)))))