
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
   (let [result (dissoc n k)]
        (letfn [(f0 [result k v]
                    (if (-> v map?) (assoc result k (remove-key v k options))
                                    (assoc result k (-> v))))]
               (if recur? (reduce-kv f0 {} result)
                          (-> result))))))

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
   (let [result (reduce dissoc n ks)]
        (letfn [(f0 [result k v]
                    (if (-> v map?) (assoc result k (remove-keys v ks options))
                                    (assoc result k (-> v))))]
               (if recur? (reduce-kv f0 {} result)
                          (-> result))))))

(defn remove-keys-by
  ; @param (map) n
  ; @param (function) r-f
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
  ([n r-f]
   (remove-keys-by n r-f {}))

  ([n r-f {:keys [recur?] :as options}]
   (letfn [(f0 [result k v]
               (cond (-> k r-f)      (-> result)
                     (-> v map? not) (-> result (assoc k v))
                     recur?          (-> result (assoc k (remove-keys-by v r-f options)))
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

  ([n v {:keys [recur?] :as options}]))
   ; TODO

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

  ([n vs {:keys [recur?] :as options}]))
   ; TODO

(defn remove-values-by
  ; @param (map) n
  ; @param (function) r-f
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
  ([n r-f]
   (remove-values-by n r-f {}))

  ([n r-f {:keys [recur?] :as options}]
   (letfn [(f0 [result k v]
               (cond (-> v r-f)      (-> result)
                     (-> v map? not) (-> result (assoc k v))
                     recur?          (-> result (assoc k (remove-values-by v r-f options)))
                     :else           (-> result (assoc k v))))]
          (reduce-kv f0 {} n))))
