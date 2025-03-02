(ns space-invaders.model.match-test
  (:require [clojure.test :refer :all]
            [space-invaders.model.match :as match]))

(deftest max-pixel-mismatches-test
  (testing "Calculation of maximum allowed pixel mismatches from tolerance given in percents"
    (let [invader
          [[\- \- \- \- \-]
           [\- \o \o \o \-]
           [\- \o \o \o \-]
           [\- \o \o \o \-]
           [\- \- \- \- \-]]]
      (is (= (match/max-pixel-mismatches invader 0) 0))
      (is (= (match/max-pixel-mismatches invader 20) 5))
      (is (= (match/max-pixel-mismatches invader 33) 8))
      (is (= (match/max-pixel-mismatches invader 100) 25)))))

(deftest mismatch-test
  (testing "Mismatch detection"
    (let [invader
          [[\o \o]
           [\- \-]]

          radar-sample-submat
          [[\- \o]
           [\o \-]]

          max-pixel-mismatches 1]
      (is (match/mismatch? invader radar-sample-submat max-pixel-mismatches)))))