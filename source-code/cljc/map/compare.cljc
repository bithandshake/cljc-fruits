
(ns map.compare
    (:require [clojure.data]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn difference
  ; @description
  ; Computes the difference between two maps, returning key-value pairs that are
  ; present in the first map but not in the second map.
  ;
  ; @param (map) a
  ; @param (map) b
  ;
  ; @usage
  ; (difference {:a "a" :b "b"} {:a "a"})
  ;
  ; @example
  ; (difference {:a "a" :b "b"} {:a "a"})
  ; =>
  ; {:b "b"}
  ;
  ; @return (map)
  ; Key-value pairs presented only in 'a' map.
  [a b]
  (-> a (clojure.data/diff b) first))
