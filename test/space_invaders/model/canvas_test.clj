(ns space-invaders.model.canvas-test
  (:require [clojure.test :refer :all]
            [space-invaders.model.canvas :as canvas]
            [space-invaders.model.matrix :as matrix]))

(deftest canvas-test
  (testing "Drawing on and rendering of canvas"
    (let [canvas-size {:w 9, :h 9}
          canvas-padding {:x 2, :y 2}

          invader-1
          (matrix/pad-submat
            canvas-size
            [[\- \o \-]
             [\- \o \-]]
            {:x 3, :y 3})

          invader-2
          (matrix/pad-submat
            canvas-size
            [[\- \-]
             [\o \o]
             [\- \-]]
            {:x 4, :y 4})]
      (is (= (-> (canvas/canvas canvas-size canvas-padding)
                 (canvas/draw-invader invader-1)
                 (canvas/draw-invader invader-2)
                 (canvas/render))
             "         \n         \n  -----  \n  --o--  \n  --o--  \n  --oo-  \n  -----  \n         \n         ")))))