
(ns fruits.map.merge
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn deep-merge
  ; @description
  ; Merges the given nested maps.
  ;
  ; @param (map) n
  ; @param (list of maps) xyz
  ;
  ; @usage
  ; (deep-merge {:a {:b "a/b"}} {:a {:c "a/c"}})
  ; =>
  ; {:a {:b "a/b" :c "a/c"}}
  ;
  ; @return (*)
  [n & xyz]
  (let [n   (mixed/to-map n)
        xyz (map mixed/to-map xyz)]
       (letfn [(f0 [result x]
                   (if (and (map? result)
                            (map? x))
                       (merge-with f0 result x)
                       (-> x)))]
              (if (some identity xyz)
                  (reduce f0 n xyz)
                  (-> n)))))

(defn reversed-merge
  ; @description
  ; Merges the given maps in a reversed order.
  ;
  ; @param (list of maps) xyz
  ;
  ; @usage
  ; (reversed-merge {:a "A"} {:a "B"})
  ; =>
  ; {:a "A"}
  ;
  ; @usage
  ; (reversed-merge {:a "A"} {:a "B"} {:a "C"})
  ; =>
  ; {:a "A"}
  ;
  ; @return (map)
  [& xyz]
  (let [xyz (map mixed/to-map xyz)]
       (apply merge (reverse xyz))))

(defn merge-some
  ; @description
  ; Merges values of the given maps that are not NIL.
  ;
  ; @param (list of maps) xyz
  ;
  ; @usage
  ; (merge-some {:a "A"} {:a nil})
  ; =>
  ; {:a "A"}
  ;
  ; @usage
  ; (merge-some {:a "A"} {:a nil} {:a "C"})
  ; =>
  ; {:a "C"}
  ;
  ; @return (map)
  [& xyz]
  (let [xyz (map mixed/to-map xyz)]
       (letfn [(f0 [result x]   (reduce-kv f1 result x))
               (f1 [result k v] (if (-> v some?)
                                    (-> result (assoc k v))
                                    (-> result)))]
              (reduce f0 {} xyz))))
