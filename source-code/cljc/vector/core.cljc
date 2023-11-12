
(ns vector.core
    (:require [seqable.api   :as seqable]
              [vector.item   :as item]
              [vector.remove :as remove]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn sum-items-by
  ; @description
  ; - Sums the derived values of items in the given 'n' vector.
  ; - Values are derived by applying the given 'v-f' function on the item.
  ;
  ; @param (vector) n
  ; @param (function) v-f
  ;
  ; @usage
  ; (sum-items-by [{:value 10} {:value 5}] :value)
  ;
  ; @example
  ; (sum-items-by [{:value 10} {:value 5}] :value)
  ; =>
  ; 15
  ;
  ; @return (integer)
  [n v-f]
  (letfn [(f [sum x] (let [v (v-f x)]
                          (if (-> v integer?)
                              (+  sum v)
                              (-> sum))))]
         (reduce f 0 n)))

(defn gap-items
  ; @param (*) n
  ; @param (*) delimiter
  ;
  ; @usage
  ; (gap-items [:A :B] :x)
  ;
  ; @example
  ; (gap-items [:A :B :C :D] :x)
  ; =>
  ; [:A :x :B :x :C :x :D]
  ;
  ; @return (vector)
  [n delimiter]
  (letfn [(f [result dex x]
             (if (-> dex zero?)
                 [x]
                 (conj result delimiter x)))]
         (reduce-kv f [] n)))

(defn repeat-item
  ; @param (*) n
  ; @param (integer) count
  ;
  ; @usage
  ; (repeat-item :a 5)
  ;
  ; @example
  ; (repeat-item :a 5)
  ; =>
  ; [:a :a :a :a :a]
  ;
  ; @return (vector)
  [n count]
  (vec (repeat count n)))

(defn cons-item
  ; @param (vector) n
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (cons-item [:a :b] :c :d :e)
  ;
  ; @example
  ; (cons-item [:a :b] :c :d :e)
  ; =>
  ; [:e :d :c :a :b]
  ;
  ; @return (vector)
  [n & xyz]
  (letfn [(f [result x] (cons x result))]
         (vec (reduce f n (vec xyz)))))

(defn cons-item-once
  ; @param (vector) n
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (cons-item-once [:a :b] :b)
  ;
  ; @usage
  ; (cons-item-once [:a :b] :b :c :d :e)
  ;
  ; @example
  ; (cons-item-once [:a :b] :b :c :d :e)
  ; =>
  ; [:e :d :c :a :b]
  ;
  ; @return (vector)
  [n & xyz]
  (letfn [(f [result x] (if (-> result (item/contains-item? x))
                            (-> result)
                            (-> x (cons result))))]
         (vec (reduce f n (vec xyz)))))

(defn conj-item
  ; @param (vector) n
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (conj-item [:a :b] :c)
  ;
  ; @usage
  ; (conj-item [:a :b] :c :d)
  ;
  ; @example
  ; (conj-item [:a :b] :c)
  ; =>
  ; [:a :b :c]
  ;
  ; @example
  ; (conj-item [:a :b] :c :d)
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  [n & xyz]
  (apply conj n xyz))

(defn conj-item-once
  ; @description
  ; Conj the item if the vector does not contain it.
  ;
  ; @param (vector) n
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (conj-item-once [:a :b] :b)
  ;
  ; @usage
  ; (conj-item-once [:a :b] :b :c :d)
  ;
  ; @example
  ; (conj-item-once [:a :b] :b :c :d)
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  [n & xyz]
  (letfn [(f [result x] (if (-> result (item/contains-item? x))
                            (-> result)
                            (-> result (conj x))))]
         (reduce f n (vec xyz))))

(defn conj-some
  ; @description
  ; Conj the item if it is NOT nil.
  ;
  ; @param (vector) n
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (conj-some [:a :b] :c)
  ;
  ; @usage
  ; (conj-some [:a :b] :c nil)
  ;
  ; @example
  ; (conj-some [:a :b] :c nil)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n & xyz]
  (letfn [(f [result x] (if x (-> result (conj x))
                              (-> result)))]
         (reduce f n (vec xyz))))

(defn concat-items
  ; @param (list of vectors) abc
  ;
  ; @usage
  ; (concat-items [:a :b] [:c :d] [:e :f])
  ;
  ; @example
  ; (concat-items [:a :b] [:c :d] [:e :f])
  ; =>
  ; [:a :b :c :d :e :f]
  ;
  ; @return (vector)
  [& abc]
  (vec (apply concat abc)))

(defn concat-items-once
  ; @param (list of vectors) abc
  ;
  ; @usage
  ; (concat-items-once [:a :b] [:a :c])
  ;
  ; @example
  ; (concat-items-once [:a :b :c] [:c :d :e :a])
  ; =>
  ; [:b :c :d :e :a]
  ;
  ; @return (vector)
  [& abc]
  (-> (apply concat abc) set vec))

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
  ; (replace-item [:a :b :c] :c :x)
  ;
  ; @example
  ; (replace-item [:a :b :c :d :c] :c :x)
  ; =>
  ; [:a :b :x :d :x]
  ;
  ; @return (vector)
  [n a b]
  (letfn [(f [result x]
             (if (= x a)
                 (conj result b)
                 (conj result x)))]
         (reduce f [] n)))

(defn insert-item
  ; @description
  ; Inserts the given 'x' value into the given 'n' vector to a specific position.
  ;
  ; @param (vector) n
  ; @param (integer) cursor
  ; @param (*) x
  ;
  ; @usage
  ; (insert-item [:a :b :c] 0 :x)
  ;
  ; @example
  ; (insert-item [:a :b :c] 2 :d)
  ; =>
  ; [:a :b :d :c]
  ;
  ; @example
  ; (insert-item [:a :b :c] 999 :d)
  ; =>
  ; [:a :b :d :c]
  ;
  ; @example
  ; (insert-item nil 999 :d)
  ; =>
  ; [:d]
  ;
  ; @example
  ; (insert-item {:a "b"} 1 :d)
  ; =>
  ; {:a "b"}
  ;
  ; @return (vector)
  [n cursor x]
  (let [n      (if (vector? n) n [])
        cursor (seqable/normalize-cursor n cursor)]
       (concat-items (subvec n 0 cursor)
                     [x]
                     (subvec n cursor))))

(defn toggle-item
  ; @description
  ; Toggles the presence of the given 'x' value in the 'n' vector.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (toggle-item [:a :b] :a)
  ;
  ; @example
  ; (toggle-item [:a :b] :c)
  ; =>
  ; [:a :b :c]
  ;
  ; @example
  ; (toggle-item [:a :b :c] :c)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n x]
  (if (item/contains-item? n x)
      (remove/remove-item  n x)
      (conj                n x)))
