
(ns map.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-vector
  ; @param (map) n
  ;
  ; @usage
  ; (to-vector {:a "A" b "B"})
  ;
  ; @example
  ; (to-vector {:a "A" b "B" :c "C"})
  ; =>
  ; ["A" "B" "C"]
  ;
  ; @return (vector)
  [n]
  (letfn [(f [%1 _ %3] (conj %1 %3))]
         (reduce-kv f [] n)))
