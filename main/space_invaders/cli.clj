(ns space-invaders.cli
  (:require [clojure.pprint :refer [pprint]]
            [malli.core :as m]
            [malli.transform :as mt]
            [space-invaders.malli.transform :as smt]
            [space-invaders.schema :as schema]))

(def ^:dynamic *args* nil)

(def ^:private coercer
  (m/coercer schema/args (mt/transformer smt/matrix-transformer smt/matrices-transformer mt/string-transformer mt/default-value-transformer)))

(defn- coerce [args]
  (-> (into {} (map vec) (partition 2 args))
      (coercer)))

(defn args []
  *args*)

(defn help! []
  (println schema/documentation))

(defmacro with-args [args computation]
  `(binding [*args* (~coerce ~args)]
    (-> ~computation (pprint))))