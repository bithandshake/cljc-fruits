
(ns vector.dex
    (:require [loop.api     :refer [some-indexed]]
              [math.api     :as math]
              [sequence.api :as sequence]
              [vector.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn dex-in-bounds?
  ; @description
  ; - Returns TRUE if the given 'dex' value is falls between 0 and the highest possible index value ('(-> n count dec)').
  ; - Cursors are different from indexes in vectors.
  ;   A cursor could be at the very end of the vector while an index could only point to the last item at the end.
  ;
  ; @param (*) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dex-in-bounds? [:a :b :c] 2)
  ;
  ; @example
  ; (dex-in-bounds? [:a :b :c] 2)
  ; =>
  ; true
  ;
  ; @example
  ; (dex-in-bounds? [:a :b :c] 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n dex]
  (and (-> dex nat-int?)
       (-> n count (> dex))))

(defn dex-out-of-bounds?
  ; @description
  ; - Returns TRUE if the given 'dex' value is NOT falls between 0 and the highest possible index value ('(-> n count dec)').
  ; - Cursors are different from indexes in vectors.
  ;   A cursor could be at the very end of the vector while an index could only point to the last item at the end.
  ;
  ; @param (*) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dex-out-of-bounds? [:a :b :c] 3)
  ;
  ; @example
  ; (dex-out-of-bounds? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @example
  ; (dex-out-of-bounds? [:a :b :c] 2)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n dex]
  (or (-> dex nat-int?)
      (-> n count (<= dex))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-dex?
  ; @param (*) n
  ;
  ; @usage
  ; (item-dex? 42)
  ;
  ; @example
  ; (item-dex? 42)
  ; =>
  ; true
  ;
  ; @example
  ; (item-dex? -3)
  ; =>
  ; false
  ;
  ; @example
  ; (item-dex? :a)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n integer?)
       (>= n 0)))

(defn dex-range
  ; @param (vector) n
  ;
  ; @usage
  ; (dex-range [:a :b :c])
  ;
  ; @example
  ; (dex-range [:a :b :c])
  ; =>
  ; [0 1 2]
  ;
  ; @return (vector)
  [n]
  (-> n count range vec))

(defn dex-first?
  ; @param (integer) dex
  ;
  ; @usage
  ; (dex-first? 1)
  ;
  ; @example
  ; (dex-first? 1)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [dex]
  (= dex 0))

(defn dex-last?
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dex-last? [:a :b :c] 2)
  ;
  ; @example
  ; (dex-last? [:a :b :c] 2)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n dex]
  (= (inc   dex)
     (count n)))

(defn last-dex
  ; @param (vector) n
  ;
  ; @usage
  ; (last-dex [:a :b :c])
  ;
  ; @example
  ; (last-dex [:a :b :c])
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n]
  (if (-> n check/nonempty?)
      (-> n count dec)))

(defn next-dex
  ; @description
  ; Returns with the next item's index after the given dex.
  ; At the end of the vector it jumps to the first index.
  ;
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (next-dex [:a :b :c :d] 0)
  ;
  ; @example
  ; (next-dex [:a :b :c :d] 1)
  ; =>
  ; 2
  ;
  ; @example
  ; (next-dex [:a :b :c :d] 3)
  ; =>
  ; 0
  ;
  ; @example
  ; (next-dex [] 3)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n dex]
  (if (-> n check/nonempty?)
      (-> dex (sequence/next-dex 0 (-> n count dec)))
      (-> 0)))

(defn inc-dex
  ; @description
  ; Returns with the next item's index after the given dex.
  ; At the end of the vector it stops.
  ;
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (inc-dex [:a :b :c :d] 3)
  ;
  ; @example
  ; (inc-dex [:a :b :c :d] 1)
  ; =>
  ; 2
  ;
  ; @example
  ; (inc-dex [:a :b :c :d] 3)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n dex]
  (if (-> n (dex-last? dex))
      (-> dex)
      (-> dex inc)))

(defn prev-dex
  ; @description
  ; Returns with the previous item's index before the given dex.
  ; At the beginning of the vector it jumps to the last index.
  ;
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @example
  ; (prev-dex [:a :b :c :d] 0)
  ; =>
  ; 3
  ;
  ; @example
  ; (prev-dex [:a :b :c :d] 0)
  ; =>
  ; 3
  ;
  ; @example
  ; (prev-dex [] 3)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n dex]
  (if (-> n vector?)
      (-> dex (sequence/prev-dex 0 (-> n count dec)))
      (-> 0)))

(defn dec-dex
  ; @description
  ; Returns with the previous item's index before the given dex.
  ; At the beginning of the vector it stops.
  ;
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dec-dex [:a :b :c :d] 2)
  ;
  ; @example
  ; (dec-dex [:a :b :c :d] 3)
  ; =>
  ; 2
  ;
  ; @example
  ; (dec-dex [:a :b :c :d] 0)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n dex]
  (if (-> dex dex-first?)
      (-> dex)
      (-> dex dec)))

(defn match-dex
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @example
  ; (match-dex ["a" "b" "c"] 0)
  ; =>
  ; 0
  ;
  ; @example
  ; (match-dex ["a" "b" "c"] 1)
  ; =>
  ; 1
  ;
  ; @example
  ; (match-dex ["a" "b" "c"] 2)
  ; =>
  ; 2
  ;
  ; @example
  ; (match-dex ["a" "b" "c"] 3)
  ; =>
  ; 0
  ;
  ; @example
  ; (match-dex ["a" "b" "c"] 4)
  ; =>
  ; 1
  ;
  ; @return (integer)
  [n dex]
  ; A vektor elemeinek száma alapján meghatározza, hogy a dex paraméterként átadott
  ; érték a vektor hányadik indexének felelne meg ha a vektor elemeit addig ismételnénk,
  ; hogy legyen legalább annyi eleme, mint amilyen magas szám a dex értéke.
  (let [count (count n)
        x     (-> dex (/ count)
                      (math/floor))
        y     (* x count)]
       (- dex y)))

(defn item-last-dex
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (item-last-dex [:a :b :a :b] :b)
  ;
  ; @example
  ; (item-last-dex [:a :b :a :b] :b)
  ; =>
  ; 3
  ;
  ; @return (nil or integer)
  [n x]
  (when (vector? n)
        (letfn [(f [%1 %2 %3] (if (= %3 x) %2 %1))]
               (reduce-kv f nil n))))

(defn item-first-dex
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @example
  ; (item-first-dex [:a :b :a :b] :b)
  ; =>
  ; 1
  ;
  ; @return (nil or integer)
  [n x]
  (when (vector? n)
        (letfn [(f [%1 %2] (if (= %2 x) %1))]
               (some-indexed f n))))
