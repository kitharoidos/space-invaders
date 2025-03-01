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

          canvas-padding {:x 2, :y 2}
          canvas-size {:w 9, :h 9}
          padded-invader-1 (matrix/pad-submat canvas-size invader-1 {:x 3, :y 3})
          padded-invader-2 (matrix/pad-submat canvas-size invader-2 {:x 4, :y 4})]
      (is (= (-> (canvas/canvas canvas-size canvas-padding)
                 (canvas/draw-padded-invader padded-invader-1)
                 (canvas/draw-padded-invader padded-invader-2)
                 (canvas/render))
             "         \n         \n  -----  \n  --o--  \n  --o--  \n  --oo-  \n  -----  \n         \n         ")))))