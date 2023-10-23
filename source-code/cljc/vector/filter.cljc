
(ns vector.filter
    (:require [vector.dex :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn filter-items
  ; @param (vector) n
  ; @param (function) filter-f
  ;
  ; @usage
  ; (filter-items [:a :b "c"] keyword?)
  ;
  ; @example
  ; (filter-items [:a :b "c"] keyword?)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n filter-f]
  (vec (filter filter-f n)))

(defn filter-items-by
  ; @param (vector) n
  ; @param (function) filter-f
  ; @param (function) value-f
  ;
  ; @usage
  ; (filter-items-by [{:value :a} {:value :b} {:value "c"}] keyword? :value)
  ;
  ; @example
  ; (filter-items-by [{:value :a} {:value :b} {:value "c"}] keyword? :value)
  ; =>
  ; [{:value :a} {:value :b}]
  ;
  ; @return (vector)
  [n filter-f value-f])
  ; TODO

(defn first-filtered
  ; @param (vector) n
  ; @param (function) filter-f
  ;
  ; @usage
  ; (first-filtered ["a" :b "c"] keyword?)
  ;
  ; @example
  ; (first-filtered ["a" :b "c" :d "e"] keyword?)
  ; =>
  ; :b
  ;
  ; @return (*)
  [n filter-f]
  (letfn [(f [%] (if (filter-f %) %))]
         (some f n)))

(defn first-filtered-by
  ; @param (vector) n
  ; @param (function) filter-f
  ; @param (function) value-f
  ;
  ; @usage
  ; (first-filtered-by [{:value :a} {:value :b} {:value "c"}] keyword? :value)
  ;
  ; @example
  ; (first-filtered-by [{:value :a} {:value :b} {:value "c"}] keyword? :value)
  ; =>
  ; {:value :a}
  ;
  ; @return (*)
  [n filter-f value-f]
  (letfn [(f [%] (if (-> % value-f filter-f) %))]
         (some f n)))

(defn last-filtered
  ; @param (vector) n
  ; @param (function) filter-f
  ;
  ; @usage
  ; (last-filtered ["a" :b "c"] string?)
  ;
  ; @example
  ; (last-filtered ["a" :b "c" :d "e"] keyword?)
  ; =>
  ; :d
  ;
  ; @return (*)
  [n filter-f]
  (letfn [(f [%1 %2] (if (filter-f %2) %2 %1))]
         (reduce f nil n)))

(defn last-filtered-by
  ; @param (vector) n
  ; @param (function) filter-f
  ; @param (function) value-f
  ;
  ; @usage
  ; (last-filtered-by [{:value :a} {:value :b} {:value "c"}] keyword? :value)
  ;
  ; @example
  ; (last-filtered-by [{:value :a} {:value :b} {:value "c"}] keyword? :value)
  ; =>
  ; {:value :b}
  ;
  ; @return (*)
  [n filter-f value-f]
  (letfn [(f [%1 %2] (if (-> %2 value-f filter-f) %2 %1))]
         (reduce f nil n)))

(defn nth-filtered
  ; @param (vector) n
  ; @param (function) filter-f
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
  ; @return (*)
  [n filter-f dex]
  (letfn [(f [x match-count f-dex]
             (if (-> x filter-f not)
                 ; If x is NOT matches ...
                 (if (dex/dex-last? n f-dex)
                     ; If NO more items in n ...
                     (-> nil)
                     ; If more items in n ...
                     (f (get n (inc f-dex)) match-count (inc f-dex)))
                 ; If x is matches ...
                 (if (< match-count dex)
                     ; If x is not the nth match ...
                     (if (dex/dex-last? n f-dex)
                         ; If NO more items in n ...
                         (-> nil)
                         ; If more items in n ...
                         (f (get n (inc f-dex)) (inc match-count) (inc f-dex)))
                     ; If x is the nth match ...
                     (-> x))))]
         (f (first n) 0 0)))

(defn nth-filtered-by
  ; @param (vector) n
  ; @param (function) filter-f
  ; @param (function) value-f
  ; @param (integer) dex
  ;
  ; @usage
  ; (nth-filtered-by [{:value :a} {:value :b} {:value "c"}] keyword? 1)
  ;
  ; @example
  ; (nth-filtered-by [{:value :a} {:value :b} {:value "c"}] keyword? 2)
  ; =>
  ; {:value :b}
  ;
  ; @return (*)
  [n filter-f value-f dex])
  ; TODO

(defn filtered-count
  ; @param (vector) n
  ; @param (function) filter-f
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
  [n filter-f]
  (count (filter filter-f n)))

(defn filtered-count?
  ; @param (vector) n
  ; @param (function) filter-f
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
  [n filter-f x]
  (= x (filtered-count n filter-f)))
