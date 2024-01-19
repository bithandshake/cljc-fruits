
(ns fruits.map.toggle
    (:require [fruits.mixed.api :as mixed]
              [fruits.map.dissoc :as dissoc]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn toggle
  ; @description
  ; Associates a key-value pair to the given 'n' map if the key is not present,
  ; or removes it if the key is already present.
  ;
  ; @param (map) n
  ; @param (*) k
  ; @param (*) v
  ;
  ; @usage
  ; (toggle {} :a "A")
  ; =>
  ; {:a "A"}
  ;
  ; @usage
  ; (toggle {:a "A"} :a "A")
  ; =>
  ; {}
  ;
  ; @usage
  ; (toggle {:a "B"} :a "A")
  ; =>
  ; {}
  ;
  ; @return (*)
  [n k v]
  (let [n (mixed/to-map n)]
       (if-let [_ (get n k)]
               (dissoc n k)
               (assoc  n k v))))

(defn toggle-in
  ; @description
  ; Associates a nested key-value pair to the given 'n' map if the key is not present,
  ; or removes it if the key is already present.
  ;
  ; @param (map) n
  ; @param (vector) path
  ; @param (*) v
  ;
  ; @usage
  ; (toggle-in {} [:a :b] "B")
  ; =>
  ; {:a {:b "B"}}
  ;
  ; @usage
  ; (toggle-in {:a "A"} [:a :b] "B")
  ; =>
  ; {:a {:b "B"}}
  ;
  ; @usage
  ; (toggle-in {:a {:b "B"}} [:a :B] "B")
  ; =>
  ; {}
  ;
  ; @return (*)
  [n path v]
  (let [n    (mixed/to-map n)
        path (mixed/to-vector path)]
       (if (get-in           n path)
           (dissoc/dissoc-in n path)
           (assoc-in         n path v))))
