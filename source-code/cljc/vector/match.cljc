
(ns vector.match
    (:require [vector.check :as check]))

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
  (boolean (some f n)))

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
      (letfn [(f0 [dex]
                  (cond (-> n (nth dex) f)
                        (-> n (nth dex))
                        (-> dex (< (-> n count dec)))
                        (-> dex inc f0)))]
             (f0 0))))

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
      (letfn [(f0 [dex]
                  (cond (-> n (nth dex) f)
                        (-> n (nth dex))
                        (-> dex (not= 0))
                        (-> dex dec f0)))]
             (f0 (-> n count dec)))))

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
      (letfn [(f0 [dex match-count]
                  (if (-> n (nth dex) f)
                      (cond (-> th  (= match-count))
                            (-> n   (nth dex))
                            (-> dex (< (-> n count dec)))
                            (f0 (inc dex)
                                (inc match-count)))
                      (cond (-> dex (< (-> n count dec)))
                            (f0 (inc dex) match-count))))]
             (f0 0 0))))

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
