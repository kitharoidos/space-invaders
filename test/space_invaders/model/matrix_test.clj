(ns space-invaders.model.matrix-test
  (:require [clojure.test :refer :all]
            [space-invaders.model.matrix :as matrix]))

(deftest pad-submat-test
  (testing "Submatrix padding"
    (is (= (->> (matrix/pad-submat {:w 4, :h 4}
                                   [[1 1]
                                    [1 1]]
                                   {:x 1, :y 1})
                (mapv vec))
           [[nil nil nil nil]
            [nil 1 1 nil]
            [nil 1 1 nil]
            [nil nil nil nil]]))
    (is (= (->> (matrix/pad-submat {:w 3, :h 2}
                                   [[1 1]
                                    [1 1]]
                                   {:x 1, :y 0})
                (mapv vec))
           [[nil 1 1]
            [nil 1 1]]))))

(deftest submat-positions-test
  (testing "Submatrix positions"
    (is (= (->> (matrix/submat-positions {:w 3, :h 3} {:w 2, :h 2})
                (into #{}))
           #{{:x 0, :y 0}
             {:x 0, :y 1}
             {:x 1, :y 0}
             {:x 1, :y 1}}))))