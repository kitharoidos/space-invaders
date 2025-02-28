(ns space-invaders.model.canvas-test
  (:require [clojure.test :refer :all]
            [space-invaders.model.canvas :as canvas]
            [space-invaders.model.matrix :as matrix]))

(deftest canvas-test
  (testing "Canvas"
    (let [invader-1
          [[\- \o \-]
           [\- \o \-]]

          invader-2
          [[\- \-]
           [\o \o]
           [\- \-]]

          radar-size
          {:w 5, :h 5}

          canvas
          (canvas/new-canvas (map matrix/size [invader-1 invader-2]) radar-size)

          pattern-1
          (matrix/pad (canvas/size canvas) invader-1 {:x 3, :y 3})

          pattern-2
          (matrix/pad (canvas/size canvas) invader-2 {:x 4, :y 4})]
      (is (= (-> canvas
                 (canvas/add-invader pattern-1)
                 (canvas/add-invader pattern-2)
                 (canvas/render))
             "         \n         \n  -----  \n  --o--  \n  --o--  \n  --oo-  \n  -----  \n         \n         ")))))