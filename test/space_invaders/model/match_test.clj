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
      (is (= (->> (apply concat radar-sample)
                  (reduce match/update-match (match/new-match invader-pattern))
                  (match/mismatches))
             2)))))