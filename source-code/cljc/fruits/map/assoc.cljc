
(ns fruits.map.assoc
    (:require [fruits.map.convert :as convert]
              [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn assoc-by
  ; @description
  ; Associates the given 'xyz' values to the given 'n' map at the given 'path' dynamic path.
  ;
  ; @param (map) n
  ; @param (vector) path
  ; @param (*) v
  ;
  ; @usage
  ; (defn last-dex [%] (-> % count dec))
  ; (assoc-by {:a [{:b "B"} {:c "C"}]} [:a last-dex] :x "X")
  ; =>
  ; {:a [{:b "B"} {:c "C" :x "X"}]}
  ;
  ; @usage
  ; (assoc-by {:a [{:b "B"} {:c "C"}]} [:a #(-> % count dec)] :x "X")
  ; =>
  ; {:a [{:b "B"} {:c "C" :x "X"}]}
  ;
  ; @return (map)
  [n path v]
  (let [n    (convert/to-associative n)
        path (seqable/dynamic-path n path)]
       (assoc-in n path v)))

(defn assoc-some
  ; @description
  ; Associates the given value(s) to the given 'n' map, in case the value is not NIL.
  ;
  ; @param (map) n
  ; @param (list of key-value pairs) kvs
  ;
  ; @usage
  ; (assoc-some {:a "A"} :b nil :c "C")
  ; =>
  ; {:a "A" :c "C"}
  ;
  ; @return (map)
  [n & kvs]
  (let [n (convert/to-associative n)]
       (loop [result n kvs kvs]
             (if (-> kvs count (< 2))
                 (-> result)
                 (let [k   (first  kvs)
                       v   (second kvs)
                       kvs (drop 2 kvs)]
                      (if (-> v some?)
                          (-> result (assoc k v) (recur kvs))
                          (-> result             (recur kvs))))))))

(defn assoc-in-some
  ; @description
  ; Associates the given value(s) to the given 'n' map nested, in case the value is not NIL.
  ;
  ; @param (map) n
  ; @param (list of path-value pairs) pvs
  ;
  ; @usage
  ; (assoc-in-some {:a "A"} [:b] nil [:c] "C")
  ; =>
  ; {:a "A" :c "C"}
  ;
  ; @return (map)
  [n & pvs]
  (let [n (convert/to-associative n)]
       (loop [result n pvs pvs]
             (if (-> pvs count (< 2))
                 (-> result)
                 (let [p   (first  pvs)
                       v   (second pvs)
                       pvs (drop 2 pvs)]
                      (if (-> v some?)
                          (-> result (assoc-in p v) (recur pvs))
                          (-> result                (recur pvs))))))))
