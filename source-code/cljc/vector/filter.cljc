
(ns vector.filter
    (:require [seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn filter-items
  ; @param (vector) n
  ; @param (function) filter-f
  ;
  ; @usage
  ; (filter-items [:a :b "c"] keyword?)
  ;
  ; @example
  ; (filter-items [:a :b "c"] keyword?)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n filter-f]
  (vec (filter filter-f n)))
