
(ns map.walk
    (:require [map.remove :as remove]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->keys
  ; @description
  ; - Applies the given 'f' function on each key of the given 'n' map.
  ; - The 'f' function takes a key and optionally the corresponding value as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value also to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->keys {"a" "A" "b" "B"} keyword)
  ;
  ; @example
  ; (->keys {:a "A" :b "B"} name)
  ; =>
  ; {"a" "A" "b" "B"}
  ;
  ; @return (map)
  ([n f]
   (->keys n f {}))

  ([n f {:keys [provide-value?]}]
   (letfn [(f0 [  k v] (if provide-value? (f k v) (f k)))
           (f1 [r k v] (assoc r (f0 k v) v))]
          (reduce-kv f1 {} n))))

(defn ->>keys
  ; @description
  ; - Applies the given 'f' function on each key of the given 'n' map (recursivelly).
  ; - The 'f' function takes a key and optionally the corresponding value as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value also to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->>keys {:a "A" :b [{:c "C"}]} name)
  ;
  ; @example
  ; (->>keys {:a "A" :b "B" :c [{:d "D"}]} name)
  ; =>
  ; {"a" "A" "b" "B" "c" [{"d" "D"}]}
  ;
  ; @return (map)
  ([n f]
   (->>keys n f {}))

  ([n f {:keys [provide-value?]}]
   ; DOES NOT apply the 'f' function on vector items, because vector items are equivalents to map values and NOT to map keys!
   (letfn [(f0 [k v] (if provide-value? (f k v) (f v)))
           (f1 [r]   (cond (vector? r) (reduce    #(conj  %1            (f1 %2)) [] r)
                           (map?    r) (reduce-kv #(assoc %1 (f0 %2 %3) (f1 %3)) {} r)
                           :return  r))]
          (f1 n))))

(defn ->values
  ; @description
  ; - Applies the given 'f' function on each value of the given 'n' map.
  ; - The 'f' function takes a value and optionally the corresponding key as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key also to the given 'f' function.
  ;   Default: false}
  ;
  ; @example
  ; (->values {:a "A" :b "B"} keyword)
  ; =>
  ; {:a :A :b :B}
  ;
  ; @return (map)
  ([n f]
   (->values n f {}))

  ([n f {:keys [provide-key?]}]
   (letfn [(f0 [  k v] (if provide-key? (f k v) (f v)))
           (f1 [r k v] (assoc r k (f0 k v)))]
          (reduce-kv f1 {} n))))

