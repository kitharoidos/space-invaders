(ns space-invaders.schema
  (:require [malli.util :as mu]))

(def ^:private radar-matrix
  [:and
   [:vector
    [:vector
     [:enum \o \-]]]
   [:fn #(and (seq %) (every? seq %) (apply = (map count %)))]])

(def args
  [:map
   ["--known-invaders"
    (-> [:and
         [:vector
          radar-matrix]
         [:fn seq]]
        (mu/update-properties assoc :matrices? true))]
   ["--radar-sample"
    (-> radar-matrix
        (mu/update-properties assoc :matrix? true))]
   ["--tolerance"
    nat-int?]])

(def documentation
  "Options:
  --known-invaders (required) Path to a text file with non-empty invader matrices separated by empty lines (at least one matrix is required)
  --radar-sample (required) Path to a text file with a non-empty radar matrix
  --tolerance (required) Maximum number of allowed mismatches when matching an invader matrix to a radar matrix at a certain position")