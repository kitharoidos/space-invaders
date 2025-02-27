(ns main
  (:require
    [clojure.tools.build.api :as build]))

(def main 'space-invaders.main)
(def class-dir "target/classes")
(def basis (build/create-basis {:project "deps.edn"}))
(def uber-file "target/space-invaders.jar")

(defn clean [_]
  (build/delete {:path "target"}))

(defn uberjar [_]
  (clean nil)
  (build/copy-dir {:src-dirs   ["main"]
                   :target-dir class-dir})
  (build/compile-clj {:basis     basis
                      :src-dirs  ["main"]
                      :class-dir class-dir})
  (build/uber {:class-dir class-dir
               :uber-file uber-file
               :basis     basis
               :main      main}))
