
(ns map.namespace)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-namespace
  ; @param (map) n
  ;
  ; @usage
  ; (get-namespace {:a/b "A"})
  ;
  ; @example
  ; (get-namespace {:a "A"})
  ; =>
  ; nil
  ;
  ; @example
  ; (get-namespace {:a/b "A"})
  ; =>
  ; :a
  ;
  ; @example
  ; (get-namespace {:a   "A"
  ;                 :b   "B"
  ;                 :c/d "C"
  ;                 :e/f "E"})
  ; =>
  ; :c
  ;
  ; @example
  ; (get-namespace {"a/b" "A"})
  ; =>
  ; :a
  ;
  ; @return (keyword)
  [n]
  (letfn [(f [item-key]
             (cond (string? item-key)
                   (if-let [namespace (-> item-key keyword namespace)]
                           (keyword namespace))
                   (keyword? item-key)
                   (if-let [namespace (-> item-key         namespace)]
                           (keyword namespace))))]
         (some f (keys n))))

(defn namespaced?
  ; @param (map) n
  ;
  ; @usage
  ; (namespaced? {:a/b "A"})
  ;
  ; @example
  ; (namespaced? {:a "A"})
  ; =>
  ; false
  ;
  ; @example
  ; (namespaced? {:a/b "A"})
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (-> n get-namespace some?))

(defn add-namespace
  ; @param (map) n
  ; @param (keyword) namespace
  ;
  ; @usage
  ; (add-namespace {:a "A"} :b)
  ;
  ; @example
  ; (add-namespace {:a "A"} :b)
  ; =>
  ; {:a/b "A"}
  ;
  ; @example
  ; (add-namespace {"a" "A"} :b)
  ; =>
  ; {"a/b" "A"}
  ;
  ; @return (map)
  [n namespace]
  (letfn [(f [n item-key item-value]
             (cond (string?  item-key) (assoc n (str     (name namespace) "/"   item-key)  item-value)
                   (keyword? item-key) (assoc n (keyword (name namespace) (name item-key)) item-value)
                   :return n))]
         (reduce-kv f {} n)))

(defn remove-namespace
  ; @param (map) n
  ;
  ; @usage
  ; (remove-namespace {:a/b "A"})
  ;
  ; @example
  ; (remove-namespace {:a/b "A"})
  ; =>
  ; {:a "A"}
  ;
  ; @example
  ; (remove-namespace {"a/b" "A"})
  ; =>
  ; {"a" "A"}
  ;
  ; @return (map)
  [n]
  (letfn [(f [n item-key item-value]
             (cond (string?  item-key) (assoc n (-> item-key keyword name)         item-value)
                   (keyword? item-key) (assoc n (-> item-key         name keyword) item-value)
                   :return n))]
         (reduce-kv f {} n)))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn assoc-ns
  ; @param (map) n
  ; @param (keyword) key
  ; @param (*) value
  ;
  ; @example
  ; (assoc-ns {:fruit/apple "red"} :banana "yellow")
  ; =>
  ; {:fruit/apple "red" :fruit/banana "yellow"}
  ;
  ; @return (map)
  [n key value])
  ; TODO

(defn get-ns
  ; @param (map) n
  ; @param (keyword) key
  ;
  ; @example
  ; (get-ns {:fruit/apple "red"} :apple)
  ; =>
  ; "red"
  ;
  ; @return (*)
  [n key]
  (if-let [namespace (get-namespace n)]
          (let [key (keyword (name namespace) (name key))]
               (get n key))))
