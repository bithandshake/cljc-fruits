
(ns map.merge
    (:require [noop.api :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn deep-merge
  ; @param (map) n
  ; @param (list of maps) xyz
  ;
  ; @usage
  ; (deep-merge {:a {:b "a/b"}} {:c {:d "c/d"}})
  ;
  ; @usage
  ; (deep-merge {:a {:b "a/b"}} {:c {:d "c/d"}})
  ;
  ; @return (*)
  [n & xyz]
  (letfn [(f [result x]
             (if (and (map? result)
                      (map? x))
                 (merge-with f result x)
                 (return x)))]
         (if (some identity xyz)
             (reduce f n xyz)
             (return n))))

(defn reversed-merge
  ; @param (list of maps) xyz
  ;
  ; @usage
  ; (reversed-merge {:a "A"} {:a "B"})
  ;
  ; @example
  ; (reversed-merge {:a "A"} {:a "B"})
  ; =>
  ; {:a "A"}
  ;
  ; @example
  ; (reversed-merge {:a "A"} {:a "B"} {:a "C"})
  ; =>
  ; {:a "A"}
  ;
  ; @return (map)
  [& xyz]
  (apply merge (reverse xyz)))

(defn merge-some
  ; @param (list of maps) xyz
  ;
  ; @usage
  ; (merge-some {:a "A"} {:a nil})
  ;
  ; @example
  ; (merge-some {:a "A"} {:a nil})
  ; =>
  ; {:a "A"}
  ;
  ; @example
  ; (merge-some {:a "A"} {:a nil} {:a "C"})
  ; =>
  ; {:a "C"}
  ;
  ; @return (map)
  [& xyz]
  (letfn [(f0 [result x]   (reduce-kv f1 result x))
          (f1 [result k v] (if (some? v)
                               (assoc  result k v)
                               (return result)))]
         (reduce f0 {} xyz)))
