
(ns vector.dex
    (:require [candy.api    :refer [return]]
              [loop.api     :refer [some-indexed]]
              [math.api     :as math]
              [sequence.api :as sequence]
              [vector.type  :as type]))

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

(defn dex-out-of-bounds?
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dex-out-of-bounds? [:a :b :c] 0)
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
  (or (<  dex 0)
      (>= dex (count n))))

(defn dex-in-bounds?
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dex-out-in-bounds? [:a :b :c] 0)
  ;
  ; @example
  ; (dex-out-in-bounds? [:a :b :c] 2)
  ; =>
  ; true
  ;
  ; @example
  ; (dex-out-in-bounds? [:a :b :c] 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n dex]
  (and (>= dex 0)
       (<  dex (count n))))

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
  (if (-> n type/nonempty?)
      (-> n count dec)))

(defn next-dex
  ; @description
  ; A vektor elemeinek száma alapján meghatározza, a dex után következő indexet.
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
  (if (type/nonempty?    n)
      (sequence/next-dex dex 0 (-> n count dec))
      (return            0)))

(defn inc-dex
  ; @description
  ; A vektor elemeinek száma alapján meghatározza, a dex után következő indexet.
  ; A legmagasabb indexnél megáll.
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
  (if (dex-last? n dex)
      (return      dex)
      (inc         dex)))

(defn prev-dex
  ; @description
  ; A vektor elemeinek száma alapján meghatározza, a dex-et megelőző indexet.
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
  (if (vector?           n)
      (sequence/prev-dex dex 0 (-> n count dec))
      (return            0)))

(defn dec-dex
  ; @description
  ; A vektor elemeinek száma alapján meghatározza, a dex-et megelőző indexet.
  ; A legalacsonyabb értéknél (0) megáll.
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
  (if (dex-first? dex)
      (return     dex)
      (dec        dex)))

(defn match-dex
  ; @description
  ; A vektor elemeinek száma alapján meghatározza, hogy a dex paraméterként átadott
  ; érték a vektor hányadik indexének felelne meg ha a vektor elemeit addig ismételnénk,
  ; hogy legyen legalább annyi eleme, mint amilyen magas szám a dex értéke.
  ;
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
