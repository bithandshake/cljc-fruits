
(ns vector.core
    (:require [vector.check  :as check]
              [vector.dex    :as dex]
              [vector.nth    :as nth]
              [vector.remove :as remove]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

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
             (if (= 0 dex)
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
  ; @description
  ; Cons the item if the vector does not contain it.
  ;
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
  (letfn [(f [result x] (if (-> result (check/contains-item? x))
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
  (vec (apply conj n xyz)))

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
  (letfn [(f [result x] (if (-> result (check/contains-item? x))
                            (-> result)
                            (-> result (conj x))))]
         (vec (reduce f n (vec xyz)))))

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
         (vec (reduce f n (vec xyz)))))

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

(defn align-items
  ; @description
  ; Concatenate items of vectors with end alignment. If a vector's last items are
  ; indentical with the next vector's first items the indentical items will be
  ; merged to avoid duplications.
  ;
  ; @param (list of vectors) abc
  ;
  ; @usage
  ; (align-items [:a :b :c :d] [:c :d :e :f])
  ;
  ; @example
  ; (align-items [:a :b :c :d] [:c :d :e :f])
  ; =>
  ; [:a :b :c :d :e :f]
  ;
  ; @example
  ; (align-items [:a :b :c] [:c :d :e] [:e :f :g])
  ; =>
  ; [:a :b :c :d :e :f :g]
  ;
  ; @return (vector)
  [& abc]
  (letfn [
          ; (aligned? [:a :b :c] [:b :c e] 0)
          ; =>
          ; false
          ;
          ; (aligned? [:a :b :c] [:b :c e] 1)
          ; =>
          ; true
          (aligned? [a b shift] (let [a-size (- (count a) shift)
                                      b-size (min a-size (count b))]
                                     (= (subvec a shift)
                                        (subvec b 0 b-size))))

          ; ...
          (shiftable? [a b prev-shift] (< prev-shift (-> a count dec)))

          ; ...
          (align-f [a b shift] (cond (aligned?   a b shift) (concat-items (subvec a 0 shift) b)
                                     (shiftable? a b shift) (align-f a b (inc shift))
                                     :no-alignment-found    (concat-items a b)))

          ; ...
          (f [result dex x] (if (= dex 0) x (align-f result x 0)))]

         ; ...
         (reduce-kv f [] (vec abc))))

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
  (letfn [(f [result x]
             (if (= x a)
                 (conj-item result b)
                 (conj-item result x)))]
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
        (nil? n) (-> [x])
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
  [n x]
  (let [item-first-dex (dex/item-first-dex n x)
        next-item-dex  (dex/next-dex       n item-first-dex)]
       (nth/nth-item n next-item-dex)))

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
