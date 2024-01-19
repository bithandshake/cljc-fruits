
(ns fruits.math.power)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn power
  ; @param (number) x
  ; @param (number) n
  ;
  ; @usage
  ; (power 2 3)
  ; =>
  ; 8
  ;
  ; @return (number)
  [x n]
  (if (and (number? x)
           (number? n))
      (if (zero? n) 1
          (* x (power x (dec n))))))
