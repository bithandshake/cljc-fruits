
(ns fruits.vector.remove
    (:require [fruits.seqable.api    :as seqable]
              [fruits.vector.contain :as contain]
              [fruits.vector.dex     :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-first-item
  ; @param (vector) n
  ;
  ; @usage
  ; (remove-first-item [:a :b :c])
  ;
  ; @example
  ; (remove-first-item [:a :b :c :d :e])
  ; =>
  ; [:b :c :d :e]
  ;
  ; @return (vector)
  [n]
  (vec (drop 1 n)))

(defn remove-first-items
  ; @param (vector) n
  ; @param (integer) cut
  ;
  ; @usage
  ; (remove-first-items [:a :b :c] 2)
  ;
  ; @example
  ; (remove-first-items [:a :b :c :d :e] 2)
  ; =>
  ; [:c :d :e]
  ;
  ; @return (vector)
  [n cut]
  (cond (-> cut integer? not) (-> n)
        (>= cut (count n))    (-> [])
        :return (subvec n cut)))

(defn remove-last-item
  ; @param (vector) n
  ;
  ; @usage
  ; (remove-last-item [:a :b :c])
  ;
  ; @example
  ; (remove-last-item [:a :b :c :d :e])
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  [n]
  (-> n drop-last vec))

(defn remove-last-items
  ; @param (vector) n
  ; @param (integer) cut
  ;
  ; @usage
  ; (remove-last-items [:a :b :c] 2)
  ;
  ; @example
  ; (remove-last-items [:a :b :c :d :e] 2)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n cut]
  (cond (-> cut integer? not) (-> n)
        (>= cut (count n))    (-> [])
        :return (subvec n 0 (-> n count (- cut)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-item
  ; @param (vector) n
  ; @param (*) dex
  ;
  ; @usage
  ; (remove-item [:a :b] :b)
  ;
  ; @example
  ; (remove-item [:a :b] :b)
  ; =>
  ; [:a]
  ;
  ; @example
  ; (remove-item [:a :b :a] :a)
  ; =>
  ; [:b]
  ;
  ; @return (vector)
  [n x]
  (letfn [(f0 [%] (= % x))]
         (vec (remove f0 n))))

(defn remove-item-once
  ; @param (vector) n
  ; @param (*) dex
  ;
  ; @usage
  ; (remove-item-once [:a :b :b] :b)
  ;
  ; @example
  ; (remove-item-once [:a :b :b] :b)
  ; =>
  ; [:a :b]
  ;
  ; @example
  ; (remove-item-once [:a :b :a] :a)
  ; =>
  ; [:b :a]
  ;
  ; @return (vector)
  [n x]
  (loop [dex 0]
        (cond (seqable/dex-out-of-bounds? n dex) (-> n)
              (not= x (nth n dex))               (-> dex inc recur)
              :return (vec (concat (subvec n 0 dex)
                                   (if-not (seqable/dex-last? n dex)
                                           (subvec n (inc dex))))))))

(defn remove-items
  ; @param (vector) n
  ; @param (vector) xyz
  ;
  ; @usage
  ; (remove-items [:a :b :c] [:b :c])
  ;
  ; @example
  ; (remove-items [:a :b :c] [:b :c])
  ; =>
  ; [:a]
  ;
  ; @example
  ; (remove-items [:a :b :b :c ] [:b :c])
  ; =>
  ; [:a]
  ;
  ; @return (vector)
  [n xyz]
  (vec (remove (set xyz) n)))

(defn remove-items-by
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (remove-items-by [:a :b :c] keyword?)
  ;
  ; @example
  ; (remove-items-by [:a :b :c] keyword?)
  ; =>
  ; []
  ;
  ; @return (vector)
  [n f]
  (letfn [(f0 [result x]
              (if (-> x f)
                  (-> result)
                  (-> result (conj x))))]
         (reduce f0 [] n)))

(defn remove-duplicates
  ; @param (vector) n
  ;
  ; @usage
  ; (remove-duplicates [:a :b :c :a])
  ;
  ; @example
  ; (remove-duplicates [:a :b :c :a])
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n]
  (letfn [(f0 [result x]
              (if (-> result (contain/contains-item? x))
                  (-> result)
                  (-> result (conj x))))]
         (reduce f0 [] n)))

(defn remove-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (remove-first-occurence [:a :b :b :c] :b)
  ;
  ; @example
  ; (remove-first-occurence [:a :b :c :d :d :e] :d)
  ; =>
  ; [:a :b :c :d :e]
  ;
  ; @return (vector)
  [n x]
  ; BUG#1130
  ; The ShadowCLJS protects the 'inc' function from receiving a NIL value as parameter.
  ; The 'first-dex-of' function returns through a when condition.
  ; Therefore, without using the '(if (number? item-dex) ...)' condition, the ShadowCLJS
  ; would throw the following error message:
  ; "cljs.core/+, all arguments must be numbers, got [#{nil clj-nil} number] instead"
  (if-let [item-dex (dex/first-dex-of n x)]
          (if (number? item-dex)
              (vec (concat (subvec n 0 item-dex)
                           (subvec n (inc item-dex)))))
          (-> n)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn keep-items
  ; @param (vector) n
  ; @param (vector) xyz
  ;
  ; @usage
  ; (keep-items [:a :b :c :d] [:b :c])
  ;
  ; @example
  ; (keep-items [:a :b :c :d] [:b :c])
  ; =>
  ; [:b :c]
  ;
  ; @return (vector)
  [n xyz]
  (letfn [(f0 [result x]
              (if (-> xyz    (contain/contains-item? x))
                  (-> result (conj                   x))
                  (-> result)))]
         (reduce f0 [] n)))

(defn keep-items-by
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (keep-items-by [:a :b "c" "d"] keyword?)
  ;
  ; @example
  ; (keep-items-by [:a :b "c" "d"] keyword?)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n f]
  (letfn [(f0 [result x]
              (if (-> x f)
                  (-> result (conj x))
                  (-> result)))]
         (reduce f0 [] n)))
