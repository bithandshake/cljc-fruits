
(ns integer.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-keyword
  ; @param (integer) n
  ;
  ; @example
  ;  (to-keyword 2)
  ;  =>
  ;  :2
  ;
  ; @return (keyword)
  [n]
  (-> n str keyword))
