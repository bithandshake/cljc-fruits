
(ns fruits.map.namespace
    (:refer-clojure :exclude [namespace])
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn namespace
  ; @description
  ; Returns the namespace of the given 'n' map.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (namespace {:a "A"})
  ; =>
  ; nil
  ;
  ; @usage
  ; (namespace {:a/b "A"})
  ; =>
  ; :a
  ;
  ; @usage
  ; (namespace {:a   "A"
  ;             :b   "B"
  ;             :c/d "C"
  ;             :e/f "E"})
  ; =>
  ; :c
  ;
  ; @usage
  ; (namespace {"a/b" "A"})
  ; =>
  ; :a
  ;
  ; @return (keyword)
  [n]
  (let [n (mixed/to-map n)]
       (letfn [(f0 [k]
                   (cond (string?  k) (if-let [ns (-> k keyword clojure.core/namespace)] (keyword ns))
                         (keyword? k) (if-let [ns (-> k clojure.core/namespace)]         (keyword ns))))]
              (some f0 (keys n)))))

(defn add-namespace
  ; @description
  ; Adds a specific namespace to the keys of the given 'n' map.
  ;
  ; @param (map) n
  ; @param (keyword) ns
  ;
  ; @usage
  ; (add-namespace {:a "A"} :b)
  ; =>
  ; {:a/b "A"}
  ;
  ; @usage
  ; (add-namespace {"a" "A"} :b)
  ; =>
  ; {"a/b" "A"}
  ;
  ; @return (map)
  [n ns]
  (let [n  (mixed/to-map n)
        ns (mixed/to-keyword ns)]
       (letfn [(f0 [n k v]
                   (cond (string?  k) (assoc n (str     (name ns) "/"   k)  v)
                         (keyword? k) (assoc n (keyword (name ns) (name k)) v)
                         :return n))]
              (reduce-kv f0 {} n))))

(defn remove-namespace
  ; @description
  ; Removes the namespace of the keys in the given 'n' map.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (remove-namespace {:a/b "A"})
  ; =>
  ; {:a "A"}
  ;
  ; @usage
  ; (remove-namespace {"a/b" "A"})
  ; =>
  ; {"a" "A"}
  ;
  ; @return (map)
  [n]
  (let [n (mixed/to-map n)]
       (letfn [(f0 [n k v]
                   (cond (string?  k) (assoc n (-> k keyword name)         v)
                         (keyword? k) (assoc n (-> k         name keyword) v)
                         :return n))]
              (reduce-kv f0 {} n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn assoc-ns
  ; @description
  ; Associates the given key-value pair to given 'n' map adding the namespace of the map to the new key.
  ;
  ; @param (map) n
  ; @param (keyword) k
  ; @param (*) v
  ;
  ; @usage
  ; (assoc-ns {:fruit/apple "red"} :banana "yellow")
  ; =>
  ; {:fruit/apple "red" :fruit/banana "yellow"}
  ;
  ; @return (map)
  [n k v]
  (let [n (mixed/to-map n)
        k (mixed/to-keyword k)]
       (if-let [namespace (namespace n)]
               (let [k (keyword (name namespace) (name k))] (assoc n k v))
               (let [k (-> k)]                              (assoc n k v)))))

(defn get-ns
  ; @description
  ; Returns a specific value from the given 'n' map using the given key as it had the same namespace as the map.
  ;
  ; @param (map) n
  ; @param (keyword) k
  ;
  ; @usage
  ; (get-ns {:fruit/apple "red"} :apple)
  ; =>
  ; "red"
  ;
  ; @return (*)
  [n k]
  (let [n (mixed/to-map n)
        k (mixed/to-keyword k)]
       (if-let [namespace (namespace n)]
               (let [k (keyword (name namespace) (name k))] (get n k))
               (let [k (-> k)]                              (get n k)))))
