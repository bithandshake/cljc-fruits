
(ns map.remove)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-keys
  ; @param (map) n
  ; @param (vector) keys
  ;
  ; @usage
  ; (remove-keys {:a "A" :b "B"} [:a])
  ;
  ; @example
  ; (remove-keys {:a "A" :b "B" :c "C"} [:a :c])
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  [n keys]
  (reduce dissoc n keys))

(defn remove-keys-by
  ; @param (map) n
  ; @param (function) r-f
  ;
  ; @usage
  ; (remove-keys-by {:a "A"} #(= % :a))
  ;
  ; @example
  ; (remove-keys-by {:a "A" :b "B" :c "C"} #(= % :a))
  ; =>
  ; {:b "B" :c "C"}
  ;
  ; @return (map)
  [n r-f]
  (letfn [(f [%1 %2 %3]
             (if (-> %2 r-f)
                 (-> %1)
                 (-> %1 (assoc %2 %3))))]
         (reduce-kv f {} n)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-values
  ; @param (map) n
  ; @param (vector) values
  ;
  ; @usage
  ; (remove-values {:a "A"} ["A"])
  ;
  ; @example
  ; (remove-values {:a "A" :b "B" :c "C"} ["A" "B"])
  ; =>
  ; {:c "C"}
  ;
  ; @return (map)
  [n values])
  ; TODO

(defn remove-values-by
  ; @param (map) n
  ; @param (vector) r-f
  ;
  ; @example
  ; (remove-values-by {:a "A"} #(= % "A"))
  ;
  ; @example
  ; (remove-values-by {:a "A" :b "B" :c "C"} #(= % "A"))
  ; =>
  ; {:b "B" :c "C"}
  ;
  ; @return (map)
  [n r-f]
  (letfn [(f [%1 %2 %3]
             (if (-> %3 r-f)
                 (-> %1)
                 (-> %1 (assoc %2 %3))))]
         (reduce-kv f {} n)))
