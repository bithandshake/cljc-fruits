
(ns fruits.math.percent)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn percent->angle
  ; @param (number) n
  ;
  ; @example
  ; (percent->angle 50)
  ; =>
  ; 180
  ;
  ; @return (number)
  [n]
  (* 360 (/ n 100)))

(defn percent
  ; @param (number) total
  ; @param (number) value
  ;
  ; @example
  ; (percent 50 20)
  ; =>
  ; 40
  ;
  ; @return (number)
  [total value]
  (/ value (/ total 100)))

; TEMP
; What's the real name of this?
(defn percent-result
  ; @param (number) total
  ; @param (number) percentage
  ;
  ; @example
  ; (percent 50 40)
  ; =>
  ; 20
  ;
  ; @return (number)
  [total percentage]
  (/ (* total percentage) 100))

(defn percent-rest
  ; @param (number) total
  ; @param (number) percentage
  ;
  ; @example
  ; (percent-rest 50 40)
  ; =>
  ; 30
  ;
  ; @return (number)
  [total percentage]
  (- total (percent-result total percentage)))

(defn percent-add
  ; @param (number) total
  ; @param (number) percentage
  ;
  ; @example
  ; (percent-add 50 40)
  ; =>
  ; 70
  ;
  ; @return (number)
  [total percentage]
  (+ total (percent-result total percentage)))

(defn percent-diff
  ; @param (number) a
  ; @param (number) b
  ;
  ; @example
  ; (percent-diff 100 110)
  ; =>
  ; 10
  ;
  ; @example
  ; (percent-diff 100 90)
  ; =>
  ; -10
  ;
  ; @example
  ; (percent-diff 50 55)
  ; =>
  ; 10
  ;
  ; @example
  ; (percent-diff 50 45)
  ; =>
  ; -10
  ;
  ; @return (number)
  [a b]
  (- (percent a b) 100))
