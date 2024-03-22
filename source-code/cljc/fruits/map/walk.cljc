
(ns fruits.map.walk
    (:require [fruits.mixed.api :as mixed]))

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
  ;   If TRUE, provides the corresponding value to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->keys {:a "A" :b "B"} name)
  ; =>
  ; {"a" "A" "b" "B"}
  ;
  ; @return (map)
  ([n f]
   (->keys n f {}))

  ([n f {:keys [provide-value?]}]
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [       k v] (if provide-value? (f k v) (f k)))
                (f1 [result k v] (assoc result (f0 k v) v))]
               (reduce-kv f1 {} n)))))

(defn ->keys-by
  ; @description
  ; - Applies the given 'f' function on keys of the given 'n' map that for the given 'test-f' function returns TRUE.
  ; - The 'f' function takes a key and optionally the corresponding value as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->keys-by {0 "A" 1 "B"} even? dec)
  ; =>
  ; {-1 "A" 1 "B"}
  ;
  ; @return (map)
  ([n test-f f]
   (->keys-by n test-f f {}))

  ([n test-f f {:keys [provide-value?]}]
   (let [n      (mixed/to-map n)
         test-f (mixed/to-ifn test-f)
         f      (mixed/to-ifn f)]
        (letfn [(f0 [       k v] (if provide-value? (f k v) (f k)))
                (f1 [       k v] (if (test-f k) (f0 k v) k))
                (f2 [result k v] (assoc result (f1 k v) v))]
               (reduce-kv f2 {} n)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->>keys
  ; @description
  ; - Applies the given 'f' function on each key of the given 'n' map (recursivelly).
  ; - The 'f' function takes a key and optionally the corresponding value as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->>keys {:a "A" :b "B" :c [{:d "D"}]} name)
  ; =>
  ; {"a" "A" "b" "B" "c" [{"d" "D"}]}
  ;
  ; @return (map)
  ([n f]
   (->>keys n f {}))

  ([n f {:keys [provide-value?]}]
   ; DOES NOT apply the 'f' function on vector items, because vector items are equivalents to map values and NOT to map keys!
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [k v]    (if provide-value? (f k v) (f k)))
                (f1 [result] (cond (vector? result) (reduce    #(conj  %1            (f1 %2)) [] result)
                                   (map?    result) (reduce-kv #(assoc %1 (f0 %2 %3) (f1 %3)) {} result)
                                   :return  result))]
               (f1 n)))))

(defn ->>keys-by
  ; @description
  ; - Applies the given 'f' function on keys of the given 'n' map (recursivelly) that for the given 'test-f' function returns TRUE.
  ; - The 'f' function takes a key and optionally the corresponding value as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->>keys-by {0 "A" 1 [{2 "B"}]} even? dec)
  ; =>
  ; {-1 "A" 1 [{1 "B"}]}
  ;
  ; @return (map)
  ([n test-f f]
   (->>keys-by n test-f f {}))

  ([n test-f f {:keys [provide-value?]}]
   ; DOES NOT apply the 'f' function on vector items, because vector items are equivalents to map values and NOT to map keys!
   (let [n      (mixed/to-map n)
         test-f (mixed/to-ifn test-f)
         f      (mixed/to-ifn f)]
        (letfn [(f0 [k v]    (if provide-value? (f k v) (f k)))
                (f1 [k v]    (if (test-f k) (f0 k v) k))
                (f2 [result] (cond (vector? result) (reduce    #(conj  %1            (f2 %2)) [] result)
                                   (map?    result) (reduce-kv #(assoc %1 (f1 %2 %3) (f2 %3)) {} result)
                                   :return  result))]
               (f2 n)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->values
  ; @description
  ; - Applies the given 'f' function on each value of the given 'n' map.
  ; - The 'f' function takes a value and optionally the current result and/or the corresponding key as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:on-self? (boolean)(opt)
  ;   If TRUE, applies the given 'f' function on the given 'n' value also.
  ;  :provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key to the given 'f' function.
  ;   Default: false
  ;  :provide-result? (boolean)(opt)
  ;   If TRUE, provides the current result to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->values {:a "A" :b "B"} keyword)
  ; =>
  ; {:a :A :b :B}
  ;
  ; @return (map)
  ([n f]
   (->values n f {}))

  ([n f {:keys [on-self? provide-key? provide-result?]}]
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [result k v] (if provide-result? (if provide-key? (f result k v) (f result v))
                                                     (if provide-key? (f        k v) (f        v))))
                (f1 [result k v] (assoc result k (f0 result k v)))]
               (reduce-kv f1 {} (if on-self? (f0 nil nil n)
                                             (->         n)))))))

