
(ns fruits.vector.contain
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn contains-item?
  ; @description
  ; Returns TRUE if the given 'x' value is present in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (contains-item? [:a :b] :a)
  ; =>
  ; true
  ;
  ; @usage
  ; (contains-item? [:a :b] :c)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [%] (= % x))]
              (-> (some f0 n) boolean))))

(defn not-contains-item?
  ; @description
  ; Returns TRUE if the given 'x' value is NOT present in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (not-contains-item? [:a :b] :c)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-contains-item? [:a :b] :a)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [%] (not= % x))]
              (every? f0 n))))
