
(ns vector.move
    (:require [math.api      :as math]
              [vector.dex    :as dex]
              [vector.range  :as range]
              [vector.remove :as remove]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn move-nth-item
  ; @param (vector) n
  ; @param (integer) from
  ; @param (integer) to
  ;
  ; @usage
  ; (move-nth-item [:a :b :c] 0 2)
  ;
  ; @example
  ; (move-nth-item [:a :b :c :d :e :f :g :h] 2 2)
  ; =>
  ; [:a :b :c :d :e :f :g :h]
  ;
  ; @example
  ; (move-nth-item [:a :b :c :d :e :f :g :h] 2 5)
  ; =>
  ; [:a :b :d :e :f :c :g :h]
  ;
  ; @example
  ; (move-nth-item [:a :b :c :d :e :f :g :h] 5 2)
  ; =>
  ; [:a :b :f :c :d :e :g :h]
  ;
  ; @return (vector)
  [n from to]
  (if (dex/dex-in-bounds? n from)
      (let [to (math/between! to 0 (count n))]
           (cond
                 ; Stay in place
                 (= from to) (-> n)

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
      (-> n)))

(defn move-nth-item-bwd
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (move-nth-item-bwd [:a :b :c] 1)
  ;
  ; @example
  ; (move-nth-item-bwd [:a :b :c :d] 2)
  ; =>
  ; [:a :c :b :d]
  ;
  ; @example
  ; (move-nth-item-bwd [:a :b :c :d] 0)
  ; =>
  ; [:b :c :d :a]
  ;
  ; @return (vector)
  [n dex]
  (move-nth-item n dex (dex/prev-dex n dex)))

(defn move-nth-item-fwd
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (move-nth-item-fwd [:a :b :c] 1)
  ;
  ; @example
  ; (move-nth-item-fwd [:a :b :c :d] 2)
  ; =>
  ; [:a :b :d :c]
  ;
  ; @example
  ; (move-nth-item-fwd [:a :b :c :d] 3)
  ; =>
  ; [:d :a :b :c]
  ;
  ; @return (vector)
  [n dex]
  (move-nth-item n dex (dex/next-dex n dex)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

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
          (move-nth-item n item-first-dex dex)
          (-> n)))
