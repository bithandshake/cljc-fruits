
(ns fruits.math.circle
    (:require [fruits.math.config :as config]
              [fruits.math.power  :as power]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn diameter->area
  ; @description
  ; Returns the calculated area for the given diameter value.
  ;
  ; @param (number) diameter
  ;
  ; @usage
  ; (diameter->area 100000)
  ; =>
  ; 7853981634
  ;
  ; @return (number)
  [diameter]
  (if (-> diameter number?)
      (-> diameter (/ 2)
                   (power/power 2)
                   (* config/PI))))

(defn diameter->circum
  ; @description
  ; Returns the calculated circumference for the given diameter value.
  ;
  ; @param (number) diameter
  ;
  ; @usage
  ; (diameter->circum 100000)
  ; =>
  ; 314156
  ;
  ; @return (number)
  [diameter]
  (if (-> diameter number?)
      (-> diameter (* config/PI))))

(defn diameter->radius
  ; @description
  ; Returns the calculated radius for the given diameter value.
  ;
  ; @param (number) diameter
  ;
  ; @usage
  ; (diameter->radius 100000)
  ; =>
  ; 50000
  ;
  ; @return (number)
  [diameter]
  (if (-> diameter number?)
      (-> diameter (/ 2))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn radius->area
  ; @description
  ; Returns the calculated area for the given radius value.
  ;
  ; @param (number) radius
  ;
  ; @usage
  ; (radius->area 50000)
  ; =>
  ; 7853981634
  ;
  ; @return (number)
  [radius]
  (if (-> radius number?)
      (-> radius (power/power 2)
                 (* config/PI))))

(defn radius->circum
  ; @description
  ; Returns the calculated circumference for the given radius value.
  ;
  ; @param (number) radius
  ;
  ; @usage
  ; (radius->circum 50000)
  ; =>
  ; 314156
  ;
  ; @return (number)
  [radius]
  (if (-> radius number?)
      (-> radius (* 2 config/PI))))

(defn radius->diameter
  ; @description
  ; Returns the calculated diameter for the given radius value.
  ;
  ; @param (number) radius
  ;
  ; @usage
  ; (radius->diameter 50000)
  ; =>
  ; 100000
  ;
  ; @return (number)
  [radius]
  (if (-> radius number?)
      (-> radius (* 2))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn circum->area
  ; @description
  ; Returns the calculated area for the given circumference value.
  ;
  ; @param (number) circum
  ;
  ; @usage
  ; (circum->area 314156)
  ; =>
  ; 7853981634
  ;
  ; @return (number)
  [circum]
  (if (-> circum number?)
      (-> circum (/ 2 config/PI)
                 (power/power 2)
                 (*  config/PI))))

(defn circum->diameter
  ; @description
  ; Returns the calculated diameter for the given circumference value.
  ;
  ; @param (number) circum
  ;
  ; @usage
  ; (diameter->circum 314156)
  ; =>
  ; 50000
  ;
  ; @return (number)
  [circum]
  (if (-> circum number?)
      (-> circum (/ config/PI))))

(defn circum->radius
  ; @description
  ; Returns the calculated radius for the given circumference value.
  ;
  ; @param (number) circum
  ;
  ; @usage
  ; (diameter->radius 314156)
  ; =>
  ; 100000
  ;
  ; @return (number)
  [circum]
  (if (-> circum number?)
      (-> circum (/ 2 config/PI))))
