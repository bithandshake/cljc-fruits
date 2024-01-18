
(ns fruits.vector.add
    (:require [fruits.vector.contain :as contain]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn conj-item
  ; @description
  ; Conjoins the given items to the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (conj-item [:a :b] :c)
  ; =>
  ; [:a :b :c]
  ;
  ; @usage
  ; (conj-item [:a :b] :c :d)
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  [n & xyz]
  (let [n (mixed/to-vector n)]
       (apply conj n xyz)))

(defn conj-item-once
  ; @description
  ; Conjoins the given items to the given 'n' vector in case it doesn't contain them already.
  ;
  ; @param (vector) n
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (conj-item-once [:a :b] :b :c :d)
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  [n & xyz]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [result x]
                   (if (-> result (contain/contains-item? x))
                       (-> result)
                       (-> result (conj x))))]
              (reduce f0 n (vec xyz)))))

(defn conj-some
  ; @description
  ; Conjoins the given items to the given 'n' vector in case the items are not NIL.
  ;
  ; @param (vector) n
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (conj-some [:a :b] :c nil)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n & xyz]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [result x]
                   (if x (-> result (conj x))
                         (-> result)))]
              (reduce f0 n (vec xyz)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn cons-item
  ; @description
  ; Constructs a new vector where the given items are the first and the items of the given 'n' vector are the rest.
  ;
  ; @param (vector) n
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (cons-item [:a :b] :c :d :e)
  ; =>
  ; [:e :d :c :a :b]
  ;
  ; @return (vector)
  [n & xyz]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [result x] (cons x result))]
              (vec (reduce f0 n (vec xyz))))))

(defn cons-item-once
  ; @description
  ; Constructs a new vector where the given items are the first and the items of the given 'n' vector are the rest
  ; in case the 'n' vector doesn't contain them already.
  ;
  ; @param (vector) n
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (cons-item-once [:a :b] :b :c)
  ; =>
  ; [:c :a :b]
  ;
  ; @usage
  ; (cons-item-once [:a :b] :b :c :d :e)
  ; =>
  ; [:e :d :c :a :b]
  ;
  ; @return (vector)
  [n & xyz]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [result x]
                   (if (-> result (contain/contains-item? x))
                       (-> result)
                       (-> x (cons result))))]
              (vec (reduce f0 n (vec xyz))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn concat-items
  ; @description
  ; Concatenates the given vectors into one.
  ;
  ; @param (list of vectors) abc
  ;
  ; @usage
  ; (concat-items [:a :b] [:c :d] [:e :f])
  ; =>
  ; [:a :b :c :d :e :f]
  ;
  ; @return (vector)
  [& abc]
  (let [abc (map mixed/to-vector abc)]
       (vec (apply concat abc))))

(defn concat-items-once
  ; @description
  ; Concatenates the given vectors into one without duplicating any item.
  ;
  ; @param (list of vectors) abc
  ;
  ; @usage
  ; (concat-items-once [:a :b :c] [:c :d :e :a])
  ; =>
  ; [:b :c :d :e :a]
  ;
  ; @return (vector)
  [& abc]
  (let [abc (map mixed/to-vector abc)]
       (-> (apply concat abc) set vec)))
