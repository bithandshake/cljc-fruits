
(ns math.collection
    (:require [noop.api :refer [param return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn collection-minimum
  ; @param (collection) n
  ;
  ; @example
  ; (collection-minimum [100 14 3 55])
  ; =>
  ; 3
  ;
  ; @example
  ; (collection-minimum ["0" 1 "a" nil])
  ; =>
  ; 1
  ;
  ; @example
  ; (collection-minimum ["0" "a"])
  ; =>
  ; nil
  ;
  ; @return (nil or integer)
  [n]
  (apply min n))

(defn collection-maximum
  ; @param (collection) n
  ;
  ; @example
  ; (collection-maximum [100 14 3 55])
  ; =>
  ; 100
  ;
  ; @example
  ; (collection-maximum ["0" 1 "a" nil])
  ; =>
  ; 1
  ;
  ; @example
  ; (collection-maximum ["0" "a"])
  ; =>
  ; nil
  ;
  ; @return (nil or integer)
  [n]
  (apply max n))

(defn minimum
  ; @param (list of number) xyz
  ;
  ; @example
  ; (minimum -4.20 2 0)
  ; =>
  ; 2
  ;
  ; @return (number)
  [& xyz]
  (collection-minimum xyz))

(defn maximum
  ; @param (list of numbers) xyz
  ;
  ; @example
  ; (maximum -4.20 2 0)
  ; =>
  ; 2
  ;
  ; @return (number)
  [& xyz]
  (collection-maximum xyz))
