
(ns fruits.vector.result
    (:require [fruits.mixed.api    :as mixed]
              [fruits.seqable.api  :as seqable]
              [fruits.vector.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-result
  ; @description
  ; Returns the first item of the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (first-result [:a :b :c "d"] keyword?)
  ; =>
  ; true
  ;
  ; @usage
  ; (first-result [:a :b :c "d"] #(if (keyword? %) (name %)))
  ; =>
  ; "a"
  ;
  ; @return (*)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (loop [dex 0]
             (if (seqable/dex-in-bounds? n dex)
                 (if-let [result (-> n (nth dex) f)]
                         (-> result)
                         (-> dex inc recur))))))

(defn second-result
  ; @description
  ; Returns the second item of the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (second-result [:a :b :c "d"] keyword?)
  ; =>
  ; true
  ;
  ; @usage
  ; (second-result [:a :b :c "d"] #(if (keyword? %) (name %)))
  ; =>
  ; "b"
  ;
  ; @return (*)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (loop [dex 0 match-count 0]
             (if (seqable/dex-in-bounds? n dex)
                 (if-let [result (-> n (nth dex) f)]
                         (if (-> 1 (= match-count))
                             (-> result)
                             (recur (inc dex)
                                    (inc match-count)))
                         (recur (inc dex) match-count))))))

(defn last-result
  ; @description
  ; Returns the last item of the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (last-result [:a :b :c "d"] keyword?)
  ; =>
  ; true
  ;
  ; @usage
  ; (last-result [:a :b :c "d"] #(if (keyword? %) (name %)))
  ; =>
  ; "c"
  ;
  ; @return (*)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (loop [dex (-> n count dec)]
             (if (seqable/dex-in-bounds? n dex)
                 (if-let [result (-> n (nth dex) f)]
                         (-> result)
                         (-> dex dec recur))))))

(defn nth-result
  ; @description
  ; Returns the nth item of the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (integer) th
  ;
  ; @usage
  ; (nth-result [:a :b :c "d"] keyword? 1)
  ; =>
  ; true
  ;
  ; @usage
  ; (nth-result [:a :b :c "d"] #(if (keyword? %) (name %)) 1)
  ; =>
  ; "b"
  ;
  ; @return (*)
  [n f th]
  (let [n  (mixed/to-vector n)
        f  (mixed/to-ifn f)
        th (mixed/to-integer th)]
       (loop [dex 0 match-count 0]
             (if (seqable/dex-in-bounds? n dex)
                 (if-let [result (-> n (nth dex) f)]
                         (if (-> th (= match-count))
                             (-> result)
                             (recur (inc dex)
                                    (inc match-count)))
                         (recur (inc dex) match-count))))))

(defn all-results
  ; @description
  ; Returns all items of the given 'n' vector that match the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (all-results [:a :b :c "d"] keyword?)
  ; =>
  ; [true true true]
  ;
  ; @usage
  ; (all-results [:a :b :c "d"] #(if (keyword? %) (name %)))
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @return (vector)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (letfn [(f0 [results x]
                   (if-let [result (f x)]
                           (-> results conj result)
                           (-> results)))]
              (reduce f0 [] n))))

(defn result-count
  ; @description
  ; Returns how many items of the given 'n' vector match the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (result-count [:a :b "c"] keyword?)
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (count (filter f n))))
