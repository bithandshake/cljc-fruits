
(ns fruits.math.round)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn floor
  ; @param (number) n
  ;
  ; @usage
  ; (floor 4.20)
  ; =>
  ; 4
  ;
  ; @usage
  ; (floor 4.80)
  ; =>
  ; 4
  ;
  ; @return (integer)
  [n]
  (if (number? n)
      (Math/floor n)))

(defn ceil
  ; @param (number) n
  ;
  ; @usage
  ; (ceil 4.20)
  ; 5
  ; 4
  ;
  ; @usage
  ; (ceil 4.80)
  ; =>
  ; 5
  ;
  ; @return (integer)
  [n]
  (if (number? n)
      (Math/ceil n)))

(defn round
  ; @param (number) n
  ; @param (integer) precision
  ;
  ; @usage
  ; (round 4.20)
  ; =>
  ; 4
  ;
  ; @usage
  ; (round 4.80)
  ; =>
  ; 5
  ;
  ; @usage
  ; (round 420 100)
  ; =>
  ; 400
  ;
  ; @return (integer)
  ([n]
   (if (> 0.5 (rem n 1)) (quot n 1)
                    (inc (quot n 1))))

  ([n precision]
   (if (and (number? n)
            (integer? precision))
       (-> n (/ precision)
             (round)
             (* precision)))))
