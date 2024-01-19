
(ns fruits.vector.toggle
    (:require [fruits.vector.add     :as add]
              [fruits.vector.contain :as contain]
              [fruits.vector.remove  :as remove]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn toggle-item
  ; @description
  ; Toggles the presence of the given 'x' value in the 'n' vector.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (toggle-item [:a :b] :a)
  ;
  ; @example
  ; (toggle-item [:a :b] :c)
  ; =>
  ; [:a :b :c]
  ;
  ; @example
  ; (toggle-item [:a :b :c] :c)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n x]
  (if (contain/contains-item? n x)
      (remove/remove-item     n x)
      (add/conj-item          n x)))
