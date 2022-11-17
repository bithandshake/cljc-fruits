
(ns map.filter)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn filter-values
  ; @param (map) n
  ; @param (function) filter-f
  ;
  ; @usage
  ; (filter-values {:a "A"} string?)
  ;
  ; @example
  ; (filter-values {:a 0 :b 1 :c 2} even?)
  ; =>
  ; {:a 0 :c 2}
  ;
  ; @return (map)
  [n filter-f]
  (letfn [(f [%1 %2 %3] (if (filter-f %3) (assoc %1 %2 %3) %1))]
         (reduce-kv f {} n)))

(defn filter-values-by
  ; @param (map) n
  ; @param (function) filter-f
  ; @param (function) value-f
  ;
  ; @usage
  ; (filter-values-by {:a {:value "A"}} #(= % "A") :value)
  ;
  ; @example
  ; (filter-values-by {:a {:value "A"} :b {:value "B"}}
  ;                   #(= % "A") :value)
  ; =>
  ; {:a {:value "A"}}
  ;
  ; @return (map)
  [n filter-f value-f]
  (letfn [(f [%1 %2 %3] (if (filter-f (value-f %3)) (assoc %1 %2 %3) %1))]
         (reduce-kv f {} n)))
