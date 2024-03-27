
(ns fruits.map.remove
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-key
  ; @description
  ; Removes a specific key from the given 'n' map (optionally recursively).
  ;
  ; @param (map) n
  ; @param (*) k
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (remove-key {:a "A" :b "B"} :a)
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  ([n k]
   (remove-key n k {}))

  ([n k {:keys [recur?] :as options}]
   (let [n (mixed/to-map n)]
        (letfn [(f0 [result ki v] (cond (-> ki (= k))   (-> result)
                                        (-> recur? not) (-> result (assoc ki v))
                                        (-> v map?)     (-> result (assoc ki (remove-key v k options)))
                                        :else           (-> result (assoc ki v))))]
               (reduce-kv f0 {} n)))))

(defn remove-keys
  ; @description
  ; Removes specific keys from the given 'n' map (optionally recursively).
  ;
  ; @param (map) n
  ; @param (vector) ks
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (remove-keys {:a "A" :b "B" :c "C"} [:a :c])
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  ([n ks]
   (remove-keys n ks {}))

  ([n ks {:keys [recur?] :as options}]
   (let [n  (mixed/to-map n)
         ks (mixed/to-vector ks)]
        (letfn [(f0 [k] (some (fn [%] (= k %)) ks))
                (f1 [result k v] (cond (-> k f0)       (-> result)
                                       (-> recur? not) (-> result (assoc k v))
                                       (-> v map?)     (-> result (assoc k (remove-keys v ks options)))
                                       :else           (-> result (assoc k v))))]
               (reduce-kv f1 {} n)))))

