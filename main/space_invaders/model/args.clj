(ns space-invaders.model.args
  (:require [clojure.set :as set]
            [malli.core :as m]
            [malli.transform :as mt]
            [space-invaders.malli.transform :as smt]
            [space-invaders.schema :as schema]))

(def ^:private coercer
  (m/coercer schema/args (mt/transformer smt/matrix-transformer smt/matrices-transformer mt/string-transformer mt/default-value-transformer)))

(defn coerce [args]
  (-> (into {} (map vec) (partition 2 args))
      (coercer)
      (set/rename-keys {"--known-invaders" :known-invaders
                        "--radar-sample" :radar-sample
                        "--tolerance" :tolerance})))