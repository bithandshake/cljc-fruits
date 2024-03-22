
(ns fruits.map.collect
    (:require [fruits.map.remove :as remove]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn collect-values
  ; @description
  ; Returns the corresponding values of the given keys collected from the given maps.
  ;
  ; @param (maps in vector) abc
  ; @param (vector) ks
  ; @param (function)(opt) test-f
  ; Default: some?
  ;
  ; @usage
  ; (collect-values [{:a "A1"} {:a "A2"} {:a :A3}] [:a] string?)
  ; =>
  ; ["A1" "A2"]
  ;
  ; @return (map)
  ([abc ks]
   (collect-values abc ks some?))

  ([abc ks test-f]
   (letfn [(f0 [result k] (-> result (update k distinct)
                                     (update k vec)))
           (f1 [result n] (merge-with conj result (remove/keep-values-by (select-keys n ks) test-f)))]
          (as-> (zipmap ks (repeat [])) %
                (reduce f1 % abc)
                (reduce f0 % ks)))))
