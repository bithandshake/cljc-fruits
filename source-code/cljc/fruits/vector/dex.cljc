
(ns fruits.vector.dex
    (:require [fruits.mixed.api    :as mixed]
              [fruits.seqable.api  :as seqable]
              [fruits.vector.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-dex-of
  ; @description
  ; Returns the index of the first occurence of the given 'x' item in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (first-dex-of [:a :b :a :b] :b)
  ; =>
  ; 1
  ;
  ; @return (integer)
  [n x]
  (let [n (mixed/to-vector n)]
       (loop [dex 0]
             (if (seqable/dex-in-bounds? n dex)
                 (cond (-> x (= (nth n dex))) (-> dex)
                       :recur                 (-> dex inc recur))))))

(defn second-dex-of
  ; @description
  ; Returns the index of the second occurence of the given 'x' item in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (second-dex-of [:a :b :a :b] :b)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n x]
  (let [n (mixed/to-vector n)]
       (loop [dex 0 match-count 0]
             (if (seqable/dex-in-bounds? n dex)
                 (cond (-> x (not= (nth n dex))) (recur (inc dex) match-count)
                       (=  2 (inc match-count))  (-> dex)
                       :recur-after-match        (recur (inc dex) (inc match-count)))))))

(defn last-dex-of
  ; @description
  ; Returns the index of the last occurence of the given 'x' item in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (last-dex-of [:a :b :a :b] :b)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n x]
  (let [n (mixed/to-vector n)]
       (loop [dex (-> n count dec)]
             (if (seqable/dex-in-bounds? n dex)
                 (cond (-> x (= (nth n dex))) (-> dex)
                       :recur                 (-> dex dec recur))))))

(defn nth-dex-of
  ; @description
  ; Returns the index of the nth occurence of the given 'x' item in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (*) x
  ; @param (integer) th
  ;
  ; @usage
  ; (nth-dex-of [:a :b :a :b :a :b] :b 1)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n x th]
  (let [n  (mixed/to-vector n)
        th (mixed/to-integer th)]
       (loop [dex 0 match-count 0]
             (if (seqable/dex-in-bounds? n dex)
                 (cond (-> x (not= (nth n dex))) (recur (inc dex) match-count)
                       (= th (inc match-count))  (-> dex)
                       :recur-after-match        (recur (inc dex) (inc match-count)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-dex-by
  ; @description
  ; Returns the index of the first match of the given 'test-f' predicate function in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (first-dex-by ["a" :b :c :d] keyword?)
  ; =>
  ; 1
  ;
  ; @return (integer)
  [n test-f]
  (let [n      (mixed/to-vector n)
        test-f (mixed/to-ifn test-f)]
       (loop [dex 0]
             (if (seqable/dex-in-bounds? n dex)
                 (cond (-> n (nth dex) test-f) (-> dex)
                       :recur                  (-> dex inc recur))))))

(defn second-dex-by
  ; @description
  ; Returns the index of the second match of the given 'test-f' predicate function in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (second-dex-by ["a" :b :c :d] keyword?)
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n test-f]
  (let [n      (mixed/to-vector n)
        test-f (mixed/to-ifn test-f)]
       (loop [dex 0 match-count 0]
             (if (seqable/dex-in-bounds? n dex)
                 (cond (-> n (nth dex) test-f not) (recur (inc dex) match-count)
                       (=  2 (inc match-count))    (-> dex)
                       :recur-after-match          (recur (inc dex) (inc match-count)))))))

(defn last-dex-by
  ; @description
  ; Returns the index of the last match of the given 'test-f' predicate function in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (last-dex-by [:a :b :c "b"] keyword?)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n test-f]
  (let [n      (mixed/to-vector n)
        test-f (mixed/to-ifn test-f)]
       (loop [dex (-> n count dec)]
             (if (seqable/dex-in-bounds? n dex)
                 (cond (-> n (nth dex) test-f) (-> dex)
                       :recur                  (-> dex dec recur))))))

(defn nth-dex-by
  ; @description
  ; Returns the index of the nth match of the given 'test-f' predicate function in the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (integer) th
  ;
  ; @usage
  ; (nth-dex-by ["a" :b :c :d] keyword? 1)
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n test-f th]
  (let [n      (mixed/to-vector n)
        test-f (mixed/to-ifn test-f)
        th     (mixed/to-integer th)]
       (loop [dex 0 match-count 0]
             (if (seqable/dex-in-bounds? n dex)
                 (cond (-> n (nth dex) test-f not) (recur (inc dex) match-count)
                       (= th (inc match-count))    (-> dex)
                       :recur-after-match          (recur (inc dex) (inc match-count)))))))
