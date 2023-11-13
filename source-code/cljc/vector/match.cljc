
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
  ; (any-item-match? [:a :b :c] string?)
  ; =>
  ; false
  ;
  ; @example
  ; (any-item-match? [:a "b" :c] string?)
  ; =>
  ; true
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
  ; (all-items-match? [:a "b" "c"] string?)
  ; =>
  ; false
  ;
  ; @example
  ; (all-items-match? ["a" "b" "c"] string?)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n f]
  (every? f n))

(defn first-match
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (first-match [:a :b :c "d"] string?)
  ;
  ; @example
  ; (first-match [:a :b :c] string?)
  ; =>
  ; nil
  ;
  ; @example
  ; (first-match [:a "b" :c] string?)
  ; =>
  ; "b"
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
  ; (last-match [:a :b :c "d"] string?)
  ;
  ; @example
  ; (last-match [:a :b :c] string?)
  ; =>
  ; nil
  ;
  ; @example
  ; (last-match [:a "b" :c] string?)
  ; =>
  ; "b"
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
