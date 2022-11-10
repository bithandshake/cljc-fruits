
(ns geometry.columns
    (:require [math.api :as math]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn column-count
  ; Kiszámítja hány oszlopban fér el a megadott számú elem és ...
  ; ... szükség szerint csökkenti az oszlopok számát aszerint, hogy az ne haladja
  ;     meg a max-column-count paraméterként átadott értéket.
  ; ... szükség szerint csökkenti az oszlopok számát aszerint, hogy az oszlopok
  ;     összeadott szélessége ne haladja meg a max-width paraméterként átadott értéket.
  ;
  ; @param (integer) item-count
  ; @param (px) column-width
  ; @param (integer) max-column-count
  ; @param (px) max-width
  ;
  ; @example
  ;  (column-count 13 200 8 1980)
  ;  =>
  ;  8
  ;
  ; @example
  ;  (column-count 13 200 8 1240)
  ;  =>
  ;  6
  ;
  ; @example
  ;  (column-count 2 200 8 1980)
  ;  =>
  ;  2
  ;
  ; @example
  ;  (column-count 0 200 8 1980)
  ;  =>
  ;  0
  ;
  ; @return (integer)
  [item-count column-width max-column-count max-width]
  (let [max-columns-fit  (math/floor (/ max-width column-width))
        max-column-count (math/minimum max-column-count max-columns-fit)]
       (math/between! item-count 0 max-column-count)))

(defn columns-width
  ; Kiszámítja, hogy a (column-count ...) függvény által meghatározott
  ; számú oszlopnak mennyi az összeadott szélessége.
  ;
  ; @param (integer) item-count
  ; @param (px) column-width
  ; @param (integer) max-column-count
  ; @param (px) max-width
  ;
  ; @example
  ;  (columns-width 13 200 8 1980)
  ;  =>
  ;  1600
  ;
  ; @example
  ;  (columns-width 13 200 8 1240)
  ;  =>
  ;  1200
  ;
  ; @example
  ;  (columns-width 2 200 8 1980)
  ;  =>
  ;  400
  ;
  ; @example
  ;  (columns-width 0 200 8 1980)
  ;  =>
  ;  0
  ;
  ; @return (integer)
  [item-count column-width max-column-count max-width]
  (let [column-count (column-count item-count column-width max-column-count max-width)]
       (* column-width column-count)))
