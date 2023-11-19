
(ns vector.match
    (:require [vector.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn any-item-match?
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (any-item-match? ["a" "b" :c] keyword?)
  ;
  ; @example
  ; (any-item-match? [:a "b" :c] string?)
  ; =>
  ; true
  ;
  ; @example
  ; (any-item-match? [:a :b :c] string?)
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
  ; @param (integer) dex
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
  [n f dex]
  (if (check/nonempty? n)
      (letfn [(f0 [f-dex match-count]
                  (if (-> n (nth f-dex) f)
                      (cond (-> dex   (= match-count))
                            (-> n     (nth f-dex))
                            (-> f-dex (< (-> n count dec)))
                            (f0 (inc f-dex)
                                (inc match-count)))
                      (cond (-> f-dex (< (-> n count dec)))
                            (f0 (inc f-dex) match-count))))]
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
