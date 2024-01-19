
(ns fruits.map.compare
    (:require [clojure.data]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn difference
  ; @description
  ; Computes the difference between the given 'a' and 'b' maps, returning key-value
  ; pairs that are present in the 'a' map but not in the 'b' map.
  ;
  ; @param (map) a
  ; @param (map) b
  ;
  ; @usage
  ; (difference {:a "a" :b "b"} {:a "a"})
  ; =>
  ; {:b "b"}
  ;
  ; @return (map)
  ; Key-value pairs present only in 'a' map.
  [a b]
  (let [a (mixed/to-map a)
        b (mixed/to-map b)]
       (-> a (clojure.data/diff b) first)))
