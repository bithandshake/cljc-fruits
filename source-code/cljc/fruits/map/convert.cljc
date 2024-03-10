
(ns fruits.map.convert
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-longhand
  ; @description
  ; Converts the given 'n' value into a value of a specific key within the result only in case it is not a map already.
  ;
  ; @param (*) n
  ; @param (*) k
  ;
  ; @usage
  ; (to-longhand "A" :a)
  ; =>
  ; {:a "A"}
  ;
  ; @usage
  ; (to-longhand {:a "A"} :a)
  ; =>
  ; {:a "A"}
  ;
  ; @return (map)
  [n k]
  (if (-> n map?)
      (-> n)
      {k n}))

(defn to-vector
  ; @description
  ; Converts the given 'n' map into vector.
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
  ; Converts the given 'n' map into NIL.
  ;
  ; @param (map) n
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

(defn to-associative
  ; @description
  ; Converts the given 'n' value into map, in case it does not implement the IAssociative protocol.
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
  ; {}
  ;
  ; @usage
  ; (to-associative "abc")
  ; =>
  ; {0 "abc"}
  ;
  ; @usage
  ; (to-associative 123)
  ; =>
  ; {0 123}
  ;
  ; @return (map or associative *)
  [n]
  (cond (-> n associative?) (-> n)
        (-> n nil?)         (-> {})
        :else               (-> {0 n})))

(defn to-seqable
  ; @description
  ; Converts the given 'n' value into map, in case it does not implement the ISeqable protocol.
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
  ; {0 123}
  ;
  ; @return (map or seqable *)
  [n]
  (if (-> n seqable?)
      (-> n)
      (-> {0 n})))
