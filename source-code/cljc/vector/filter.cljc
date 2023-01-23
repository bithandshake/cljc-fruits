
(ns vector.filter
    (:require [noop.api   :refer [return]]
              [vector.dex :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn filter-items-by
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (filter-items-by [:a :b "c"] keyword?)
  ;
  ; @example
  ; (filter-items-by [:a :b "c"] keyword?)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n test-f]
  (vec (filter test-f n)))

(defn first-filtered
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (first-filtered ["a" :b "c"] keyword?)
  ;
  ; @example
  ; (first-filtered ["a" :b "c" :d "e"] keyword?)
  ; =>
  ; :b
  ;
  ; (first-filtered ["a" :b "c" :d "e"] #(string? %1))
  ; =>
  ; "a"
  ;
  ; @return (*)
  [n test-f]
  (letfn [(f [%] (if (test-f %) %))]
         (some f n)))

(defn last-filtered
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (last-filtered ["a" :b "c"] string?)
  ;
  ; @example
  ; (last-filtered ["a" :b "c" :d "e"] keyword?)
  ; =>
  ; :d
  ;
  ; (last-filtered ["a" :b "c" :d "e"] #(string? %1))
  ; =>
  ; "e"
  ;
  ; @return (*)
  [n test-f]
  (letfn [(f [%1 %2] (if (test-f %2) %2 %1))]
         (reduce f nil n)))

(defn nth-filtered
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (integer) dex
  ;
  ; @usage
  ; (nth-filtered ["a" :b "c"] keyword? 1)
  ;
  ; @example
  ; (nth-filtered ["a" :b "c" :d "e"] keyword? 2)
  ; =>
  ; :d
  ;
  ; (nth-filtered ["a" :b "c" :d "e"] #(string? %1) 2)
  ; =>
  ; "c"
  ;
  ; @return (*)
  [n test-f dex]
  (letfn [(f [x match-count f-dex]
             (if (-> x test-f not)
                 ; If x is NOT matches ...
                 (if (dex/dex-last? n f-dex)
                     ; If NO more items in n ...
                     (return nil)
                     ; If more items in n ...
                     (f (get n (inc f-dex)) match-count (inc f-dex)))
                 ; If x is matches ...
                 (if (< match-count dex)
                     ; If x is not the nth match ...
                     (if (dex/dex-last? n f-dex)
                         ; If NO more items in n ...
                         (return nil)
                         ; If more items in n ...
                         (f (get n (inc f-dex)) (inc match-count) (inc f-dex)))
                     ; If x is the nth match ...
                     (return x))))]
         (f (first n) 0 0)))

(defn filtered-count
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (filtered-count [:a :b "c"] string?)
  ;
  ; @example
  ; (filtered-count [:a :b "c"] keyword?)
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n test-f]
  (count (filter test-f n)))

(defn filtered-count?
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (integer) x
  ;
  ; @usage
  ; (filtered-count? [:a :b "c"] string? 1)
  ;
  ; @example
  ; (filtered-count? [:a :b "c"] keyword? 2)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n test-f x]
  (= x (filtered-count n test-f)))
