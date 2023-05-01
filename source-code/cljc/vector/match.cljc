
(ns vector.match
    (:require [loop.api :refer [some-indexed]]
              [noop.api :refer [return]]))

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

(defn get-first-match
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (get-first-match [:a :b :c "d"] string?)
  ;
  ; @example
  ; (get-first-match [:a :b :c] string?)
  ; =>
  ; nil
  ;
  ; @example
  ; (get-first-match [:a "b" :c] string?)
  ; =>
  ; "b"
  ;
  ; @return (*)
  [n test-f]
  (letfn [(f [%] (if (test-f %)
                     (return %)))]
         (some f n)))

(defn get-last-match
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (get-last-match [:a :b :c "d"] string?)
  ;
  ; @example
  ; (get-last-match [:a :b :c] string?)
  ; =>
  ; nil
  ;
  ; @example
  ; (get-last-match [:a "b" :c] string?)
  ; =>
  ; "b"
  ;
  ; @return (*)
  [n test-f]
  (letfn [(f [%1 %2] (if (test-f %2) %2 %1))]
         (reduce f nil n)))

(defn get-first-match-dex
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (get-first-match-dex [:a :b :c "d"] string?)
  ;
  ; @example
  ; (get-first-match-dex [:a :b :c] string?)
  ; =>
  ; nil
  ;
  ; @example
  ; (get-first-match-dex [:a "b" :c] string?)
  ; =>
  ; 1
  ;
  ; @return (*)
  [n test-f]
  (letfn [(f [%1 %2] (if (test-f %2) %1))]
         (some-indexed f n)))

(defn get-last-match-dex
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (get-last-match-dex [:a :b :c "d"] string?)
  ;
  ; @example
  ; (get-last-match-dex [:a :b :c] string?)
  ; =>
  ; nil
  ;
  ; @example
  ; (get-last-match-dex [:a "b" :c] string?)
  ; =>
  ; 1
  ;
  ; @return (*)
  [n test-f]
  (letfn [(f [%1 %2 %3] (if (test-f %3) %2 %1))]
         (reduce-kv f nil n)))
