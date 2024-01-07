
(ns fruits.vector.match
    (:require [fruits.vector.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn any-item-matches?
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (any-item-matches? ["a" "b" :c] keyword?)
  ;
  ; @example
  ; (any-item-matches? [:a "b" :c] string?)
  ; =>
  ; true
  ;
  ; @example
  ; (any-item-matches? [:a :b :c] string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n f]
  (-> (some f n) boolean))

(defn no-item-matches?
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (no-item-matches? ["a" "b" :c] keyword?)
  ;
  ; @example
  ; (no-item-matches? [:a :b :c] string?)
  ; =>
  ; true
  ;
  ; @example
  ; (no-item-matches? [:a "b" :c] string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n f]
  (-> (any-item-matches? n f) not))

(defn all-items-match?
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (all-items-match? ["a" "b" "c"] string?)
  ;
  ; @example
  ; (all-items-match? ["a" "b" "c"] string?)
  ; =>
  ; true
  ;
  ; @example
  ; (all-items-match? [:a "b" "c"] string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n f]
  (every? f n))

(defn not-all-items-match?
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (not-all-items-match? ["a" "b" "c"] string?)
  ;
  ; @example
  ; (not-all-items-match? [:a "b" "c"] string?)
  ; =>
  ; true
  ;
  ; @example
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
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (first-match [:a :b :c "d"] keyword?)
  ;
  ; @example
  ; (first-match [:a :b :c "d"] keyword?)
  ; =>
  ; :a
  ;
  ; @return (*)
  [n f]
  (if (check/nonempty? n)
      (loop [dex 0]
            (cond (-> n (nth dex) f)            (-> n (nth dex))
                  (-> dex (< (-> n count dec))) (-> dex inc recur)))))

(defn last-match
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (last-match [:a :b :c "d"] keyword?)
  ;
  ; @example
  ; (last-match [:a :b :c "d"] keyword?)
  ; =>
  ; :c
  ;
  ; @return (*)
  [n f]
  (if (check/nonempty? n)
      (loop [dex (-> n count dec)]
            (cond (-> n (nth dex) f) (-> n (nth dex))
                  (-> dex (not= 0))  (-> dex dec recur)))))

(defn nth-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (integer) th
  ;
  ; @usage
  ; (nth-match [:a :b :c "d"] keyword? 1)
  ;
  ; @example
  ; (nth-match [:a :b :c "d"] keyword? 1)
  ; =>
  ; :b
  ;
  ; @return (*)
  [n f th]
  (if (and (check/nonempty? n)
           (nat-int? th))
      (loop [dex 0 match-count 0]
            (if (-> n (nth dex) f)
                (cond (-> th  (= match-count))
                      (-> n   (nth dex))
                      (-> dex (< (-> n count dec)))
                      (recur (inc dex)
                             (inc match-count)))
                (cond (-> dex (< (-> n count dec)))
                      (recur (inc dex) match-count))))))

(defn all-matches
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (all-matches [:a :b :c "d"] keyword?)
  ;
  ; @example
  ; (all-matches [:a :b :c "d"] keyword?)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n f]
  (if (check/nonempty? n)
      (letfn [(f0 [matches x]
                  (if (-> x f)
                      (-> matches conj x)
                      (-> matches)))]
             (reduce f0 [] n))))

(defn match-count
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (match-count [:a :b "c"] string?)
  ;
  ; @example
  ; (match-count [:a :b "c"] keyword?)
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n f]
  (count (filter f n)))