(defn ->>values
  ; @description
  ; - Applies the given 'f' function on each value of the given 'n' map (recursivelly).
  ; - The 'f' function takes a value and optionally the corresponding key or path as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key also to the given 'f' function.
  ;   Default: false
  ;  :provide-path? (boolean)(opt)
  ;   If TRUE, provides the corresponding path also to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->>values {:a "A" :b ["C"]} keyword)
  ;
  ; @example
  ; (->>values {:a "A" :b "B" :c [:d "E" {:f "F"}]} keyword)
  ; =>
  ; {:a :A :b :B :c [:d :e {:f :F}]}
  ;
  ; @return (map)
  ([n f]
   (->>values n f {}))

  ([n f {:keys [provide-key? provide-path?]}]
   ; Applies the 'f' function on vector items as well, because vector items are equivalents to map values!
   (letfn [(f0 [p v] (if provide-key? (f (last p) v) (if provide-path? (f p v) (f v))))
           (f1 [p v] (cond (map?    v) (reduce-kv #(assoc %1 %2 (f1 (conj p %2) %3)) {} v)
                           (vector? v) (reduce-kv #(conj  %1    (f1 (conj p %2) %3)) [] v)
                           :return     (f0 p v)))]
          (f1 [] n))))

(defn ->kv
  ; @description
  ; - Applies the given 'k-f' function on each key and the given 'v-f' function on each value of the given 'n' map.
  ; - The 'k-f' function takes a key and optionally the corresponding value as parameter(s).
  ;   The 'v-f' function takes a value and optionally the corresponding key as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) k-f
  ; @param (function) v-f
  ; @param (map)(opt) options
  ; {:provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key also to the given 'v-f' function.
  ;   Default: false
  ;  :provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value also to the given 'k-f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->kv {:a 1} name inc)
  ;
  ; @example
  ; (->kv {:a 1 :b 2} name inc)
  ; =>
  ; {"a" 2 "b" 3}
  ;
  ; @return (map)
  ([n k-f v-f]
   (->kv n k-f v-f {}))

  ([n k-f v-f {:keys [provide-key? provide-value?]}]
   (letfn [(f0 [  k v] (if provide-value? (k-f k v) (k-f k)))
           (f1 [  k v] (if provide-key?   (v-f k v) (v-f v)))
           (f2 [r k v] (assoc r (f0 k v) (f1 k v)))]
          (reduce-kv f2 {} n))))

(defn ->>kv
  ; @description
  ; - Applies the given 'k-f' function on each key and the given 'v-f' function on each value of the given 'n' map (recursivelly).
  ; - The 'k-f' function takes a key and optionally the corresponding value as parameter(s).
  ;   The 'v-f' function takes a value and optionally the corresponding key or path as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) k-f
  ; @param (function) v-f
  ; @param (map)(opt) options
  ; {:provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key also to the given 'v-f' function.
  ;   Default: false
  ;  :provide-path? (boolean)(opt)
  ;   If TRUE, provides the corresponding path also to the given 'v-f' function.
  ;   Default: false
  ;  :provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value also to the given 'k-f' function.
  ;   Default: false}
  ;
  ; @example
  ; (->>kv {"a" "A" "b" ["C"]} keyword keyword)
  ;
  ; @example
  ; (->>kv {"a" "A" "b" "B" "c" ["D" "E" {"f" "F"}]} keyword keyword)
  ; =>
  ; {:a :A :b :B :c [:D :E {:f :F}]}
  ;
  ; @return (map)
  ([n k-f v-f]
   (->>kv n k-f v-f {}))

  ([n k-f v-f {:keys [provide-key? provide-path? provide-value?]}]
   ; Applies the 'v-f' function on vector items as well, because vector items are equivalents to map values!
   (letfn [(f0 [k v] (if provide-value? (k-f k v) (k-f k)))
           (f1 [p v] (if provide-key?   (v-f (last p) v) (if provide-path? (v-f p v) (v-f v))))
           (f2 [p v] (cond (map?    v) (reduce-kv #(assoc %1 (f0 %2 %3) (f2 (conj p %2) %3)) {} v)
                           (vector? v) (reduce-kv #(conj  %1            (f2 (conj p %2) %3)) [] v)
                           :return     (f1 p v)))]
          (f2 [] n))))

(defn ->remove-keys-by
  ; @description
  ; - Removes specific keys from the given 'n' map.
  ;   Decides which keys to be removed by applying the given 'r-f' function on them.
  ; - The 'r-f' function takes a key as parameter.
  ;   If the 'r-f' function returns TRUE, the key will be removed.
  ;
  ; @param (map) n
  ; @param (function) r-f
  ;
  ; @usage
  ; (->remove-keys-by {:a "A"} #(= % :a))
  ;
  ; @example
  ; (->remove-keys-by {:a "A" :b "B"}
  ;                   #(= % :a))
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  [n r-f]
  (remove/remove-keys-by n r-f))

(defn ->>remove-keys-by
  ; @description
  ; - Removes specific keys from the given 'n' map (recursivelly).
  ; - Decides which keys to be removed by applying the given 'r-f' function on them.
  ; - The 'r-f' function takes a key as parameter.
  ;   If the 'r-f' function returns TRUE, the key will be removed.
  ;
  ; @param (map) n
  ; @param (function) r-f
  ;
  ; @example
  ; (->>remove-keys-by {:a "A" :b "B" :c {:a "A" :b "B"}}
  ;                    #(= % :a))
  ; =>
  ; {:b "B" :c {:b "B"}}
  ;
  ; @return (map)
  [n r-f])
  ; TODO

(defn ->remove-values-by
  ; @description
  ; - Removes specific values from the given 'n' map.
  ;   Decides which values to be removed by applying the given 'r-f' function on them.
  ; - The 'r-f' function takes a value as parameter.
  ;   If the 'r-f' function returns TRUE, the value will be removed.
  ;
  ; @param (map) n
  ; @param (function) r-f
  ;
  ; @example
  ; (->remove-values-by {:a "A" :b "B"}
  ;                     #(= % "A"))
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  [n r-f]
  (remove/remove-values-by n r-f))

(defn ->>remove-values-by
  ; @description
  ; - Removes specific values from the given 'n' map (recursivelly).
  ;   Decides which values to be removed by applying the given 'r-f' function on them.
  ; - The 'r-f' function takes a value as parameter.
  ;   If the 'r-f' function returns TRUE, the value will be removed.
  ;
  ; @param (map) n
  ; @param (function) r-f
  ;
  ; @usage
  ; (->>remove-values-by {:a "A" :b {:c [{:d "D"}]}}
  ;                      #(= % "D"))
  ;
  ; @example
  ; (->>remove-values-by {:a "A" :b "B" :c {:a "A" :b "B" :c [{:a "A"}]}}
  ;                      #(= % "A"))
  ; =>
  ; {:b "B" :c {:b "B" :c [{}]}}
  ;
  ; @return (map)
  [n r-f]
  ; Applies the 'f' function on vector items as well, because vector items are equivalents to map values!
  (letfn [(f0 [n k x] (if   (r-f     x) n (assoc n k (f2 x))))
          (f1 [n   x] (if   (r-f     x) n (conj  n   (f2 x))))
          (f2 [n]     (cond (map?    n)   (reduce-kv f0 {} n)
                            (vector? n)   (reduce    f1 [] n)
                            :return  n))]
         (f2 n)))
