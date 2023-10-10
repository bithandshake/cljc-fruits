
(ns map.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-vector
  ; @description
  ; Converts the given 'n' map into a vector.
  ;
  ; @param (map) n
  ; @param (function)(opt) convert-f
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
   (letfn [(f [result [k v]]
              (conj result v))]
          (reduce f [] n)))

  ([n convert-f]
   (letfn [(f [result [k v]]
              (conj result (convert-f k v)))]
          (reduce f [] n))))
