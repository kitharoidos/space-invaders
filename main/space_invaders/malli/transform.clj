(ns space-invaders.malli.transform
  (:require [clojure.string :as str]
            [malli.core :as m]
            [malli.transform :as mt]))

(defn- parse-matrix [text]
  (->> (str/split-lines text)
       (mapv vec)))

(defn- decode-matrix [schema _]
  (let [{:keys [matrix?]} (m/properties schema)]
    (if matrix?
      (fn [file-path]
        (parse-matrix (slurp file-path)))
      identity)))

(def matrix-transformer
  (mt/transformer
    {:name "read matrix from text file"
     :decoders
     {:vector
      {:compile decode-matrix}
      :and
      {:compile decode-matrix}}}))

(defn- decode-matrices [schema _]
  (let [{:keys [matrices?]} (m/properties schema)]
    (if matrices?
      (fn [file-path]
        (->> (str/split (slurp file-path) #"\n{2,}")
             (mapv parse-matrix)))
      identity)))

(def matrices-transformer
  (mt/transformer
    {:name "read matrices from text file"
     :decoders
     {:vector
      {:compile decode-matrices}
      :and
      {:compile decode-matrices}}}))