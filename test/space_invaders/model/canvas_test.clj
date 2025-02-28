(ns space-invaders.model.canvas-test
  (:require [clojure.test :refer :all]
            [space-invaders.model.canvas :as canvas]
            [space-invaders.model.matrix :as matrix]))

(deftest canvas-test
  (testing "Drawing on and rendering of canvas"
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
          (canvas/canvas (map matrix/size [invader-1 invader-2]) radar-size)

          padded-invader-1
          (matrix/pad-submat (canvas/size canvas) invader-1 {:x 3, :y 3})

          padded-invader-2
          (matrix/pad-submat (canvas/size canvas) invader-2 {:x 4, :y 4})]
      (is (= (-> canvas
                 (canvas/draw-padded-invader padded-invader-1)
                 (canvas/draw-padded-invader padded-invader-2)
                 (canvas/render))
             "         \n         \n  -----  \n  --o--  \n  --o--  \n  --oo-  \n  -----  \n         \n         ")))))