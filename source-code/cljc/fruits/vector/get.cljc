
(ns fruits.vector.get
    (:require [fruits.seqable.api :as seqable]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-item
  ; @description
  ; Returns the first item of the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (first-item [:a :b :c])
  ; =>
  ; :a
  ;
  ; @return (*)
  [n]
  (let [n (mixed/to-vector n)]
       (first n)))

(defn second-item
  ; @description
  ; Returns the first item of the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (first-item [:a :b :c])
  ; =>
  ; :a
  ;
  ; @return (*)
  [n]
  (let [n (mixed/to-vector n)]
       (second n)))

(defn last-item
  ; @description
  ; Returns the last item of the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (last-item [:a :b :c])
  ; =>
  ; :c
  ;
  ; @return (*)
  [n]
  (let [n (mixed/to-vector n)]
       (last n)))

(defn nth-item
  ; @description
  ; Returns the nth item of the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (integer) th
  ;
  ; @usage
  ; (nth-item [:a :b :c] 2)
  ; =>
  ; :c
  ;
  ; @return (*)
  [n th]
  (let [n (mixed/to-vector n)]
       (if-let [th (seqable/normalize-dex n th {:adjust? false :mirror? true})]
               (nth n th))))
