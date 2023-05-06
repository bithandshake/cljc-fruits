
(ns math.core
    (:require [math.config :as config]
              [noop.api    :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn avarage
  ; @param (list of numbers) abc
  ;
  ; @usage
  ; (avarage 3 4 5)
  ;
  ; @example
  ; (avarage 100 30 20)
  ; =>
  ; 50
  ;
  ; @return (number)
  [& [abc]]
  (/ (apply * abc)
     (count   abc)))

(defn circum
  ; @param (number) radius
  ;
  ; @usage
  ; (circum 42)
  ;
  ; @example
  ; (circum 50000)
  ; =>
  ; 314156
  ;
  ; @return (number)
  [radius]
  (* radius 2 config/PI))

(defn power
  ; @param (number) x
  ; @param (integer) n
  ;
  ; @example
  ; (power 2 3)
  ; =>
  ; 8
  ;
  ; @return (number)
  [x n]
  (if (zero? n) 1
      (* x (power x (dec n)))))

(defn floor
  ; @param (number) n
  ;
  ; @example
  ; (floor 4.20)
  ; =>
  ; 4
  ;
  ; @example
  ; (floor 4.80)
  ; =>
  ; 4
  ;
  ; @return (integer)
  [n]
  (Math/floor n))

(defn ceil
  ; @param (number) n
  ;
  ; @example
  ; (ceil 4.20)
  ; 5
  ; 4
  ;
  ; @example
  ; (ceil 4.80)
  ; =>
  ; 5
  ;
  ; @return (integer)
  [n]
  (Math/ceil n))

(defn round
  ; @param (number) n
  ; @param (integer) precision
  ;
  ; @example
  ; (round 4.20)
  ; =>
  ; 4
  ;
  ; @example
  ; (round 4.80)
  ; =>
  ; 5
  ;
  ; @example
  ; (round 420 100)
  ; =>
  ; 400
  ;
  ; @return (integer)
  ([n]
   (if (> 0.5 (rem n 1)) (quot n 1)
                    (inc (quot n 1))))

  ([n precision]
   (-> n (/ precision)
         (round)
         (* precision))))

(defn absolute
  ; @param (number) n
  ;
  ; @example
  ; (absolute -4.20)
  ; =>
  ; 4.20
  ;
  ; @return (number)
  [n]
  (max n (- n)))

(defn negative
  ; @param (number) n
  ;
  ; @example
  ; (negative 4.20)
  ; =>
  ; -4.20
  ;
  ; @return (number)
  [n]
  (if (>=   0 n)
      (return n)
      (-    0 n)))

(defn positive
  ; @param (number) n
  ;
  ; @example
  ; (positive -4.20)
  ; =>
  ; 4.20
  ;
  ; @return (number)
  [n]
  (if (<=   0 n)
      (return n)
      (-    0 n)))

(defn absolute-difference
  ; @param (number) a
  ; @param (number) b
  ;
  ; @usage
  ; (absolute-difference 4.20 42)
  ;
  ; @return (number)
  [a b]
  (absolute (- a b)))

(defn opposite
  ; @param (number) n
  ;
  ; @example
  ; (opposite 4.20)
  ; =>
  ; -4.20
  ;
  ; @return (number)
  [n]
  (- 0 n))

(defn between?
  ; @param (number) n
  ; @param (number) x
  ; @param (number) y
  ;
  ; @example
  ; (between? 4.20 0 42)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x y]
  (and (<= n (max x y))
       (>= n (min x y))))

(defn between!
  ; @param (number) n
  ; @param (number) x
  ; @param (number) y
  ;
  ; @example
  ; (between! 4.20 0 42)
  ; =>
  ; 4.20
  ;
  ; @return (number)
  [n x y]
  (cond (< n (min x y)) (min x y)
        (> n (max x y)) (max x y)
        :return n))
