(ns space-invaders.model.match-test
  (:require [clojure.test :refer :all]
            [space-invaders.model.match :as match]))

(deftest mismatch-test
  (testing "Mismatch detection"
    (let [invader
          [[\o \o]
           [\- \-]]

          radar-sample
          [[\- \o]
           [\o \-]]]
      (is (= (->> (map #(if (match/mismatch? %1 %2) 1 0)
                       (flatten invader)
                       (flatten radar-sample))
                  (reduce + 0))
             2)))))