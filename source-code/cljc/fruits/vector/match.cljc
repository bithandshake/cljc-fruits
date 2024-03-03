
(ns fruits.vector.match
    (:require [fruits.mixed.api    :as mixed]
              [fruits.seqable.api  :as seqable]
              [fruits.vector.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn any-item-matches?
  ; @description
  ; Returns TRUE if any item in the given 'n' vector matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (any-item-matches? [:a "b" :c] string?)
  ; =>
  ; true
  ;
  ; @usage
  ; (any-item-matches? [:a :b :c] string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (-> (some f n) boolean)))

(defn no-item-matches?
  ; @description
  ; Returns TRUE if no item in the given 'n' vector matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (no-item-matches? [:a :b :c] string?)
  ; =>
  ; true
  ;
  ; @usage
  ; (no-item-matches? [:a "b" :c] string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n f]
  (-> (any-item-matches? n f) not))

(defn all-items-match?
  ; @description
  ; Returns TRUE if every item in the given 'n' vector matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (all-items-match? ["a" "b" "c"] string?)
  ; =>
  ; true
  ;
  ; @usage
  ; (all-items-match? [:a "b" "c"] string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (every? f n)))

(defn not-all-items-match?
  ; @description
  ; Returns TRUE if not every item in the given 'n' vector matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (not-all-items-match? [:a "b" "c"] string?)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-all-items-match? ["a" "b" "c"] string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n f]
  (-> (all-items-match? n f) not))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-match
  ; @description
  ; Returns the first item in the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (first-match [:a :b :c "d"] keyword?)
  ; =>
  ; :a
  ;
  ; @return (*)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (loop [dex 0]
             (if (seqable/dex-in-bounds? n dex)
                 (cond (-> n (nth dex) f) (-> n (nth dex))
                       :recur             (-> dex inc recur))))))

(defn second-match
  ; @description
  ; Returns the second item in the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (second-match [:a :b :c "d"] keyword?)
  ; =>
  ; :b
  ;
  ; @return (*)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (loop [dex 0 match-count 0]
             (if (seqable/dex-in-bounds? n dex)
                 (cond (-> n (nth dex) f not) (-> dex inc (recur match-count))
                       (-> 2 (= match-count)) (-> n (nth dex))
                       :recur-after-match     (-> dex inc (recur (inc match-count))))))))

(defn last-match
  ; @description
  ; Returns the last item in the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (last-match [:a :b :c "d"] keyword?)
  ; =>
  ; :c
  ;
  ; @return (*)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (loop [dex (-> n count dec)]
             (if (seqable/dex-in-bounds? n dex)
                 (cond (-> n (nth dex) f) (-> n (nth dex))
                       :recur             (-> dex dec recur))))))

(defn nth-match
  ; @description
  ; Returns the nth item in the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (integer) th
  ;
  ; @usage
  ; (nth-match [:a :b :c "d"] keyword? 1)
  ; =>
  ; :b
  ;
  ; @return (*)
  [n f th]
  (let [n  (mixed/to-vector n)
        f  (mixed/to-ifn f)
        th (mixed/to-integer th)]
       (loop [dex 0 match-count 0]
             (if (seqable/dex-in-bounds? n dex)
                 (cond (-> n (nth dex) f not)  (-> dex inc (recur match-count))
                       (-> th (= match-count)) (-> n (nth dex))
                       :recur-after-match      (-> dex inc (recur (inc match-count))))))))

(defn all-matches
  ; @description
  ; Returns all items in the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (all-matches [:a :b :c "d"] keyword?)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (letfn [(f0 [matches x]
                   (if (-> x f)
                       (-> matches (conj x))
                       (-> matches)))]
              (reduce f0 [] n))))

(defn match-count
  ; @description
  ; Returns how many items in the given 'n' vector matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (match-count [:a :b "c"] keyword?)
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n f]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (count (filter f n))))
