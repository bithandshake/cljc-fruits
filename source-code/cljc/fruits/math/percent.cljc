
(ns fruits.math.percent)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn percent->angle
  ; @param (number) n
  ;
  ; @usage
  ; (percent->angle 100)
  ; =>
  ; 360
  ;
  ; @usage
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
  ; @usage
  ; (percent 100 40)
  ; =>
  ; 40
  ;
  ; @usage
  ; (percent 50 20)
  ; =>
  ; 40
  ;
  ; @return (number)
  [total value]
  (/ value (/ total 100)))

(defn percent-result
  ; @param (number) total
  ; @param (number) percentage
  ;
  ; @usage
  ; (percent 100 20)
  ; =>
  ; 20
  ;
  ; @usage
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
  ; @usage
  ; (percent-rest 100 70)
  ; =>
  ; 30
  ;
  ; @usage
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
  ; @usage
  ; (percent-add 100 40)
  ; =>
  ; 140
  ;
  ; @usage
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
  ; @usage
  ; (percent-diff 100 110)
  ; =>
  ; 10
  ;
  ; @usage
  ; (percent-diff 100 90)
  ; =>
  ; -10
  ;
  ; @usage
  ; (percent-diff 50 55)
  ; =>
  ; 10
  ;
  ; @usage
  ; (percent-diff 50 45)
  ; =>
  ; -10
  ;
  ; @return (number)
  [a b]
  (- (percent a b) 100))
