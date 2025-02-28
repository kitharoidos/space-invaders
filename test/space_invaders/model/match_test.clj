(ns space-invaders.model.match-test
  (:require [clojure.test :refer :all]
            [space-invaders.model.match :as match]))

(deftest match-test
  (testing "Match"
    (let [invader-pattern
          [[\o \o]
           [\- \-]]

          radar-sample
          [[\- \o]
           [\o \-]]]
      (is (= (->> (map #(if (match/mismatch? %1 %2) 1 0) (flatten invader-pattern) (flatten radar-sample))
                  (reduce + 0))
             2)))))