(ns space-invaders.model.args-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [space-invaders.model.args :as args])
  (:import (clojure.lang ExceptionInfo)))

(deftest missing-options-test
  (testing "No missing options"
    (is (args/coerce
          ["--known-invaders" (io/resource "cli/valid-known-invaders")
           "--radar-sample" (io/resource "cli/valid-radar-sample")
           "--tolerance" "10"])))
  (testing "Missing --known-invaders"
    (is (thrown? ExceptionInfo (args/coerce
                                 ["--radar-sample" (io/resource "cli/valid-radar-sample")
                                  "--tolerance" "10"]))))
  (testing "Missing --radar-sample"
    (is (thrown? ExceptionInfo (args/coerce
                                 ["--known-invaders" (io/resource "cli/valid-known-invaders")
                                  "--tolerance" "10"]))))
  (testing "Missing --tolerance"
    (is (thrown? ExceptionInfo (args/coerce
                                 ["--known-invaders" (io/resource "cli/valid-known-invaders")
                                  "--radar-sample" (io/resource "cli/valid-radar-sample")])))))

(deftest invalid-known-invaders-test
  (testing "Known invaders empty"
    (is (thrown? ExceptionInfo (args/coerce
                                 ["--known-invaders" (io/resource "cli/empty")
                                  "--radar-sample" (io/resource "cli/valid-radar-sample")
                                  "--tolerance" "10"]))))
  (testing "One known invader invalid"
    (is (thrown? ExceptionInfo (args/coerce
                                 ["--known-invaders" (io/resource "cli/one-known-invader-invalid")
                                  "--radar-sample" (io/resource "cli/valid-radar-sample")
                                  "--tolerance" "10"])))))

(deftest invalid-radar-sample-test
  (testing "Radar sample not a matrix"
    (is (thrown? ExceptionInfo (args/coerce
                                 ["--known-invaders" (io/resource "cli/valid-known-invaders")
                                  "--radar-sample" (io/resource "cli/radar-sample-not-a-matrix")
                                  "--tolerance" "10"]))))
  (testing "Radar sample with invalid chars"
    (is (thrown? ExceptionInfo (args/coerce
                                 ["--known-invaders" (io/resource "cli/valid-known-invaders")
                                  "--radar-sample" (io/resource "cli/radar-sample-with-invalid-chars")
                                  "--tolerance" "10"])))))

(deftest invalid-tolerance-test
  (testing "Negative tolerance"
    (is (thrown? ExceptionInfo (args/coerce
                                 ["--known-invaders" (io/resource "cli/valid-known-invaders")
                                  "--radar-sample" (io/resource "cli/valid-radar-sample")
                                  "--tolerance" "-1"]))))
  (testing "Fractional tolerance"
    (is (thrown? ExceptionInfo (args/coerce
                                 ["--known-invaders" (io/resource "cli/valid-known-invaders")
                                  "--radar-sample" (io/resource "cli/valid-radar-sample")
                                  "--tolerance" "0.1"]))))
  (testing "Tolerance over 100"
    (is (thrown? ExceptionInfo (args/coerce
                                 ["--known-invaders" (io/resource "cli/valid-known-invaders")
                                  "--radar-sample" (io/resource "cli/valid-radar-sample")
                                  "--tolerance" "101"])))))