
(ns fruits.vector.duplicate
    (:require [fruits.mixed.api      :as mixed]
              [fruits.seqable.api    :as seqable]
              [fruits.vector.contain :as contain]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn duplicate-first-item
  ; @description
  ; Duplicates the first item in the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (duplicate-first-item [:a :b :c :d :e])
  ; =>
  ; [:a :a :b :c :d :e]
  ;
  ; @return (vector)
  [n]
  (let [n (mixed/to-vector n)]
       (if (-> n count (>= 1))
           (-> n first (cons n) vec)
           (-> n))))

(defn duplicate-second-item
  ; @description
  ; Duplicates the second item in the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (duplicate-second-item [:a :b :c :d :e])
  ; =>
  ; [:a :b :b :c :d :e]
  ;
  ; @return (vector)
  [n]
  (let [n (mixed/to-vector n)]
       (if (-> n count (>= 2))
           (vec (concat (subvec n 0 1)
                        [(second n)]
                        (subvec n 1)))
           (-> n))))

(defn duplicate-last-item
  ; @description
  ; Duplicates the last item in the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (duplicate-last-item [:a :b :c :d :e])
  ; =>
  ; [:a :b :c :d :e :e]
  ;
  ; @return (vector)
  [n]
  (let [n (mixed/to-vector n)]
       (if (->  n count (>= 1))
           (->> n last (conj n) vec)
           (->  n))))

(defn duplicate-nth-item
  ; @description
  ; Duplicates the nth item in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (integer) th
  ;
  ; @usage
  ; (duplicate-nth-item [:a :b :c :d :e] 2)
  ; =>
  ; [:a :b :c :c :d :e]
  ;
  ; @return (vector)
  [n th]
  (let [n (mixed/to-vector n)]
       (if-let [th (seqable/normalize-dex n th {:adjust? false :mirror? true})]
               (vec (concat (subvec n 0 th)
                            [(nth n th)]
                            (subvec n th)))
               (-> n))))

(defn duplicate-nth-items
  ; @important
  ; This function is incomplete and may not behave as expected.
  ;
  ; @description
  ; Duplicates the nth items in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (integers in vector) ths
  ;
  ; @usage
  ; (duplicate-nth-items [:a :b :c :d :e] [0 2])
  ; =>
  ; [:a :a :b :c :c :d :e]
  ;
  ; @return (vector)
  [n ths])
  ; TODO
