
(ns sequence.dex)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn next-dex
  ; @descripiton
  ; Returns with the following index after the given 'dex'.
  ; Keeps the return value in the domain ranged by the value of the
  ; given 'min' and 'max'.
  ;
  ; @param (integer) dex
  ; @param (integer) min
  ; @param (integer) max
  ;
  ; @example
  ; (next-dex 10 8 20)
  ; =>
  ; 11
  ;
  ; @example
  ; (next-dex 20 8 20)
  ; =>
  ; 8
  ;
  ; @return (integer)
  [dex min max]
  (cond (>= dex max) min
        (<  dex min) min
        :return (inc dex)))

(defn prev-dex
  ; @descripiton
  ; Returns with the previous index before the given 'dex'.
  ; Keeps the return value in the domain ranged by the value of the
  ; given 'min' and 'max'.
  ;
  ; @param (integer) dex
  ; @param (integer) min
  ; @param (integer) max
  ;
  ; @example
  ; (prev-dex 10 8 20)
  ; =>
  ; 9
  ;
  ; @example
  ; (prev-dex 8 8 20)
  ; =>
  ; 20
  ;
  ; @return (integer)
  ; A dex elotti index, ami nem lehet kisebb, mint min es nem lehet nagyobb,
  ; mint max
  [dex min max]
  (cond (<= dex min) max
        (>  dex max) max
        :return (dec dex)))

(defn prev-prev-dex
  ; @descripiton
  ; Returns with the index before previous of the given 'dex'.
  ; Keeps the return value in the domain ranged by the value of the
  ; given 'min' and 'max'.
  ;
  ; @param (integer) dex
  ; @param (integer) min
  ; @param (integer) max
  ;
  ; @example
  ; (prev-prev-dex 10 8 20)
  ; =>
  ; 8
  ;
  ; @example
  ; (prev-prev-dex 9 8 20)
  ; =>
  ; 20
  ;
  ; @return (integer)
  [dex min max]
  (cond
        ; If 'max' is NOT greater than 'min', then returns with 'min'.
        (not (> max min)) min

        ; If 'dex' is greater than 'max', then returns with 'max - 1'
        (> dex max) (dec max)

        ; If 'dex' is equal to or smaller than 'min', then returns with 'max - 1'
        (<= dex min) (dec max)

        ; If 'dex' is equal to 'min + 1', then returns with 'max'.
        (= dex (inc min)) max

        ; Otherwise returns with 'dex  - 2'
        :return (- dex 2)))
