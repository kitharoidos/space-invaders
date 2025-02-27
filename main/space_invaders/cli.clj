(ns space-invaders.cli
  (:require [clojure.pprint :refer [pprint]]))

(def ^:dynamic *args* nil)

(defn- parse [args]
  args)

(defn args []
  *args*)

(defmacro with-args [args computation]
  `(binding [*args* (~parse ~args)]
    (-> ~computation (pprint))))