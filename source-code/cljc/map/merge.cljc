
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
         (if (some   identity xyz)
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
