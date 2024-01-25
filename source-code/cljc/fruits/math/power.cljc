
(ns fruits.math.power)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn power
  ; @description
  ; Performs an exponentiation on the given 'n' number.
  ;
  ; @param (number) n
  ; @param (integer) exp
  ;
  ; @usage
  ; (power 2 3)
  ; =>
  ; 8
  ;
  ; @return (number)
  [n exp]
  (if (and (-> n number?)
           (-> exp integer?))
      (loop [result 1 exp exp]
            (if (-> exp zero?)
                (-> result)
                (-> result (* n)
                           (recur (dec exp)))))))
