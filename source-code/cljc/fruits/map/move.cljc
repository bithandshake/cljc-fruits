
(ns fruits.map.move
    (:require [fruits.map.dissoc :as dissoc]
              [fruits.mixed.api  :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn move
  ; @description
  ; Moves a specific value or values within the given 'n' map.
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

(defn move-some
  ; @description
  ; Moves a specific value or values within the given 'n' map, in case they are not NIL.
  ;
  ; @param (map) n
  ; @param (list of *) ks
  ;
  ; @usage
  ; (move-some {:a "A" :b nil} :a :x)
  ; =>
  ; {:x "A" :b nil}
  ;
  ; @usage
  ; (move-some {:a "A" :b nil} :a :x :b :y)
  ; =>
  ; {:x "A" :b nil}
  ;
  ; @usage
  ; (move-some {:a "A" :b nil} :a :x :x :y)
  ; =>
  ; {:y "A" :b nil}
  ;
  ; @return (map)
  [n & ks]
  (let [n (mixed/to-map n)]
       (loop [n n ks (vec ks)]
             (if (-> ks count (< 2))
                 (-> n)
                 (recur (if-some [v (get n (first ks))]
                                 (-> n (assoc (second ks) v) (dissoc (first ks)))
                                 (-> n))
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
  ; (move-in {:a {:b "B" :c "C"}} [:a :b] [:x :y])
  ; =>
  ; {:x {:y "B" :c "C"}}
  ;
  ; @return (map)
  [n from to]
  (let [n    (mixed/to-map n)
        from (mixed/to-vector from)
        to   (mixed/to-vector to)]
       (-> n (dissoc/dissoc-in from)
             (assoc-in to (get-in n from)))))

(defn move-in-some
  ; @description
  ; Moves a specific nested value within the given 'n' map, in case it is not NIL.
  ;
  ; @param (map) n
  ; @param (vector) from
  ; @param (vector) to
  ;
  ; @usage
  ; (move-in-some {:a {:b "B" :c nil}} [:a :b] [:x :y])
  ; =>
  ; {:a {:c nil}
  ;  :x {:y "B"}}
  ;
  ; @usage
  ; (move-in-some {:a {:b "B" :c nil}} [:a :c] [:x :y])
  ; =>
  ; {:a {:b "B" :c nil}}
  ;
  ; @return (map)
  [n from to]
  (let [n    (mixed/to-map n)
        from (mixed/to-vector from)
        to   (mixed/to-vector to)]
       (if-some [v (get-in n from)]
                (-> n (dissoc/dissoc-in from) (assoc-in to v))
                (-> n))))
