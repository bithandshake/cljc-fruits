
(ns fruits.map.dissoc
    (:require [fruits.mixed.api   :as mixed]
              [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn dissoc-in
  ; @note
  ; https://github.com/day8/re-frame/blob/master/src/re_frame/utils.cljc
  ;
  ; @description
  ; Dissociates a specific value from the given 'n' map.
  ;
  ; @param (map) n
  ; @param (vector) path
  ;
  ; @usage
  ; (dissoc-in {:a {:b "B"}} [:a :b])
  ; =>
  ; {}
  ;
  ; @return (map)
  [n [k & ks :as path]]
  (let [n (mixed/to-map n)]
       (if ks (if-let [next-n (get n k)]
                      (let [new-n (dissoc-in next-n ks)]
                           (if (seq new-n)
                               (assoc n k new-n)
                               (dissoc n k)))
                      (-> n))
              (dissoc n k))))

(defn dissoc-by
  ; @description
  ; Dissociates a specific value from the given 'n' map at the given 'path' dynamic path.
  ;
  ; @param (map) n
  ; @param (vector) path
  ;
  ; @usage
  ; (letfn [(last-dex [%] (-> % count dec))]
  ;        (dissoc-by {:a [{:b "B"} {:c "C"}]} [:a last-dex]))
  ; =>
  ; {:a [{:b "B"}]}
  ;
  ; @usage
  ; (dissoc-by {:a [{:b "B"} {:c "C"}]} [:a #(-> % count dec))])
  ; =>
  ; {:a [{:b "B"}]}
  ;
  ; @return (map)
  [n path]
  (let [n    (mixed/to-map n)
        path (seqable/dynamic-path n path)]
       (dissoc-in n path)))
