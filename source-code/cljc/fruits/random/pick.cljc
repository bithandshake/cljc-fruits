
(ns fruits.random.pick
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn pick-vector-item
  ; @description
  ; Returns a randomly picked item from the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (pick-vector-item [:a :b :c])
  ; =>
  ; :a
  ;
  ; @usage
  ; (pick-vector-item [:a :b :c])
  ; =>
  ; :c
  ;
  ; @return (*)
  [n]
  (let [n (mixed/to-vector n)]
       ; Alternative: rand-nth
       (nth n (-> n count rand-int))))
