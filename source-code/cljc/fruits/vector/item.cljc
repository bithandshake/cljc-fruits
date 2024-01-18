
(ns fruits.vector.item
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-only?
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (item-only? [:b] :b)
  ; =>
  ; true
  ;
  ; @usage
  ; (item-only? [:a :b] :b)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-vector n)]
       (= n [x])))

(defn item-first?
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (item-first? [:a :b] :a)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-vector n)]
       (-> n first (= x))))

(defn item-second?
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (item-second? [:a :b] :b)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-vector n)]
       (-> n second (= x))))

(defn item-last?
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (item-last? [:a :b] :b)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-vector n)]
       (-> n last (= x))))

(defn item-nth?
  ; @param (vector) n
  ; @param (*) x
  ; @param (integer) th
  ;
  ; @usage
  ; (item-nth? [:a :b] :b 1)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x th]
  (let [n  (mixed/to-vector n)
        th (mixed/to-integer th)]
       (if (-> n count (>= th))
           (-> n (nth th) (= x)))))
