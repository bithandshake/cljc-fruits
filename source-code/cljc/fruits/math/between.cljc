
(ns fruits.math.between)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn between?
  ; @description
  ; Returns TRUE if the given 'n' number falls within the given 'x' and 'y' numbers.
  ;
  ; @param (number) n
  ; @param (number) x
  ; @param (number) y
  ;
  ; @usage
  ; (between? 2 0 5)
  ; =>
  ; true
  ;
  ; @usage
  ; (between? 7 0 5)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x y]
  (if (and (number? n)
           (number? x)
           (number? y))
      (and (<= n (max x y))
           (>= n (min x y)))))

(defn between
  ; @description
  ; Ensures that the given 'n' number falls within the given 'x' and 'y' numbers.
  ;
  ; @param (number) n
  ; @param (number) x
  ; @param (number) y
  ;
  ; @usage
  ; (between 2 0 5)
  ; =>
  ; 2
  ;
  ; @usage
  ; (between 7 0 5)
  ; =>
  ; 5
  ;
  ; @return (number)
  [n x y]
  (if (and (number? n)
           (number? x)
           (number? y))
      (cond (< n (min x y)) (min x y)
            (> n (max x y)) (max x y)
            :return n)))
