
(ns fruits.map.convert
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-vector
  ; @description
  ; Converts the given 'n' map to a vector.
  ;
  ; @param (map) n
  ; @param (function)(opt) convert-f
  ; Default: (fn [k v] v)
  ;
  ; @usage
  ; (to-vector {:a "A" b "B" :c "C"})
  ; =>
  ; ["A" "B" "C"]
  ;
  ; @usage
  ; (defn my-convert-f [k v] [k v])
  ; (to-vector {:a "A" b "B"} my-convert-f)
  ; =>
  ; [[:a "A"] [:b "B"]]
  ;
  ; @return (vector)
  ([n]
   (to-vector n (fn [k v] v)))

  ([n convert-f]
   (let [n         (mixed/to-map n)
         convert-f (mixed/to-ifn convert-f)]
        (letfn [(f0 [result [k v]]
                    (conj result (convert-f k v)))]
               (reduce f0 [] n)))))

(defn to-nil
  ; @description
  ; Converts the given 'n' map to a NIL value.
  ;
  ; @param (vector) n
  ; @param (map)(opt) options
  ; {:if-empty? (boolean)(opt)
  ;   Converts only if the given 'n' map is empty.
  ;   Default: true}
  ;
  ; @usage
  ; (to-nil {})
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-nil {:a "A" :b "B" :c "C"})
  ; =>
  ; {:a "A" :b "B" :c "C"}
  ;
  ; @return (nil or map)
  ([n]
   (to-nil n {}))

  ([n {:keys [if-empty?] :or {if-empty? true}}]
   ; Alternative: 'not-empty'
   ; https://clojuredocs.org/clojure.core/not-empty
   (let [n (mixed/to-map n)]
        (cond (-> n empty?)      (-> nil)
              (-> if-empty? not) (-> nil)
              :return n))))