(defn remove-keys-by
  ; @description
  ; Removes keys from the given 'n' map that match the given 'f' function (optionally recursively).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value to the given 'f' function.
  ;   Default: false
  ;  :recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (remove-keys-by {0 "A" 1 "B" 2 "C"} even?)
  ; =>
  ; {1 "1"}
  ;
  ; @return (map)
  ([n f]
   (remove-keys-by n f {}))

  ([n f {:keys [provide-value? recur?] :as options}]
   (let [n (mixed/to-map n)]
        (letfn [(f0 [       k v] (if provide-value? (f k v) (f k)))
                (f1 [result k v] (cond (f0 k v)        (-> result)
                                       (-> recur? not) (-> result (assoc k v))
                                       (-> v map?)     (-> result (assoc k (remove-keys-by v f options)))
                                       :else           (-> result (assoc k v))))]
               (reduce-kv f1 {} n)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-value
  ; @description
  ; Removes a specific value from the given 'n' map (optionally recursively).
  ;
  ; @param (map) n
  ; @param (*) v
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (remove-value {:a "A" :b "B"} "A")
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  ([n v]
   (remove-value n v {}))

  ([n v {:keys [recur?] :as options}]
   (let [n (mixed/to-map n)]
        (letfn [(f0 [result k vi] (cond (-> vi (= v))   (-> result)
                                        (-> recur? not) (-> result (assoc k vi))
                                        (-> vi map?)    (-> result (assoc k (remove-value vi v options)))
                                        :else           (-> result (assoc k vi))))]
               (reduce-kv f0 {} n)))))

(defn remove-values
  ; @description
  ; Removes specific values from the given 'n' map (optionally recursively).
  ;
  ; @param (map) n
  ; @param (vector) vs
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (remove-values {:a "A" :b "B" :c "C"} ["A" "C"])
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  ([n vs]
   (remove-values n vs {}))

  ([n vs {:keys [recur?] :as options}]
   (let [n  (mixed/to-map n)
         vs (mixed/to-vector vs)]
        (letfn [(f0 [v] (some (fn [%] (= v %)) vs))
                (f1 [result k v] (cond (-> v f0)       (-> result)
                                       (-> recur? not) (-> result (assoc k v))
                                       (-> v map?)     (-> result (assoc k (remove-values v vs options)))
                                       :else           (-> result (assoc k v))))]
               (reduce-kv f1 {} n)))))

(defn remove-values-by
  ; @description
  ; Removes values from the given 'n' map that match the given 'f' function (optionally recursively).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key to the given 'f' function.
  ;   Default: false
  ;  :recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (remove-values-by {:a 0 :b 1 :c 2} even?)
  ; =>
  ; {:b 1}
  ;
  ; @return (map)
  ([n f]
   (remove-values-by n f {}))

  ([n f {:keys [provide-key? recur?] :as options}]
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [       k v] (if provide-key? (f k v) (f v)))
                (f1 [result k v] (cond (f0 k v)        (-> result)
                                       (-> recur? not) (-> result (assoc k v))
                                       (-> v map?)     (-> result (assoc k (remove-values-by v f options)))
                                       :else           (-> result (assoc k v))))]
               (reduce-kv f1 {} n)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn keep-key
  ; @description
  ; Keeps only a specific key in the given 'n' map (optionally recursively).
  ;
  ; @param (map) n
  ; @param (*) k
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (keep-key {:a "A" :b "B"} :a)
  ; =>
  ; {:a "A"}
  ;
  ; @return (map)
  ([n k]
   (keep-key n k {}))

  ([n k {:keys [recur?] :as options}]
   (let [n (mixed/to-map n)]
        (letfn [(f0 [result ki v] (cond (-> ki (not= k)) (-> result)
                                        (-> recur? not)  (-> result (assoc ki v))
                                        (-> v map?)      (-> result (assoc ki (keep-key v k options)))
                                        :else            (-> result (assoc ki v))))]
               (reduce-kv f0 {} n)))))

(defn keep-keys
  ; @description
  ; Keeps only the given keys in the given 'n' map (optionally recursively).
  ;
  ; @param (map) n
  ; @param (vector) ks
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (keep-keys {:a "A" :b "B" :c "C"} [:a :c])
  ; =>
  ; {:a "A" :c "C"}
  ;
  ; @return (map)
  ([n ks]
   (keep-keys n ks {}))

  ([n ks {:keys [recur?] :as options}]
   (let [n  (mixed/to-map n)
         ks (mixed/to-vector ks)]
        (letfn [(f0 [k] (some (fn [%] (not= k %)) ks))
                (f1 [result k v] (cond (-> k f0)       (-> result)
                                       (-> recur? not) (-> result (assoc k v))
                                       (-> v map?)     (-> result (assoc k (keep-keys v ks options)))
                                       :else           (-> result (assoc k v))))]
               (reduce-kv f1 {} n)))))

(defn keep-keys-by
  ; @description
  ; Keeps only the keys in the given 'n' map that match the given 'f' function (optionally recursively).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value to the given 'f' function.
  ;   Default: false
  ;  :recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (keep-keys-by {0 "0" 1 "1" 2 "2"} even?)
  ; =>
  ; {0 "0" 2 "2"}
  ;
  ; @return (map)
  ([n f]
   (keep-keys-by n f {}))

  ([n f {:keys [provide-value? recur?] :as options}]
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [       k v] (if provide-value? (f k v) (f k)))
                (f1 [result k v] (cond (-> (f0 k v) not) (-> result)
                                       (-> recur? not)   (-> result (assoc k v))
                                       (-> v map?)       (-> result (assoc k (keep-keys-by v f options)))
                                       :else             (-> result (assoc k v))))]
               (reduce-kv f1 {} n)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn keep-value
  ; @description
  ; Keeps only a specific value in the given 'n' map (optionally recursively).
  ;
  ; @param (map) n
  ; @param (*) v
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (keep-value {:a "A" :b "B"} "A")
  ; =>
  ; {:a "A"}
  ;
  ; @return (map)
  ([n v]
   (keep-value n v {}))

  ([n v {:keys [recur?] :as options}]
   (let [n (mixed/to-map n)]
        (letfn [(f0 [result k vi] (cond (-> vi (not= v)) (-> result)
                                        (-> recur? not)  (-> result (assoc k vi))
                                        (-> vi map?)     (-> result (assoc k (keep-value vi v options)))
                                        :else            (-> result (assoc k vi))))]
               (reduce-kv f0 {} n)))))

(defn keep-values
  ; @description
  ; Keeps only the given values in the given 'n' map (optionally recursively).
  ;
  ; @param (map) n
  ; @param (vector) vs
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (keep-values {:a "A" :b "B" :c "C"} ["A" "C"])
  ; =>
  ; {:a "A" :c "C"}
  ;
  ; @return (map)
  ([n vs]
   (keep-values n vs {}))

  ([n vs {:keys [recur?] :as options}]
   (let [n  (mixed/to-map n)
         vs (mixed/to-vector vs)]
        (letfn [(f0 [v] (some (fn [%] (not= v %)) vs))
                (f1 [result k v] (cond (-> v f0)       (-> result)
                                       (-> recur? not) (-> result (assoc k v))
                                       (-> v map?)     (-> result (assoc k (keep-values v vs options)))
                                       :else           (-> result (assoc k v))))]
               (reduce-kv f1 {} n)))))

(defn keep-values-by
  ; @description
  ; Keeps only the values in the given 'n' map that match the given 'f' function (optionally recursively).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key to the given 'f' function.
  ;   Default: false
  ;  :recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (keep-values-by {:a 0 :b 1 :c 2} even?)
  ; =>
  ; {:a 0 :c 2}
  ;
  ; @return (map)
  ([n f]
   (keep-values-by n f {}))

  ([n f {:keys [provide-key? recur?] :as options}]
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [       k v] (if provide-key? (f k v) (f v)))
                (f1 [result k v] (cond (-> (f0 k v) not) (-> result)
                                       (-> recur? not)   (-> result (assoc k v))
                                       (-> v map?)       (-> result (assoc k (keep-values-by v f options)))
                                       :else             (-> result (assoc k v))))]
               (reduce-kv f1 {} n)))))
