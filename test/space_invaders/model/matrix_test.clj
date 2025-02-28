(ns space-invaders.model.matrix-test
  (:require [clojure.test :refer :all]
            [space-invaders.model.matrix :as matrix]))

(deftest lazy-pad-test
  (testing "Matrix padding"
    (is (= (->> (matrix/lazy-pad {:w 4, :h 4}
                                 [[1 1]
                                  [1 1]]
                                 {:x 1, :y 1})
                (mapv vec))
           [[nil nil nil nil]
            [nil 1 1 nil]
            [nil 1 1 nil]
            [nil nil nil nil]]))
    (is (= (->> (matrix/lazy-pad {:w 3, :h 2}
                                 [[1 1]
                                  [1 1]]
                                 {:x 1, :y 0})
                (mapv vec))
           [[nil 1 1]
            [nil 1 1]]))))

(deftest positions-test
  (testing "Matrix positions"
    (is (= (->> (matrix/positions {:w 2, :h 2})
                (into #{}))
           #{{:x 0, :y 0}
             {:x 0, :y 1}
             {:x 1, :y 0}
             {:x 1, :y 1}}))))