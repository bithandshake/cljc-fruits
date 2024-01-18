
(ns fruits.vector.remove
    (:require [fruits.seqable.api    :as seqable]
              [fruits.vector.contain :as contain]
              [fruits.vector.dex     :as dex]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-item
  ; @param (vector) n
  ; @param (*) dex
  ;
  ; @usage
  ; (remove-item [:a :b] :b)
  ; =>
  ; [:a]
  ;
  ; @usage
  ; (remove-item [:a :b :a] :a)
  ; =>
  ; [:b]
  ;
  ; @return (vector)
  [n x]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [%] (= % x))]
              (vec (remove f0 n)))))

(defn remove-item-once
  ; @param (vector) n
  ; @param (*) dex
  ;
  ; @usage
  ; (remove-item-once [:a :b :b] :b)
  ; =>
  ; [:a :b]
  ;
  ; @usage
  ; (remove-item-once [:a :b :a] :a)
  ; =>
  ; [:b :a]
  ;
  ; @return (vector)
  [n x]
  (let [n (mixed/to-vector n)]
       (loop [dex 0]
             (cond (seqable/dex-out-of-bounds? n dex) (-> n)
                   (not= x (nth n dex))               (-> dex inc recur)
                   :return (vec (concat (subvec n 0 dex)
                                        (if-not (seqable/dex-last? n dex)
                                                (subvec n (inc dex)))))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-first-item
  ; @description
  ; Removes the first item of the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (remove-first-item [:a :b :c :d :e])
  ; =>
  ; [:b :c :d :e]
  ;
  ; @return (vector)
  [n]
  (let [n (mixed/to-vector n)]
       (vec (drop 1 n))))

(defn remove-first-items
  ; @description
  ; Removes a specific amount of items from the beginning of the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (integer) cut
  ;
  ; @usage
  ; (remove-first-items [:a :b :c :d :e] 2)
  ; =>
  ; [:c :d :e]
  ;
  ; @return (vector)
  [n cut]
  (let [n   (mixed/to-vector n)
        cut (mixed/to-integer cut)]
       (cond (-> n count (<= cut)) (-> [])
             :return (into [] (subvec n cut)))))

(defn remove-last-item
  ; @description
  ; Removes the last item of the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (remove-last-item [:a :b :c :d :e])
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  [n]
  (let [n (mixed/to-vector n)]
       (-> n drop-last vec)))

(defn remove-last-items
  ; @description
  ; Removes a specific amount of items from the end of the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (integer) cut
  ;
  ; @usage
  ; (remove-last-items [:a :b :c :d :e] 2)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n cut]
  (let [n   (mixed/to-vector n)
        cut (mixed/to-integer cut)]
       (cond (-> n count (<= cut)) (-> [])
             :return (into [] (subvec n 0 (-> n count (- cut)))))))

(defn remove-nth-item
  ; @description
  ; Removes the nth item from the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (integer) th
  ;
  ; @usage
  ; (remove-nth-item [:a :b :c :d :e] 2)
  ; =>
  ; [:a :b :d :e]
  ;
  ; @return (vector)
  [n th]
  (let [n (mixed/to-vector n)]
       (if-let [th (seqable/normalize-dex n th {:adjust? false :mirror? true})]
               (vec (concat (subvec n 0 th)
                            (subvec n (inc th))))
               (-> n))))

(defn remove-nth-items
  ; @description
  ; Removes the nth items from the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (integers in vector) ths
  ;
  ; @usage
  ; (remove-nth-items [:a :b :c :d :e] [0 2])
  ; =>
  ; [:b :d :e]
  ;
  ; @return (vector)
  [n ths]
  (let [n   (mixed/to-vector n)
        ths (mixed/to-vector ths)
        ths (map mixed/to-integer ths)]
       (letfn [(f0 [result dex x]
                   (if (contain/contains-item? ths dex)
                       (->   result)
                       (conj result x)))]
              (reduce-kv f0 [] n))))

(defn remove-items
  ; @description
  ; Removes items from the given 'n' vector that are present in the given 'xyz' vector.
  ;
  ; @param (vector) n
  ; @param (vector) xyz
  ;
  ; @usage
  ; (remove-items [:a :b :c] [:b :c])
  ; =>
  ; [:a]
  ;
  ; @usage
  ; (remove-items [:a :b :b :c ] [:b :c])
  ; =>
  ; [:a]
  ;
  ; @return (vector)
  [n xyz]
  (let [n   (mixed/to-vector n)
        xyz (mixed/to-vector xyz)]
       (vec (remove (set xyz) n))))

(defn remove-items-by
  ; @description
  ; Removes every item from the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (remove-items-by [:a :b :c] keyword?)
  ; =>
  ; []
  ;
  ; @return (vector)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (letfn [(f0 [result x]
                   (if (-> x f)
                       (-> result)
                       (-> result (conj x))))]
              (reduce f0 [] n))))

(defn remove-duplicates
  ; @description
  ; Removes every duplicates from the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (remove-duplicates [:a :b :c :a])
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [result x]
                   (if (-> result (contain/contains-item? x))
                       (-> result)
                       (-> result (conj x))))]
              (reduce f0 [] n))))

(defn remove-first-occurence
  ; @description
  ; Removes the first occurence of the given 'x' item from the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (remove-first-occurence [:a :b :c :d :d :e] :d)
  ; =>
  ; [:a :b :c :d :e]
  ;
  ; @return (vector)
  [n x]
  ; @bug (#1130)
  ; The ShadowCLJS protects the 'inc' function from receiving a NIL value as parameter.
  ; The 'first-dex-of' function returns through a 'when' condition.
  ; Therefore, without using the '(if (number? item-dex) ...)' condition, the ShadowCLJS
  ; would throw the following error message:
  ; "cljs.core/+, all arguments must be numbers, got [#{nil clj-nil} number] instead"
  (let [n (mixed/to-vector n)]
       (if-let [item-dex (dex/first-dex-of n x)]
               (if (number? item-dex)
                   (vec (concat (subvec n 0 item-dex)
                                (subvec n (inc item-dex)))))
               (-> n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn keep-items
  ; @description
  ; Keeps only those items in the given 'n' vector that are present in the given 'xyz' vector.
  ;
  ; @param (vector) n
  ; @param (vector) xyz
  ;
  ; @usage
  ; (keep-items [:a :b :c :d] [:b :c])
  ; =>
  ; [:b :c]
  ;
  ; @return (vector)
  [n xyz]
  (let [n   (mixed/to-vector n)
        xyz (mixed/to-vector xyz)]
       (letfn [(f0 [result x]
                   (if (-> xyz    (contain/contains-item? x))
                       (-> result (conj                   x))
                       (-> result)))]
              (reduce f0 [] n))))

(defn keep-items-by
  ; @description
  ; Keeps only those items in the given 'n' vector that match the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (keep-items-by [:a :b "c" "d"] keyword?)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (letfn [(f0 [result x]
                   (if (-> x f)
                       (-> result (conj x))
                       (-> result)))]
              (reduce f0 [] n))))
