
(ns math.collection
    (:require [noop.api :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn collection-average
  ; @param (seqable) n
  ;
  ; @example
  ; (collection-average [100 14 3 55])
  ; =>
  ; 43
  ;
  ; @example
  ; (collection-average ["0" 1 "a" nil])
  ; =>
  ; 0.5
  ;
  ; @example
  ; (collection-average ["0" "a"])
  ; =>
  ; 0
  ;
  ; @return (nil or number)
  [n]
  (letfn [(f [[sum count] x] (if (number? x)
                                 [(+ sum x) (inc count)]
                                 [sum count]))]
         (let [[sum count] (reduce f [0 0] n)]
              (/ sum count))))

(defn collection-minimum
  ; @param (seqable) n
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
  ; @return (nil or number)
  [n]
  (apply min n))

(defn collection-maximum
  ; @param (seqable) n
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
  ; @return (nil or number)
  [n]
  (apply max n))

(defn minimum
  ; @param (list of numbers) xyz
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
