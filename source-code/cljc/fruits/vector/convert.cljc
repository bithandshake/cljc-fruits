
(ns fruits.vector.convert
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn from-subvec
  ; @description
  ; Converts the given 'n' subvector into vector.
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
  ; Converts the given 'n' vector into map.
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
  ; Converts the given 'n' vector into NIL.
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

(defn to-associative
  ; @description
  ; Converts the given 'n' value into vector, in case it does not implement the IAssociative protocol.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-associative {:a "A"})
  ; =>
  ; {:a "A"}
  ;
  ; @usage
  ; (to-associative [:a :b :c])
  ; =>
  ; [:a :b :c]
  ;
  ; @usage
  ; (to-associative nil)
  ; =>
  ; []
  ;
  ; @usage
  ; (to-associative "abc")
  ; =>
  ; ["abc"]
  ;
  ; @usage
  ; (to-associative 123)
  ; =>
  ; [123]
  ;
  ; @return (vector or associative *)
  [n]
  (cond (-> n associative?) (-> n)
        (-> n nil?)         (-> [])
        :else               (-> [n])))

(defn to-seqable
  ; @description
  ; Converts the given 'n' value into vector, in case it does not implement the ISeqable protocol.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-seqable {:a "A"})
  ; =>
  ; {:a "A"}
  ;
  ; @usage
  ; (to-seqable [:a :b :c])
  ; =>
  ; [:a :b :c]
  ;
  ; @usage
  ; (to-seqable nil)
  ; =>
  ; {}
  ;
  ; @usage
  ; (to-seqable "abc")
  ; =>
  ; "abc"
  ;
  ; @usage
  ; (to-seqable 123)
  ; =>
  ; [123]
  ;
  ; @return (map or seqable *)
  [n]
  (if (-> n seqable?)
      (-> n)
      (-> [n])))
