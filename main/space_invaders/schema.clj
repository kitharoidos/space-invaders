(ns space-invaders.schema
  (:require [malli.util :as mu]))

(def ^:private matrix
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
          matrix]
         [:fn seq]]
        (mu/update-properties assoc :matrices? true))]
   ["--radar-sample"
    (-> matrix
        (mu/update-properties assoc :matrix? true))]
   ["--tolerance"
    [:and
     nat-int?
     [:fn #(<= % 100)]]]])

(def documentation
  "Options:
  --known-invaders (required) Path to a text file with invader patterns separated by empty lines
  --radar-sample (required) Path to a text file with a radar sample
  --tolerance (required) Maximum number of allowed mismatches when matching an invader pattern to the radar sample in percents of the pattern's pixel count")