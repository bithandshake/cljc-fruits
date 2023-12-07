
(ns fruits.vector.step
    (:require [fruits.seqable.api :as seqable]
              [fruits.vector.dex  :as dex]
              [fruits.vector.nth  :as nth]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn prev-item
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @example
  ; (prev-item [:a :b :c] :c)
  ;
  ; @example
  ; (prev-item [:a :b :c :d] :b)
  ; =>
  ; :a
  ;
  ; @example
  ; (prev-item [:a :b :c :d] nil)
  ; =>
  ; :d
  ;
  ; @example
  ; (prev-item [] :a)
  ; =>
  ; ?
  ;
  ; @return (*)
  [n x]
  (let [item-first-dex (dex/first-dex-of n x)
        prev-item-dex  (seqable/prev-dex n item-first-dex)]
       (nth/nth-item n prev-item-dex)))

(defn next-item
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (next-item [:a :b :c] :a)
  ;
  ; @example
  ; (next-item [:a :b :c :d] :a)
  ; =>
  ; :b
  ;
  ; @example
  ; (next-item [:a :b :c :d] nil)
  ; =>
  ; :a
  ;
  ; @example
  ; (next-item [] :a)
  ; =>
  ; ?
  ;
  ; @return (*)
  [n x]
  (let [item-first-dex (dex/first-dex-of n x)
        next-item-dex  (seqable/next-dex n item-first-dex)]
       (nth/nth-item n next-item-dex)))
