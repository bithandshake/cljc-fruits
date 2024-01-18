
(ns fruits.vector.step
    (:require [fruits.seqable.api :as seqable]
              [fruits.vector.dex  :as dex]
              [fruits.vector.get  :as get]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn prev-item
  ; @description
  ; Returns the item that precedes the given 'x' item in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (prev-item [:a :b :c :d] :b)
  ; =>
  ; :a
  ;
  ; @usage
  ; (prev-item [:a :b :c :d] nil)
  ; =>
  ; nil
  ;
  ; @usage
  ; (prev-item [] :a)
  ; =>
  ; nil
  ;
  ; @return (*)
  [n x]
  (let [n (mixed/to-vector n)]
       (if-let [item-first-dex (dex/first-dex-of n x)]
               (let [prev-item-dex (seqable/prev-dex n item-first-dex)]
                    (get/nth-item n prev-item-dex)))))

(defn next-item
  ; @description
  ; Returns the item that follows the given 'x' item in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (next-item [:a :b :c :d] :a)
  ; =>
  ; :b
  ;
  ; @usage
  ; (next-item [:a :b :c :d] nil)
  ; =>
  ; nil
  ;
  ; @usage
  ; (next-item [] :a)
  ; =>
  ; nil
  ;
  ; @return (*)
  [n x]
  (let [n (mixed/to-vector n)]
       (if-let [item-first-dex (dex/first-dex-of n x)]
               (let [next-item-dex (seqable/next-dex n item-first-dex)]
                    (get/nth-item n next-item-dex)))))
