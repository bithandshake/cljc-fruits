
(ns vector.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-map
  ; @description
  ; Converts the given 'n' vector into a map.
  ;
  ; @param (vector) n
  ; @param (function)(opt) convert-f
  ; Default: (fn [dex x] [dex x])
  ;
  ; @usage
  ; (to-map [:a :b :c])
  ;
  ; @example
  ; (to-map [:a :b :c])
  ; =>
  ; {0 :a 1 :b 2 :c}
  ;
  ; @example
  ; (to-map [:a :b :c] (fn [dex x] [(str dex) (name x)]))
  ; =>
  ; {"0" "a" "1" "b" "2" "c"}
  ;
  ; @return (map)
  ([n]
   (to-map n (fn [dex x] [dex x])))

  ([n convert-f]
   (letfn [(f [result dex x]
              (let [[k v] (convert-f dex x)]
                   (assoc result k v)))]
          (reduce-kv f {} n))))
