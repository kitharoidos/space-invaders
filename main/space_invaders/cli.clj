(ns space-invaders.cli
  (:require [malli.core :as m]
            [malli.transform :as mt]
            [space-invaders.malli.transform :as smt]
            [space-invaders.schema :as schema]))

(def ^:dynamic *args* nil)

(def ^:private coercer
  (m/coercer schema/args (mt/transformer smt/matrix-transformer smt/matrices-transformer mt/string-transformer mt/default-value-transformer)))

(defn- coerce [args]
  (-> (into {} (map vec) (partition 2 args))
      (coercer)))

(defn known-invaders []
  (get *args* "--known-invaders"))

(defn radar-sample []
  (get *args* "--radar-sample"))

(defn tolerance []
  (get *args* "--tolerance"))

(defn help! []
  (println schema/documentation))

(defn with-args [args computation]
  (binding [*args* (coerce args)]
    (println (computation))))