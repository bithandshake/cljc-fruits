
(ns vector.dex
    (:require [loop.api :refer [some-indexed]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-last-dex
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (item-last-dex [:a :b :a :b] :b)
  ;
  ; @example
  ; (item-last-dex [:a :b :a :b] :b)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n x]
  (if (vector? n)
      (letfn [(f [%1 %2 %3] (if (= %3 x) %2 %1))]
             (reduce-kv f nil n))))

(defn item-first-dex
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @example
  ; (item-first-dex [:a :b :a :b] :b)
  ; =>
  ; 1
  ;
  ; @return (integer)
  [n x]
  (if (vector? n)
      (letfn [(f [%1 %2] (if (= %2 x) %1))]
             (some-indexed f n))))
