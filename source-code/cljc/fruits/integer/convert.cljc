
(ns fruits.integer.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-keyword
  ; @param (integer) n
  ;
  ; @usage
  ; (to-keyword 2)
  ;
  ; @example
  ; (to-keyword 2)
  ; =>
  ; :2
  ;
  ; @return (keyword)
  [n]
  (-> n str keyword))
