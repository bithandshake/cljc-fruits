
(ns vector.match
    (:require [loop.api :refer [some-indexed]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn any-item-match?
  ; @param (vector) n
  ; @param (function) test-f
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
  [n test-f]
  (boolean (some test-f n)))

(defn all-items-match?
  ; @param (vector) n
  ; @param (function) test-f
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
  [n test-f]
  (every? test-f n))

(defn get-first-match-item
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (get-first-match-item [:a :b :c "d"] string?)
  ;
  ; @example
  ; (get-first-match-item [:a :b :c] string?)
  ; =>
  ; nil
  ;
  ; @example
  ; (get-first-match-item [:a "b" :c] string?)
  ; =>
  ; "b"
  ;
  ; @return (*)
  [n test-f]
  (some test-f n))

(defn get-first-match-item-dex
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (get-first-match-item-dex [:a :b :c "d"] string?)
  ;
  ; @example
  ; (get-first-match-item-dex [:a :b :c] string?)
  ; =>
  ; nil
  ;
  ; @example
  ; (get-first-match-item-dex [:a "b" :c] string?)
  ; =>
  ; 1
  ;
  ; @return (*)
  [n test-f]
  (letfn [(f [%1 %2] (if (test-f) %2) %1)]
         (some-indexed f n)))
