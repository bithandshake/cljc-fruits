
(ns vector.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-map
  ; @param (vector) n
  ;
  ; @usage
  ; (to-map [:a :b :c])
  ;
  ; @example
  ; (to-map [:a :b :c])
  ; =>
  ; {:0 :a :1 :b :2 :c}
  ;
  ; @return (map)
  [n]
  (letfn [(f [%1 %2 %3] (assoc %1 (keyword (str %2)) %3))]
         (reduce-kv f {} n)))
