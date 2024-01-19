
(ns fruits.math.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn negative?
  ; @description
  ; Returns TRUE if the given 'n' value is a negative number.
  ;
  ; @param (number) n
  ;
  ; @usage
  ; (negative? -4.20)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n number?)
       (-> n zero? not)
       (-> n pos? not)))

(defn positive?
  ; @description
  ; Returns TRUE if the given 'n' value is a positive number.
  ;
  ; @param (number) n
  ;
  ; @usage
  ; (positive? 4.20)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n number?)
       (-> n zero? not)
       (-> n neg? not)))

(defn not-negative?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonnegative number.
  ;
  ; @param (number) n
  ;
  ; @usage
  ; (not-negative? 4.20)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n number?)
       (-> n neg? not)))

(defn not-positive?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonpositive number.
  ;
  ; @param (number) n
  ;
  ; @usage
  ; (not-positive? -4.20)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n number?)
       (-> n pos? not)))
