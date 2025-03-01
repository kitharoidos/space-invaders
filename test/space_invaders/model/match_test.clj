(ns space-invaders.model.match-test
  (:require [clojure.test :refer :all]
            [space-invaders.model.match :as match]))

(deftest mismatch-test
  (testing "Mismatch detection"
    (let [invader
          [[\o \o]
           [\- \-]]

          radar-sample-submat
          [[\- \o]
           [\o \-]]

          tolerance 1]
      (is (match/mismatch? invader radar-sample-submat tolerance)))))