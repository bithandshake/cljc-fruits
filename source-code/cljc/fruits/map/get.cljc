
(ns fruits.map.get
    (:require [fruits.mixed.api   :as mixed]
              [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-by
  ; @description
  ; Returns a specific value from the given 'n' map at the given 'path' dynamic path.
  ;
  ; @param (map) n
  ; @param (vector) path
  ; @param (*)(opt) default-value
  ;
  ; @usage
  ; (defn last-dex [%] (-> % count dec))
  ; (get-by {:a [{:b "B"} {:c "C"}]} [:a last-dex])
  ; =>
  ; {:c "C"}
  ;
  ; @usage
  ; (get-by {:a [{:b "B"} {:c "C"}]} [:a #(-> % count dec)])
  ; =>
  ; {:c "C"}
  ;
  ; @return (*)
  [n path & [default-value]]
  (let [n    (mixed/to-map n)
        path (seqable/dynamic-path n path)]
       (get-in n path default-value)))
