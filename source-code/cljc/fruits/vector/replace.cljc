
(ns fruits.vector.replace
    (:require [fruits.seqable.api :as seqable]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn replace-item
  ; @description
  ; Replaces items (that are indentical to the given 'a' value) in the given 'n' vector with the given 'b' value.
  ;
  ; @param (vector) n
  ; @param (*) a
  ; @param (*) b
  ;
  ; @usage
  ; (replace-item [:a :b :c :d :c] :c :x)
  ; =>
  ; [:a :b :x :d :x]
  ;
  ; @usage
  ; (replace-item [:a :b :b :b] :b :x)
  ; =>
  ; [:a :x :x :x]
  ;
  ; @return (vector)
  [n a b]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [result x]
                   (if (= x a)
                       (conj result b)
                       (conj result x)))]
              (reduce f0 [] n))))

(defn replace-first-item
  ; @description
  ; Replaces the first item in the given 'n' vector with the given 'x' value.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (replace-first-item [:a :b :c] :x)
  ; =>
  ; [:x :b :c]
  ;
  ; @usage
  ; (replace-first-item [] :x)
  ; =>
  ; []
  ;
  ; @return (vector)
  [n x]
  (let [n (mixed/to-vector n)]
       (cond (-> n count (< 1)) (-> n)
             (-> n count (= 1)) (-> [x])
             (-> n count (> 1)) (vec (cons x (subvec n 1))))))

(defn replace-second-item
  ; @description
  ; Replaces the second item in the given 'n' vector with the given 'x' value.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (replace-second-item [:a :b :c] :x)
  ; =>
  ; [:a :x :c]
  ;
  ; @usage
  ; (replace-second-item [:a] :x)
  ; =>
  ; [:a]
  ;
  ; @return (vector)
  [n x]
  (let [n (mixed/to-vector n)]
       (cond (-> n count (< 2)) (-> n)
             (-> n count (= 2)) (-> [(first n) x])
             (-> n count (> 2)) (vec (concat [(first n) x] (subvec n 2))))))

(defn replace-last-item
  ; @description
  ; Replaces the last item in the given 'n' vector with the given 'x' value.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (replace-last-item [:a :b :c] :x)
  ; =>
  ; [:a :b :x]
  ;
  ; @usage
  ; (replace-last-item [] :x)
  ; =>
  ; []
  ;
  ; @return (vector)
  [n x]
  (let [n (mixed/to-vector n)]
       (cond (-> n count (< 1)) (-> n)
             (-> n count (= 1)) (-> [x])
             (-> n count (> 1)) (conj (subvec n 0 (-> n count dec)) x))))

(defn replace-nth-item
  ; @description
  ; Replaces the nth item in the given 'n' vector with the given 'x' value.
  ;
  ; @param (vector) n
  ; @param (integer) th
  ; @param (*) x
  ;
  ; @usage
  ; (replace-nth-item [:a :b :c] 1 :x)
  ; =>
  ; [:a :x :c]
  ;
  ; @usage
  ; (replace-nth-item [:a :b :c :d] 999 :x)
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  [n th x]
  (let [n (mixed/to-vector n)]
       (if-let [th (seqable/normalize-dex n th {:adjust? false :mirror? true})]
               (vec (concat (subvec n 0 th)
                            [x]
                            (subvec n (inc th))))
               (-> n))))
