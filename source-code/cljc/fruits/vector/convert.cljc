
(ns fruits.vector.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn from-subvec
  ; @description
  ; Converts the given 'n' subvector into a fruits.vector.
  ;
  ; @param (subvec) n
  ;
  ; @usage
  ; (from-subvec (subvec [:a :b :c] 1))
  ;
  ; @example
  ; (from-subvec (subvec [:a :b :c] 1))
  ; [:b :c]
  ;
  ; @return (vector)
  [n]
  (into [] n))

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
   (letfn [(f0 [result dex x]
               (let [[k v] (convert-f dex x)]
                    (assoc result k v)))]
          (reduce-kv f0 {} n))))

(defn to-nil
  ; @param (vector) n
  ; @param (map)(opt) options
  ; {:if-empty? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (to-nil [])
  ;
  ; @example
  ; (to-nil [])
  ; =>
  ; nil
  ;
  ; @example
  ; (to-nil [:a :b :c])
  ; =>
  ; [:a :b :c]
  ;
  ; @return (nil or vector)
  ([n]
   (to-nil n {}))

  ([n {:keys [if-empty?] :or {if-empty? true}}]
   (cond (-> n empty?)      (-> nil)
         (-> if-empty? not) (-> nil)
         :return n)))
