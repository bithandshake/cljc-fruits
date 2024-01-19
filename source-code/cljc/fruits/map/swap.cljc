
(ns fruits.map.swap
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn swap
  ; @description
  ; Swaps the keys and values in the given 'n' map.
  ; Returns a new map with the values as keys and the keys as values.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (swap {:a "A" :b "B"})
  ; =>
  ; {"A" :a "B" :b}
  ;
  ; @return (map)
  [n]
  (let [n (mixed/to-map n)]
       (zipmap (vals n)
               (keys n))))
