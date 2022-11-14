
(ns sequence.dex)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn next-dex
  ; @param (integer) dex
  ; @param (integer) min
  ; @param (integer) max
  ;
  ; @example
  ;  (next-dex 10 8 20)
  ;  =>
  ;  11
  ;
  ; @example
  ;  (next-dex 20 8 20)
  ;  =>
  ;  8
  ;
  ; @return (integer)
  ;  A dex utan kovetkezo index, ami nem lehet kisebb, mint min es nem lehet
  ;  nagyobb, mint max
  [dex min max]
  (cond (>= dex max) min
        (<  dex min) min
        :return (inc dex)))

(defn prev-dex
  ; @param (integer) dex
  ; @param (integer) min
  ; @param (integer) max
  ;
  ; @example
  ;  (prev-dex 10 8 20)
  ;  =>
  ;  9
  ;
  ; @example
  ;  (prev-dex 8 8 20)
  ;  =>
  ;  20
  ;
  ; @return (integer)
  ;  A dex elotti index, ami nem lehet kisebb, mint min es nem lehet nagyobb,
  ;  mint max
  [dex min max]
  (cond (<= dex min) max
        (>  dex max) max
        :return (dec dex)))

(defn prev-prev-dex
  ; @param (integer) dex
  ; @param (integer) min
  ; @param (integer) max
  ;
  ; @example
  ;  (prev-prev-dex 10 8 20)
  ;  =>
  ;  8
  ;
  ; @example
  ;  (prev-prev-dex 9 8 20)
  ;  =>
  ;  20
  ;
  ; @return (integer)
  ;  A dex elotti-elotti index, ami nem lehet kisebb, mint min es nem lehet
  ;  nagyobb, mint max
  [dex min max]
  (cond (not (> max min)) min  ; Ha max nem nagyobb, mint min, akkor min
        (>  dex max) (dec max) ; Ha dex nagyobb, mint max, akkor max-1
        (<= dex min) (dec max) ; Ha dex kisebb-egyenlo, mint min, akkor max-1
        (= dex (inc min)) max  ; Ha dex eggyel tobb, mint min, akkor max
        :return (- dex 2)))    ; Kulonben dex-2
