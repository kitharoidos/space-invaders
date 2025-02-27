(ns space-invaders.main
  (:gen-class)
  (:require [space-invaders.cli :as cli]))

(defn -main [& args]
  (->> (cli/args)
       (cli/with-args args)))