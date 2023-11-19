
(ns vector.insert
    (:require [seqable.api :as seqable]
              [vector.core :as core]))

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
  ; (insert-item [:a :b :c] 0 :x)
  ;
  ; @example
  ; (insert-item [:a :b :c] 2 :d)
  ; =>
  ; [:a :b :d :c]
  ;
  ; @example
  ; (insert-item [:a :b :c] 999 :d)
  ; =>
  ; [:a :b :d :c]
  ;
  ; @example
  ; (insert-item nil 999 :d)
  ; =>
  ; [:d]
  ;
  ; @example
  ; (insert-item {:a "b"} 1 :d)
  ; =>
  ; {:a "b"}
  ;
  ; @return (vector)
  [n cursor x]
  (let [n      (if (vector? n) n [])
        cursor (seqable/normalize-cursor n cursor)]
       (core/concat-items (subvec n 0 cursor)
                          [x]
                          (subvec n cursor))))
