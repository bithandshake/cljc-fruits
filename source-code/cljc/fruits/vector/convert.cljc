
(ns fruits.vector.convert
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn from-subvec
  ; @description
  ; Converts the given 'n' subvector into a vector.
  ;
  ; @param (subvec) n
  ;
  ; @usage
  ; (from-subvec (subvec [:a :b :c] 1))
  ; =>
  ; [:b :c]
  ;
  ; @return (vector)
  [n]
  (if (-> n coll?)
      (-> [] (into n))
      (-> [])))

(defn to-map
  ; @description
  ; Converts the given 'n' vector into a map.
  ;
  ; @param (vector) n
  ; @param (function)(opt) convert-f
  ; Takes an index of a vector item and the corresponding item as parameters.
  ; Must return a map key and value pair within a vector.
  ; Default: (fn [dex x] [dex x])
  ;
  ; @usage
  ; (to-map [:a :b :c])
  ; =>
  ; {0 :a 1 :b 2 :c}
  ;
  ; @usage
  ; (to-map [:a :b :c] (fn [dex x] [(str dex) (name x)]))
  ; =>
  ; {"0" "a" "1" "b" "2" "c"}
  ;
  ; @return (map)
  ([n]
   (to-map n (fn [dex x] [dex x])))

  ([n convert-f]
   (let [n         (mixed/to-vector n)
         convert-f (mixed/to-ifn convert-f)]
        (letfn [(f0 [result dex x]
                    (let [kv (convert-f dex x)]
                         (if (mixed/kv? kv)
                             (-> result (assoc (first kv) (second kv)))
                             (-> result))))]
               (reduce-kv f0 {} n)))))

(defn to-nil
  ; @description
  ; Converts the given 'n' vector into a NIL value.
  ;
  ; @param (vector) n
  ; @param (map)(opt) options
  ; {:if-empty? (boolean)(opt)
  ;   Converts only if the given 'n' vector is empty.
  ;   Default: true}
  ;
  ; @usage
  ; (to-nil [])
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-nil [:a :b :c])
  ; =>
  ; [:a :b :c]
  ;
  ; @return (nil or vector)
  ([n]
   (to-nil n {}))

  ([n {:keys [if-empty?] :or {if-empty? true} :as options}]
   ; Alternative: 'not-empty'
   ; https://clojuredocs.org/clojure.core/not-empty
   (let [n (mixed/to-vector n)]
        (cond (-> n empty?)       (-> nil)
              (-> if-empty? not)  (-> nil)
              :return n))))
