
(ns vector.remove
    (:require [candy.api    :refer [return]]
              [vector.check :as check]
              [vector.dex   :as dex]
              [vector.range :as range]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-first-item
  ; @param (vector) n
  ;
  ; @usage
  ; (remove-first-item [:a :b :c])
  ;
  ; @example
  ; (remove-first-item [:a :b :c :d :e])
  ; =>
  ; [:b :c :d :e]
  ;
  ; @return (vector)
  [n]
  ;(if (check/nonempty? n)
  ;    (subvec          n 1)
  ;    (return          [])])
  (vec (drop 1 n)))

(defn remove-last-item
  ; @param (vector) n
  ;
  ; @usage
  ; (remove-last-item [:a :b :c])
  ;
  ; @example
  ; (remove-last-item [:a :b :c :d :e])
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  [n]
  ;(if (check/nonempty? n)
  ;    (subvec          n 0 (-> n count dec))
  ;    (return          [])])
  (-> n drop-last vec))

(defn remove-item
  ; @param (vector) n
  ; @param (*) dex
  ;
  ; @usage
  ; (remove-item [:a :b] :b)
  ;
  ; @example
  ; (remove-item [:a :b] :b)
  ; =>
  ; [:a]
  ;
  ; @example
  ; (remove-item [:a :b :a] :a)
  ; =>
  ; [:b]
  ;
  ; @return (vector)
  [n x]
  (letfn [(f [%] (= % x))]
         (vec (remove f n))))

(defn remove-item-once
  ; @param (vector) n
  ; @param (*) dex
  ;
  ; @usage
  ; (remove-item-once [:a :b :b] :b)
  ;
  ; @example
  ; (remove-item-once [:a :b :b] :b)
  ; =>
  ; [:a :b]
  ;
  ; @example
  ; (remove-item-once [:a :b :a] :a)
  ; =>
  ; [:b :a]
  ;
  ; @return (vector)
  [n x]
  (let [count (count n)]
       (letfn [(f [dex]
                        ; Ha a dex a legmagasabb index a vektorban és a vizsgált
                        ; elem megegyezik a törlendő elemmel, ...
                  (cond (and (= dex count)
                             (= x (nth n dex)))
                        ; ... törli az utolsó elemet a vektorból.
                        (subvec n 0 (-> n count dec))
                        ; Ha a dex a legmagasabb index a vektorban és a vizsgált
                        ; elem NEM egyezik meg a törlendő elemmel, ...
                        (= dex count)
                        ; ... változtatás nélkül visszatér a vektorral.
                        (return n)
                        ; Ha NEM a dex a legmagasabb index a vektorban és a vizsgált
                        ; elem megegyezik a törlendő elemmel, ...
                        (= x (nth n dex))
                        ; ... törli az elemet a vektorból.
                        (vec (concat (subvec n 0 dex)
                                     (subvec n (inc dex))))
                        ; Ha NEM a dex a legmagasabb index a vektorban és a vizsgált
                        ; elem NEM egyezik meg a törlendő elemmel, ...
                        :else
                        (-> dex inc f)))]
              (f 0))))

(defn remove-items
  ; @param (vector) n
  ; @param (vector) xyz
  ;
  ; @usage
  ; (remove-items [:a :b :c] [:b :c])
  ;
  ; @example
  ; (remove-items [:a :b :c] [:b :c])
  ; =>
  ; [:a]
  ;
  ; @example
  ; (remove-items [:a :b :b :c ] [:b :c])
  ; =>
  ; [:a]
  ;
  ; @return (vector)
  [n xyz]
  (letfn [(f [o x]
             (if (check/contains-item? xyz x)
                 (return               o)
                 (conj                 o x)))]
         (reduce f [] n)))

(defn remove-items-by
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (remove-items-by [:a :b :c] keyword?)
  ;
  ; @example
  ; (remove-items-by [:a :b :c] keyword?)
  ; =>
  ; []
  ;
  ; @return (vector)
  [n test-f]
  (letfn [(f [o x]
             (if (test-f x)
                 (return o)
                 (conj   o x)))]
         (reduce f [] n)))

(defn remove-items-kv
  ; @param (maps in vector) n
  ; @param (*) k
  ; @param (*) v
  ;
  ; @example
  ; (remove-items-kv [{:a "a"} {:b "b"} {:c "c"}] :b "b")
  ; =>
  ; [{:a "a"} {:c "c"}]
  ;
  ; @return (maps in vector)
  [n k v]
  (letfn [(f [%] (= (k %) v))]
         (vec (remove f n))))

(defn remove-duplicates
  ; @param (vector) n
  ;
  ; @usage
  ; (remove-duplicates [:a :b :c :a])
  ;
  ; @example
  ; (remove-duplicates [:a :b :c :a])
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n]
  (letfn [(f [o x]
             (if (check/contains-item? o x)
                 (return               o)
                 (conj                 o x)))]
         (reduce f [] n)))

(defn remove-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (remove-first-occurence [:a :b :b :c] :b)
  ;
  ; @example
  ; (remove-first-occurence [:a :b :c :d :d :e] :d)
  ; =>
  ; [:a :b :c :d :e]
  ;
  ; @return (vector)
  [n x]
  ; BUG#1130
  ; A shadow-cljs a remove-first-occurence függvényt (anélkül, hogy az bárhol
  ; meghívásra kerülne), megvizsgálja és a következő hibaüzenettel tér vissza:
  ; "cljs.core/+, all arguments must be numbers, got [#{nil clj-nil} number] instead"
  ;
  ; Ez azért történik mert a dex/item-first-dex függvény egy when függvényel tér
  ; vissza, ami a feltétel nem teljesülésekor nil értéket adna vissza és a shadow-cljs
  ; megvédi az inc függvényt attól, hogy nil értéket kapjon paraméterként.
  ; Bár az inc függvény number típust vár paraméterként, ettől függetlenül ha mégis
  ; nil értéket kapna, akkor azt 1-re növelné.
  ;
  ; Az (if (number? item-dex) ...) vizsgálatot látva, a shadow-cljs van olyan kegyes
  ; és megengedi az inc függvény használatát.
  (if-let [item-dex (dex/item-first-dex n x)]
          (if (number? item-dex)
              (vec (concat (subvec n 0 item-dex)
                           (subvec n (inc item-dex)))))
          (return n)))
