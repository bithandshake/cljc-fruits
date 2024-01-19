
(ns fruits.map.assoc
    (:require [fruits.seqable.api :as seqable]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn assoc-by
  ; @description
  ; Associates the given 'xyz' values to the given 'n' map at the given 'path' dynamic path.
  ;
  ; @param (map) n
  ; @param (vector) path
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (letfn [(last-dex [%] (-> % count dec))]
  ;        (assoc-by {:a [{:b "B"} {:c "C"}]} [:a last-dex] :x "X"))
  ; =>
  ; {:a [{:b "B"} {:c "C" :x "X"}]}
  ;
  ; @usage
  ; (assoc-by {:a [{:b "B"} {:c "C"}]} [:a #(-> % count dec)] :x "X")
  ; =>
  ; {:a [{:b "B"} {:c "C" :x "X"}]}
  ;
  ; @return (map)
  [n path & xyz]
  (let [n (mixed/to-map n)]
       (apply assoc-in n (seqable/dynamic-path n path) xyz)))
