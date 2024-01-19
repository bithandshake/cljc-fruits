
(ns fruits.vector.insert
    (:require [fruits.mixed.api   :as mixed]
              [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn insert-item
  ; @description
  ; Inserts the given 'x' value into the given 'n' vector to a specific position.
  ;
  ; @param (vector) n
  ; @param (integer) cursor
  ; @param (*) x
  ;
  ; @usage
  ; (insert-item [:a :b :c] 2 :d)
  ; =>
  ; [:a :b :d :c]
  ;
  ; @usage
  ; (insert-item [:a :b :c] 999 :d)
  ; =>
  ; [:a :b :c :d]
  ;
  ; @usage
  ; (insert-item nil 999 :d)
  ; =>
  ; [:d]
  ;
  ; @usage
  ; (insert-item {:a "b"} 1 :d)
  ; =>
  ; [:d]
  ;
  ; @return (vector)
  [n cursor x]
  (let [n      (mixed/to-vector n)
        cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
       (vec (concat (subvec n 0 cursor)
                    [x]
                    (subvec n cursor)))))