(defn ->values-by
  ; @description
  ; - Applies the given 'f' function on values of the given 'n' map that for the given 'test-f' function returns TRUE.
  ; - The 'f' function takes a value and optionally the current result and/or the corresponding key as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:on-self? (boolean)(opt)
  ;   If TRUE, applies the given 'f' function on the given 'n' value also.
  ;  :provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key to the given 'f' function.
  ;   Default: false
  ;  :provide-result? (boolean)(opt)
  ;   If TRUE, provides the current result to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->values-by {:a 0 :b 1} even? inc)
  ; =>
  ; {:a 1 :b 1}
  ;
  ; @return (map)
  ([n test-f f]
   (->values-by n test-f f {}))

  ([n test-f f {:keys [on-self? provide-key? provide-result?]}]
   (let [n      (mixed/to-map n)
         test-f (mixed/to-ifn test-f)
         f      (mixed/to-ifn f)]
        (letfn [(f0 [result k v] (if provide-result? (if provide-key? (f result k v) (f result v))
                                                     (if provide-key? (f        k v) (f        v))))
                (f1 [result k v] (if (test-f v) (f0 result k v) v))
                (f2 [result k v] (assoc result k (f1 result k v)))]
               (reduce-kv f2 {} (if on-self? (f1 nil nil n)
                                             (->         n)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->>values
  ; @description
  ; - Applies the given 'f' function on each value of the given 'n' map (recursivelly).
  ; - The 'f' function takes a value and optionally the current result and/or the corresponding key or path as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:on-self? (boolean)(opt)
  ;   If TRUE, applies the given 'f' function on the given 'n' value also.
  ;   Default: false
  ;  :provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key to the given 'f' function.
  ;   Default: false
  ;  :provide-path? (boolean)(opt)
  ;   If TRUE, provides the corresponding path to the given 'f' function.
  ;   Default: false
  ;  :provide-result? (boolean)(opt)
  ;   If TRUE, provides the current result to the given 'f' function.}
  ;
  ; @usage
  ; (->>values {:a "A" :b "B" :c [:d "E" {:f "F"}]} keyword)
  ; =>
  ; {:a :A :b :B :c [:d :e {:f :F}]}
  ;
  ; @return (map)
  ([n f]
   (->>values n f {}))

  ([n f {:keys [on-self? provide-key? provide-path? provide-result?]}]
   ; Applies the 'f' function on vector items also, because vector items are equivalents to map values!
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [result path v] (if provide-result? (if provide-key? (f result (last path) v) (if provide-path? (f result path v) (f result v)))
                                                        (if provide-key? (f        (last path) v) (if provide-path? (f        path v) (f        v)))))
                (f1 [result path v] (cond (-> on-self?)        (f0 result path v)
                                          (-> path empty? not) (f0 result path v)
                                          :return              (->             v)))
                (f2 [result path v] (cond (-> v map?)          (reduce-kv #(assoc %1 %2 (f2 %1 (conj path %2) %3)) {} (f1 result path v))
                                          (-> v vector?)       (reduce-kv #(conj  %1    (f2 %1 (conj path %2) %3)) [] (f1 result path v))
                                          :return                                                                     (f1 result path v)))]
               (f2 {} [] n)))))

(defn ->>values-by
  ; @description
  ; - Applies the given 'f' function on values of the given 'n' map (recursivelly) that for the given 'test-f' function returns TRUE.
  ; - The 'f' function takes a value and optionally the current result and/or the corresponding key or path as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:on-self? (boolean)(opt)
  ;   If TRUE, applies the given 'f' function on the given 'n' value also.
  ;  :provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key to the given 'f' function.
  ;   Default: false
  ;  :provide-path? (boolean)(opt)
  ;   If TRUE, provides the corresponding path to the given 'f' function.
  ;   Default: false
  ;  :provide-result? (boolean)(opt)
  ;   If TRUE, provides the current result to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->>values-by {:a 0 :b 1 :c [:d 2 {:f 3}]} integer? inc)
  ; =>
  ; {:a 1 :b 2 :c [:d 3 {:f 4}]}
  ;
  ; @return (map)
  ([n test-f f]
   (->>values-by n test-f f {}))

  ([n test-f f {:keys [on-self? provide-key? provide-path? provide-result?]}]
   ; Applies the 'f' function on vector items also, because vector items are equivalents to map values!
   (let [n      (mixed/to-map n)
         test-f (mixed/to-ifn test-f)
         f      (mixed/to-ifn f)]
        (letfn [(f0 [result path v] (if provide-result? (if provide-key? (f result (last path) v) (if provide-path? (f result path v) (f result v)))
                                                        (if provide-key? (f        (last path) v) (if provide-path? (f        path v) (f        v)))))
                (f1 [result path v] (if   (-> v test-f)        (f0 result path v) v))
                (f2 [result path v] (cond (-> on-self?)        (f1 result path v)
                                          (-> path empty? not) (f1 result path v)
                                          :return              (->             v)))
                (f3 [result path v] (cond (-> v map?)          (reduce-kv #(assoc %1 %2 (f3 %1 (conj path %2) %3)) {} (f2 result path v))
                                          (-> v vector?)       (reduce-kv #(conj  %1    (f3 %1 (conj path %2) %3)) [] (f2 result path v))
                                          :return                                                                     (f2 result path v)))]
               (f3 {} [] n)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->kv
  ; @description
  ; - Applies the given 'k-f' function on each key and the given 'v-f' function on each value of the given 'n' map.
  ; - The 'k-f' function takes a key and optionally the corresponding value as parameter(s).
  ;   The 'v-f' function takes a value and optionally the current result and/or the corresponding key as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) k-f
  ; @param (function) v-f
  ; @param (map)(opt) options
  ; {:on-self? (boolean)(opt)
  ;   If TRUE, applies the given 'v-f' function on the given 'n' value also.
  ;  :provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key to the given 'v-f' function.
  ;   Default: false
  ;  :provide-result? (boolean)(opt)
  ;   If TRUE, provides the current result to the given 'v-f' function.
  ;   Default: false
  ;  :provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value to the given 'k-f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->kv {:a 1 :b 2} name inc)
  ; =>
  ; {"a" 2 "b" 3}
  ;
  ; @return (map)
  ([n k-f v-f]
   (->kv n k-f v-f {}))

  ([n k-f v-f {:keys [on-self? provide-key? provide-result? provide-value?]}]
   (let [n   (mixed/to-map n)
         k-f (mixed/to-ifn k-f)
         v-f (mixed/to-ifn v-f)]
        (letfn [(f0 [       k v] (if provide-value? (k-f k v) (k-f k)))
                (f1 [result k v] (if provide-result? (if provide-key? (v-f result k v) (v-f result v))
                                                     (if provide-key? (v-f        k v) (v-f        v))))
                (f2 [result k v] (assoc result (f0 k v) (f1 result k v)))]
               (reduce-kv f2 {} (if on-self? (f1 nil nil n)
                                             (->         n)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->>kv
  ; @description
  ; - Applies the given 'k-f' function on each key and the given 'v-f' function on each value of the given 'n' map (recursivelly).
  ; - The 'k-f' function takes a key and optionally the corresponding value as parameter(s).
  ;   The 'v-f' function takes a value and optionally the current result and/or the corresponding key or path as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) k-f
  ; @param (function) v-f
  ; @param (map)(opt) options
  ; {:on-self? (boolean)(opt)
  ;   If TRUE, applies the given 'v-f' function on the given 'n' value also.
  ;  :provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key to the given 'v-f' function.
  ;   Default: false
  ;  :provide-path? (boolean)(opt)
  ;   If TRUE, provides the corresponding path to the given 'v-f' function.
  ;   Default: false
  ;  :provide-result? (boolean)(opt)
  ;   If TRUE, provides the current result to the given 'v-f' function.
  ;   Default: false
  ;  :provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value to the given 'k-f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->>kv {"a" "A" "b" "B" "c" ["D" "E" {"f" "F"}]} keyword keyword)
  ; =>
  ; {:a :A :b :B :c [:D :E {:f :F}]}
  ;
  ; @return (map)
  ([n k-f v-f]
   (->>kv n k-f v-f {}))

  ([n k-f v-f {:keys [on-self? provide-key? provide-path? provide-result? provide-value?]}]
   ; Applies the 'v-f' function on vector items also, because vector items are equivalents to map values!
   (let [n   (mixed/to-map n)
         k-f (mixed/to-ifn k-f)
         v-f (mixed/to-ifn v-f)]
        (letfn [(f0 [          k v] (if provide-value? (k-f k v) (k-f k)))
                (f1 [result path v] (if provide-result? (if provide-key? (v-f result (last path) v) (if provide-path? (v-f result path v) (v-f result v)))
                                                        (if provide-key? (v-f        (last path) v) (if provide-path? (v-f        path v) (v-f        v)))))
                (f2 [result path v] (cond (-> on-self?)        (f1 result path v)
                                          (-> path empty? not) (f1 result path v)
                                          :return              (->             v)))
                (f3 [result path v] (cond (-> v map?)          (reduce-kv #(assoc %1 (f0 %2 %3) (f3 %1 (conj path %2) %3)) {} (f2 result path v))
                                          (-> v vector?)       (reduce-kv #(conj  %1            (f3 %1 (conj path %2) %3)) [] (f2 result path v))
                                          :return                                                                             (f2 result path v)))]
               (f3 {} [] n)))))
