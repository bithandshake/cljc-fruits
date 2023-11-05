
(ns math.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn negative?
  ; @param (number) n
  ;
  ; @example
  ; (negative? -4.20)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (> 0 n))

(defn positive?
  ; @param (number) n
  ;
  ; @example
  ; (positive? 4.20)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (< 0 n))

(defn nonnegative?
  ; @param (number) n
  ;
  ; @example
  ; (nonnegative? 4.20)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (or (zero?     n)
      (positive? n)))
