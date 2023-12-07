
(ns fruits.map.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-vector
  ; @description
  ; Converts the given 'n' map into a vector.
  ;
  ; @param (map) n
  ; @param (function)(opt) convert-f
  ; Default: (fn [k v] v)
  ;
  ; @usage
  ; (to-vector {:a "A" b "B"})
  ;
  ; @usage
  ; (defn my-convert-f [k v] [k v])
  ; (to-vector {:a "A" b "B"} my-convert-f)
  ;
  ; @example
  ; (to-vector {:a "A" b "B" :c "C"})
  ; =>
  ; ["A" "B" "C"]
  ;
  ; @example
  ; (defn my-convert-f [k v] [k v])
  ; (to-vector {:a "A" b "B"} my-convert-f)
  ; =>
  ; [[:a "A"] [:b "B"]]
  ;
  ; @return (vector)
  ([n]
   (to-vector n (fn [k v] v)))

  ([n convert-f]
   (letfn [(f0 [result [k v]]
               (conj result (convert-f k v)))]
          (reduce f0 [] n))))
