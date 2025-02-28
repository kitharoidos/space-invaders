(ns space-invaders.main
  (:gen-class)
  (:require [com.widdindustries.slf4clj.core :as log]
            [space-invaders.cli :as cli]
            [space-invaders.model.canvas :as canvas]
            [space-invaders.model.matrix :as matrix]
            [space-invaders.model.match :as match])
  (:import (clojure.lang ExceptionInfo)))

(defn -main [& args]
  (if (= (first args) "--help")
    (cli/help!)
    (try
      (cli/with-args
        args
        (fn []
          (let [canvas (canvas/new-canvas (map matrix/size (cli/known-invaders)) (matrix/size (cli/radar-sample)))
                radar-sample (matrix/pad (canvas/size canvas) (cli/radar-sample) (canvas/padding canvas))]
            (->> (cli/known-invaders)
                 (transduce (comp (mapcat (fn [invader]
                                            (->> (matrix/positions (canvas/size canvas) (matrix/size invader))
                                                 (map (partial matrix/pad (canvas/size canvas) invader)))))
                                  (remove (fn [pattern]
                                            (->> (map #(if (match/mismatch? %1 %2) 1 0) (flatten pattern) (flatten radar-sample))
                                                 (reductions + 0)
                                                 (some (partial < (cli/tolerance)))))))
                            (completing canvas/add-invader)
                            canvas)
                 (canvas/render)))))
      (catch ExceptionInfo e
        (log/error (ex-message e) (ex-data e) e)
        (cli/help!))
      (catch Exception e
        (log/error (ex-message e) (ex-data e) e)
        (cli/help!)))))