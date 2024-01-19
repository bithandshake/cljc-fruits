
(ns fruits.math.polarity)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn absolute
  ; @param (number) n
  ;
  ; @usage
  ; (absolute -4.20)
  ; =>
  ; 4.20
  ;
  ; @return (number)
  [n]
  (if (number? n)
      (max n (- n))))

(defn negative
  ; @param (number) n
  ;
  ; @usage
  ; (negative 4.20)
  ; =>
  ; -4.20
  ;
  ; @return (number)
  [n]
  (if (number? n)
      (if (>= 0 n)
          (-> n)
          (-  0 n))))

(defn positive
  ; @param (number) n
  ;
  ; @usage
  ; (positive -4.20)
  ; =>
  ; 4.20
  ;
  ; @return (number)
  [n]
  (if (number? n)
      (if (<= 0 n)
          (-> n)
          (-  0 n))))

(defn absolute-difference
  ; @param (number) a
  ; @param (number) b
  ;
  ; @usage
  ; (absolute-difference 4.20 42)
  ;
  ; @return (number)
  [a b]
  (if (and (number? a)
           (number? b))
      (absolute (- a b))))

(defn opposite
  ; @param (number) n
  ;
  ; @usage
  ; (opposite 4.20)
  ; =>
  ; -4.20
  ;
  ; @return (number)
  [n]
  (if (number? n)
      (- 0 n)))
