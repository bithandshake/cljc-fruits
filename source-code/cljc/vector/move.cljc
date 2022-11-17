
(ns vector.move
    (:require [candy.api     :refer [return]]
              [math.api      :as math]
              [vector.dex    :as dex]
              [vector.range  :as range]
              [vector.remove :as remove]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn move-item
  ; @param (vector) n
  ; @param (integer) from
  ; @param (integer) to
  ;
  ; @usage
  ; (move-item [:a :b :c] 0 2)
  ;
  ; @example
  ; (move-item [:a :b :c :d :e :f :g :h] 2 2)
  ; =>
  ; [:a :b :c :d :e :f :g :h]
  ;
  ; @example
  ; (move-item [:a :b :c :d :e :f :g :h] 2 5)
  ; =>
  ; [:a :b :d :e :c :f :g :h]
  ;
  ; @example
  ; (move-item [:a :b :c :d :e :f :g :h] 5 2)
  ; =>
  ; [:a :b :f :c :d :e :g :h]
  ;
  ; @return (vector)
  [n from to]
  (if (dex/dex-in-bounds? n from)
      (let [to (math/between! to 0 (count n))]
           (cond ; Stay in place
                 (= from to) (return n)
                 ; Move item fwd
                 (< from to) (vec (concat (range/ranged-items n 0 from)
                                          (range/ranged-items n (inc from) (inc to))
                                          (range/ranged-items n from (inc from))
                                          (range/ranged-items n (inc to))))
                 ; Move item bwd
                 (> from to) (vec (concat (range/ranged-items n 0 to)
                                          (range/ranged-items n from (inc from))
                                          (range/ranged-items n to from)
                                          (range/ranged-items n (inc from))))))
      (return n)))

(defn move-item-to-last
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (move-item-to-last [:a :b :c] :a)
  ;
  ; @example
  ; (move-item-to-last [:a :b] :a)
  ; =>
  ; [:b :a]
  ;
  ; @example
  ; (move-item-to-last [:b] :a)
  ; =>
  ; [:b :a]
  ;
  ; @return (vector)
  [n x]
  (conj (remove/remove-item n x) x))

(defn move-item-to-first
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @example
  ; (move-item-to-first [:a :b :c] :c)
  ;
  ; @example
  ; (move-item-to-first [:a :b] :b)
  ; =>
  ; [:b :a]
  ;
  ; @example
  ; (move-item-to-first [:a] :b)
  ; =>
  ; [:b :a]
  ;
  ; @return (vector)
  [n x]
  (vec (cons x (remove/remove-item n x))))

(defn move-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (integer) dex
  ;
  ; @usage
  ; (move-first-occurence [:a :b :c] :c 0)
  ;
  ; @example
  ; (move-first-occurence [:a :b :c :a :b :c] :b 3)
  ; =>
  ; [:a :c :b :a :b :c]
  ;
  ; @example
  ; (move-first-occurence [:a :b :c :a :b :c] :b 1)
  ; =>
  ; [:a :b :c :a :b :c]
  ;
  ; @example
  ; (move-first-occurence [:a :b :c :a :b :c] :b 20)
  ; =>
  ; [:a :c :a :b :c :b]
  ;
  ; @return (vector)
  [n x dex]
  (if-let [item-first-dex (dex/item-first-dex n x)]
          (move-item n item-first-dex dex)
          (return    n)))
