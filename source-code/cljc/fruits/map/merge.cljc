
(ns fruits.map.merge
    (:require [fruits.map.remove :as remove]
              [fruits.mixed.api  :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn reversed-merge
  ; @description
  ; Merges the given maps in a reversed order.
  ;
  ; @param (list of maps) abc
  ;
  ; @usage
  ; (reversed-merge {:a "A1"} {:a "A2"} {:a "A3"})
  ; =>
  ; {:a "A1"}
  ;
  ; @return (map)
  [& abc]
  (let [abc (map mixed/to-map abc)]
       (apply merge (reverse abc))))

(defn merge-some
  ; @description
  ; Merges the given maps and filters the result for non-NIL values.
  ;
  ; @param (list of maps) abc
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
  [& abc]
  (let [abc (map mixed/to-map abc)]
       (letfn [(f0 [result x]   (reduce-kv f1 result x))
               (f1 [result k v] (if (-> v some?)
                                    (-> result (assoc k v))
                                    (-> result)))]
              (reduce f0 {} abc))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn deep-merge
  ; @description
  ; Deep merges the given nested maps.
  ;
  ; @param (list of maps) abc
  ;
  ; @usage
  ; (deep-merge {:x {:a "A1"}} {:x {:b "B2"}})
  ; =>
  ; {:x {:a "A1" :b "B2"}}
  ;
  ; @return (*)
  [& abc]
  (let [abc (map mixed/to-map abc)]
       (letfn [(f0 [result x]
                   (if (->> x map?)
                       (->> x (merge-with f0 result))
                       (->> x)))]
              (reduce f0 {} abc))))

(defn deep-merge-some
  ; @description
  ; Deep merges the given nested maps and filters the result for non-NIL values.
  ;
  ; @param (list of maps) abc
  ;
  ; @usage
  ; (deep-merge-some {:x {:a "A1" :b nil}} {:x {:c "C2" :d NIL}})
  ; =>
  ; {:x {:a "A1" :b nil :c "C2"}}
  ;
  ; @return (*)
  [& abc]
  (let [abc (map mixed/to-map abc)]
       (letfn [(f0 [result x] (reduce-kv f1 result x))
               (f1 [result k v]
                   (cond (-> v nil?)              (-> result)             ; <- The primary value is NIL.
                         (-> v map? not)          (-> result (assoc k v)) ; <- The primary value is not NIL, but also not map.
                         (-> result (get k) map?) (-> result (assoc k (f0 (-> result (get k)) v))) ; <- Both values are maps.
                         :else                    ; <- Only the primary value is a map.
                         (let [vf (f0 (-> {}) v)] ; <- Reapplying the 'f0' function ereases NIL values from the primary value.
                              (cond (-> vf empty? not) (-> result (assoc k vf)) ; <- The filtered primary value is not empty.
                                    (-> v  empty?)     (-> result (assoc k vf)) ; <- The filtered primary value is empty, but it was also empty before the filtering.
                                    :return result))))]                         ; <- The filtered primary value is empty because of the filtering.
              (reduce f0 {} abc))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn reversed-deep-merge
  ; @description
  ; Deep merges the given nested maps in a reversed order.
  ;
  ; @param (list of maps) abc
  ;
  ; (reversed-deep-merge {:x {:a "A1"}} {:x {:a "A2" :b "B2"}})
  ; =>
  ; {:x {:a "A1" :b "B2"}}
  ;
  ; @return (map)
  [& abc]
  (let [abc (map mixed/to-map abc)]
       (apply deep-merge (reverse abc))))

(defn reversed-merge-some
  ; @description
  ; Merges non-NIL values in the given maps in a reversed order.
  ;
  ; @param (list of maps) abc
  ;
  ; @usage
  ; (reversed-merge-some {:a nil} {:a "A1"} {:a "A2"})
  ; =>
  ; {:a "A1"}
  ;
  ; @return (map)
  [& abc]
  (let [abc (map mixed/to-map abc)]
       (apply merge-some (reverse abc))))
