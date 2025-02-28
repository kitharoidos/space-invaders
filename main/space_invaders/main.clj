(ns space-invaders.main
  (:gen-class)
  (:require [com.widdindustries.slf4clj.core :as log]
            [space-invaders.cli :as cli])
  (:import (clojure.lang ExceptionInfo)))

(defn -main [& args]
  (if (= (first args) "--help")
    (cli/help!)
    (try
      (->> (cli/args)
           (cli/with-args args))
      (catch ExceptionInfo e
        (log/error (ex-message e) (ex-data e) e)
        (cli/help!))
      (catch Exception e
        (log/error (ex-message e) (ex-data e) e)
        (cli/help!)))))