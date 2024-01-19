
(ns fruits.map.move
    (:require [fruits.map.dissoc :as dissoc]
              [fruits.mixed.api  :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn move
  ; @description
  ; Moves a specific value / specific values within the given 'n' map.
  ;
  ; @param (map) n
  ; @param (list of *) ks
  ;
  ; @usage
  ; (move {:a "A" :b "B"} :a :x)
  ; =>
  ; {:x "A" :b "B"}
  ;
  ; @usage
  ; (move {:a "A" :b "B"} :a :x :b :y)
  ; =>
  ; {:x "A" :y "B"}
  ;
  ; @usage
  ; (move {:a "A" :b "B"} :a :x :x :y)
  ; =>
  ; {:y "A" :b "B"}
  ;
  ; @return (map)
  [n & ks]
  (let [n (mixed/to-map n)]
       (loop [n n ks (vec ks)]
             (if (-> ks count (< 2))
                 (-> n)
                 (recur (-> n  (assoc (second ks) (get n (first ks))) (dissoc (first ks)))
                        (-> ks (subvec 2)))))))

(defn move-in
  ; @description
  ; Moves a specific nested value within the given 'n' map.
  ;
  ; @param (map) n
  ; @param (vector) from
  ; @param (vector) to
  ;
  ; @usage
  ; (move-in {:a {:b "B"}} [:a :b] [:x :y])
  ; =>
  ; {:x {:y "B"}}
  ;
  ; @return (map)
  [n from to]
  (let [n    (mixed/to-map n)
        from (mixed/to-vector from)
        to   (mixed/to-vector to)]
       (-> n (dissoc/dissoc-in from)
             (assoc-in to (get-in n from)))))
