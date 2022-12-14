
(ns vector.core
    (:require [candy.api     :refer [return]]
              [vector.check  :as check]
              [vector.dex    :as dex]
              [vector.remove :as remove]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn repeat-item
  ; @param (*) n
  ; @param (integer) x
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
  [n x]
  (vec (repeat x n)))

(defn cons-item
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (cons-item [:a :b] :c)
  ;
  ; @example
  ; (cons-item [:a :b] :c)
  ; =>
  ; [:c :a :b]
  ;
  ; @return (vector)
  [n x]
  (vec (cons x n)))

(defn cons-item-once
  ; @description
  ; Cons the item if the vector does not contain it.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (cons-item-once [:a :b] :b)
  ;
  ; @example
  ; (cons-item-once [:a :b] :b)
  ; =>
  ; [:a :b]
  ;
  ; @example
  ; (cons-item-once [:a :b] :c)
  ; =>
  ; [:c :a :b]
  ;
  ; @return (vector)
  [n x]
  (if (check/contains-item? n x)
      (return               n)
      (cons-item            n x)))

(defn conj-item
  ; @param (vector) n
  ; @param (*) x
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
  (vec (apply conj n xyz)))

(defn conj-item-once
  ; @description
  ; Conj the item if the vector does not contain it.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (conj-item-once [:a :b] :b)
  ;
  ; @example
  ; (conj-item-once [:a :b] :b)
  ; =>
  ; [:a :b]
  ;
  ; @example
  ; (conj-item-once [:a :b] :c)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n x]
  (if (check/contains-item? n x)
      (return               n)
      (conj-item            n x)))

(defn conj-some
  ; @description
  ; Conj the item if it is NOT nil.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (conj-some [:a :b] :c)
  ;
  ; @example
  ; (conj-some [:a :b] :c)
  ; =>
  ; [:a :b :c]
  ;
  ; @example
  ; (conj-some [:a :b] nil)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n x]
  (if x (conj-item n x)
        (return    n)))

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

(defn change-item
  ; @param (vector) n
  ; @param (*) a
  ; @param (*) b
  ;
  ; @usage
  ; (change-item [:a :b :c] :c :x)
  ;
  ; @example
  ; (change-item [:a :b :c :d :c] :c :x)
  ; =>
  ; [:a :b :x :d :x]
  ;
  ; @return (vector)
  [n a b]
  (letfn [(f [o x]
             (if (= x a)
                 (conj-item o b)
                 (conj-item o x)))]
         (reduce f [] n)))

(defn inject-item
  ; @param (vector) n
  ; @param (integer) dex
  ; @param (*) x
  ;
  ; @usage
  ; (inject-item [:a :b :c] 0 :x)
  ;
  ; @example
  ; (inject-item [:a :b :c] 2 :d)
  ; =>
  ; [:a :b :d :c]
  ;
  ; @example
  ; (inject-item [:a :b :c] 999 :d)
  ; =>
  ; [:a :b :d :c]
  ;
  ; @example
  ; (inject-item nil 999 :d)
  ; =>
  ; [:d]
  ;
  ; @example
  ; (inject-item {:a "b"} 1 :d)
  ; =>
  ; {:a "b"}
  ;
  ; @return (vector)
  [n dex x]
  (cond (vector? n)
        (if (dex/dex-out-of-bounds? n dex)
            (conj-item n x)
            (concat-items (subvec n 0 dex)
                          [x]
                          (subvec n dex)))
        (nil? n) (return [x])
        :return n))

(defn toggle-item
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
  (if (check/contains-item? n x)
      (remove/remove-item   n x)
      (conj-item            n x)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn prev-item
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @example
  ; (prev-item [:a :b :c] :c)
  ;
  ; @example
  ; (prev-item [:a :b :c :d] :b)
  ; =>
  ; :a
  ;
  ; @example
  ; (prev-item [:a :b :c :d] nil)
  ; =>
  ; :d
  ;
  ; @example
  ; (prev-item [] :a)
  ; =>
  ; ?
  ;
  ; @return (*)
  [n x])

(defn next-item
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (next-item [:a :b :c] :a)
  ;
  ; @example
  ; (next-item [:a :b :c :d] :a)
  ; =>
  ; :b
  ;
  ; @example
  ; (next-item [:a :b :c :d] nil)
  ; =>
  ; :a
  ;
  ; @example
  ; (next-item [] :a)
  ; =>
  ; ?
  ;
  ; @return (*)
  [n x])
  ;(let [item-first-dex (item-first-dex n x)
  ;     next-item-dex  (next-dex       n item-first-dex)
  ;    (nth-item n next-item-dex)])

(defn last-item
  ; @param (vector) n
  ;
  ; @usage
  ; (last-item [:a :b :c])
  ;
  ; @example
  ; (last-item [:a :b :c])
  ; =>
  ; :c
  ;
  ; @return (*)
  [n]
  (last n))

(defn first-item
  ; @param (vector) n
  ;
  ; @usage
  ; (first-item [:a :b :c])
  ;
  ; @example
  ; (first-item [:a :b :c])
  ; =>
  ; :a
  ;
  ; @return (*)
  [n]
  (first n))
