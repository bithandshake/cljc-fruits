
(ns map.remove)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-key
  ; @param (map) n
  ; @param (*) k
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (remove-key {:a "A" :b "B"} :a)
  ;
  ; @example
  ; (remove-key {:a "A" :b "B"} :a)
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  ([n k]
   (remove-key n k {}))

  ([n k {:keys [recur?] :as options}]
   (letfn [(f0 [result ki v] (cond (-> ki (= k))   (-> result)
                                   (-> recur? not) (-> result (assoc ki v))
                                   (-> v map?)     (-> result (assoc ki (remove-key v k options)))
                                   :else           (-> result (assoc ki v))))]
          (reduce-kv f0 {} n))))

(defn remove-keys
  ; @param (map) n
  ; @param (vector) ks
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (remove-keys {:a "A" :b "B" :c "C"} [:a :c])
  ;
  ; @example
  ; (remove-keys {:a "A" :b "B" :c "C"} [:a :c])
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  ([n ks]
   (remove-keys n ks {}))

  ([n ks {:keys [recur?] :as options}]
   (letfn [(f0 [k] (some (fn [%] (= k %)) ks))
           (f1 [result k v] (cond (-> k f0)       (-> result)
                                  (-> recur? not) (-> result (assoc k v))
                                  (-> v map?)     (-> result (assoc k (remove-keys v ks options)))
                                  :else           (-> result (assoc k v))))]
          (reduce-kv f1 {} n))))

(defn remove-keys-by
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (remove-keys-by {0 "0" 1 "1" 2 "2"} even?)
  ;
  ; @example
  ; (remove-keys-by {0 "0" 1 "1" 2 "2"} even?)
  ; =>
  ; {1 "1"}
  ;
  ; @return (map)
  ([n f]
   (remove-keys-by n f {}))

  ([n f {:keys [recur?] :as options}]
   (letfn [(f0 [result k v] (cond (-> k f)        (-> result)
                                  (-> recur? not) (-> result (assoc k v))
                                  (-> v map?)     (-> result (assoc k (remove-keys-by v f options)))
                                  :else           (-> result (assoc k v))))]
          (reduce-kv f0 {} n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-value
  ; @param (map) n
  ; @param (*) v
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (remove-value {:a "A" :b "B"} "A")
  ;
  ; @example
  ; (remove-value {:a "A" :b "B"} "A")
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  ([n v]
   (remove-value n v {}))

  ([n v {:keys [recur?] :as options}]
   (letfn [(f0 [result k vi] (cond (-> vi (= v))   (-> result)
                                   (-> recur? not) (-> result (assoc k vi))
                                   (-> vi map?)    (-> result (assoc k (remove-value vi v options)))
                                   :else           (-> result (assoc k vi))))]
          (reduce-kv f0 {} n))))

(defn remove-values
  ; @param (map) n
  ; @param (vector) vs
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (remove-values {:a "A" :b "B" :c "C"} ["A" "C"])
  ;
  ; @example
  ; (remove-values {:a "A" :b "B" :c "C"} ["A" "C"])
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  ([n vs]
   (remove-values n vs {}))

  ([n vs {:keys [recur?] :as options}]
   (letfn [(f0 [v] (some (fn [%] (= v %)) vs))
           (f1 [result k v] (cond (-> v f0)       (-> result)
                                  (-> recur? not) (-> result (assoc k v))
                                  (-> v map?)     (-> result (assoc k (remove-values v vs options)))
                                  :else           (-> result (assoc k v))))]
          (reduce-kv f1 {} n))))

(defn remove-values-by
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (remove-values-by {:a :a :b :b :c "C"} keyword?)
  ;
  ; @example
  ; (remove-values-by {:a :a :b :b :c "C"} keyword?)
  ; =>
  ; {:c "C"}
  ;
  ; @return (map)
  ([n f]
   (remove-values-by n f {}))

  ([n f {:keys [recur?] :as options}]
   (letfn [(f0 [result k v] (cond (-> v f)        (-> result)
                                  (-> recur? not) (-> result (assoc k v))
                                  (-> v map?)     (-> result (assoc k (remove-values-by v f options)))
                                  :else           (-> result (assoc k v))))]
          (reduce-kv f0 {} n))))
