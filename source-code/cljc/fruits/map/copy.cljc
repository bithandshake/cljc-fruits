
(ns fruits.map.copy
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn copy
  ; @description
  ; Duplicates a specific value / specific values within the given 'n' map.
  ;
  ; @param (map) n
  ; @param (list of *) ks
  ;
  ; @usage
  ; (copy {:a "A" :b "B"} :a :x)
  ; =>
  ; {:a "A" :b "B" :x "A"}
  ;
  ; @usage
  ; (copy {:a "A" :b "B"} :a :x :b :y)
  ; =>
  ; {:a "A" :b "B" :x "A" :y "B"}
  ;
  ; @usage
  ; (copy {:a "A" :b "B"} :a :x :x :y)
  ; =>
  ; {:a "A" :b "B" :x "A" :y "A"}
  ;
  ; @return (map)
  [n & ks]
  (let [n (mixed/to-map n)]
       (loop [n n ks (vec ks)]
             (if (-> ks count (< 2))
                 (-> n)
                 (recur (-> n  (assoc (second ks) (get n (first ks))))
                        (-> ks (subvec 2)))))))

(defn copy-in
  ; @description
  ; Duplicates a specific nested value within the given 'n' map.
  ;
  ; @param (map) n
  ; @param (vector) from
  ; @param (vector) to
  ;
  ; @usage
  ; (copy-in {:a {:b "B"}} [:a :b] [:x :y])
  ; =>
  ; {:a {:b "B"} :x {:y "B"}}
  ;
  ; @return (map)
  [n from to]
  (let [n    (mixed/to-map n)
        from (mixed/to-vector from)
        to   (mixed/to-vector to)]
       (assoc-in n to (get-in n from))))
