
(ns fruits.math.circle
    (:require [fruits.math.config :as config]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn circum
  ; @param (number) radius
  ;
  ; @usage
  ; (circum 50000)
  ; =>
  ; 314156
  ;
  ; @return (number)
  [radius]
  (if (number? radius)
      (* radius 2 config/PI)))
