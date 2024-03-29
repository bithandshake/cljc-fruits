
(ns fruits.layout.table
    (:require [fruits.math.api :as math]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn column-count
  ; @description
  ; Returns how many columns needed to display the given amount of items
  ; considering the column width, the maximum column count and the maximum
  ; displaying width.
  ;
  ; @param (integer) item-count
  ; @param (px) column-width
  ; @param (integer) max-column-count
  ; @param (px) max-width
  ;
  ; @usage
  ; (column-count 13 200 8 1980)
  ; =>
  ; 8
  ;
  ; @usage
  ; (column-count 13 200 8 1240)
  ; =>
  ; 6
  ;
  ; @usage
  ; (column-count 2 200 8 1980)
  ; =>
  ; 2
  ;
  ; @usage
  ; (column-count 0 200 8 1980)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [item-count column-width max-column-count max-width]
  (let [max-columns-fit  (math/floor (/ max-width column-width))
        max-column-count (math/minimum max-column-count max-columns-fit)]
       (math/between item-count 0 max-column-count)))

(defn columns-width
  ; @description
  ; Uses the 'column-count' function to determine how many columns needed to display
  ; a certain amount of items and returns the total width of the columns.
  ;
  ; @param (integer) item-count
  ; @param (px) column-width
  ; @param (integer) max-column-count
  ; @param (px) max-width
  ;
  ; @usage
  ; (columns-width 13 200 8 1980)
  ; =>
  ; 1600
  ;
  ; @usage
  ; (columns-width 13 200 8 1240)
  ; =>
  ; 1200
  ;
  ; @usage
  ; (columns-width 2 200 8 1980)
  ; =>
  ; 400
  ;
  ; @usage
  ; (columns-width 0 200 8 1980)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [item-count column-width max-column-count max-width]
  (let [column-count (column-count item-count column-width max-column-count max-width)]
       (* column-width column-count)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn row-count
  ; @description
  ; Uses the 'column-count' function to determine how many rows needed to display
  ; a certain amount of items.
  ;
  ; @param (integer) item-count
  ; @param (px) column-width
  ; @param (integer) max-column-count
  ; @param (px) max-width
  ;
  ; @usage
  ; (row-count 13 200 8 1980)
  ; =>
  ; 8
  ;
  ; @usage
  ; (row-count 13 200 8 1240)
  ; =>
  ; 6
  ;
  ; @usage
  ; (row-count 2 200 8 1980)
  ; =>
  ; 2
  ;
  ; @usage
  ; (row-count 0 200 8 1980)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [item-count column-width max-column-count max-width]
  (let [column-count (column-count item-count column-width max-column-count max-width)]
       (math/domain-inchoate column-count max-column-count)))
