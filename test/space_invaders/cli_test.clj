(ns space-invaders.cli-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [space-invaders.cli :as cli])
  (:import (clojure.lang ExceptionInfo)))

(deftest missing-options-test
  (testing "No missing options"
    (is (do (cli/with-args
              ["--known-invaders" (io/resource "cli/valid-known-invaders")
               "--radar-sample" (io/resource "cli/valid-radar-sample")
               "--tolerance" "10"]
              nil)
            true)))
  (testing "Missing --known-invaders"
    (is (thrown? ExceptionInfo
                 (cli/with-args
                   ["--radar-sample" (io/resource "cli/valid-radar-sample")
                    "--tolerance" "10"]
                   nil))))
  (testing "Missing --radar-sample"
    (is (thrown? ExceptionInfo
                 (cli/with-args
                   ["--known-invaders" (io/resource "cli/valid-known-invaders")
                    "--tolerance" "10"]
                   nil))))
  (testing "Missing --tolerance"
    (is (thrown? ExceptionInfo
                 (cli/with-args
                   ["--known-invaders" (io/resource "cli/valid-known-invaders")
                    "--radar-sample" (io/resource "cli/valid-radar-sample")]
                   nil)))))

(deftest invalid-known-invaders-test
  (testing "Known invaders empty"
    (is (thrown? ExceptionInfo
                 (cli/with-args
                   ["--known-invaders" (io/resource "cli/empty")
                    "--radar-sample" (io/resource "cli/valid-radar-sample")
                    "--tolerance" "10"]
                   nil))))
  (testing "One known invader invalid"
    (is (thrown? ExceptionInfo
                 (cli/with-args
                   ["--known-invaders" (io/resource "cli/one-known-invader-invalid")
                    "--radar-sample" (io/resource "cli/valid-radar-sample")
                    "--tolerance" "10"]
                   nil)))))

(deftest invalid-radar-sample-test
  (testing "Radar sample not a matrix"
    (is (thrown? ExceptionInfo
                 (cli/with-args
                   ["--known-invaders" (io/resource "cli/valid-known-invaders")
                    "--radar-sample" (io/resource "cli/radar-sample-not-a-matrix")
                    "--tolerance" "10"]
                   nil))))
  (testing "Radar sample with invalid chars"
    (is (thrown? ExceptionInfo
                 (cli/with-args
                   ["--known-invaders" (io/resource "cli/valid-known-invaders")
                    "--radar-sample" (io/resource "cli/radar-sample-with-invalid-chars")
                    "--tolerance" "10"]
                   nil)))))

(deftest invalid-tolerance-test
  (testing "Negative tolerance"
    (is (thrown? ExceptionInfo (cli/with-args
                                 ["--known-invaders" (io/resource "cli/valid-known-invaders")
                                  "--radar-sample" (io/resource "cli/valid-radar-sample")
                                  "--tolerance" "-1"]
                                 nil))))
  (testing "Fractional tolerance"
    (is (thrown? ExceptionInfo (cli/with-args
                                 ["--known-invaders" (io/resource "cli/valid-known-invaders")
                                  "--radar-sample" (io/resource "cli/valid-radar-sample")
                                  "--tolerance" "0.1"]
                                 nil)))))