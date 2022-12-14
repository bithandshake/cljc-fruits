
(ns vector.nth
    (:require [candy.api    :refer [return]]
              [vector.check :as check]
              [vector.dex   :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn nth-item
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (nth-item [:a :b :c] 1)
  ;
  ; @example
  ; (nth-item [:a :b :c] 2)
  ; =>
  ; :c
  ;
  ; @return (*)
  [n dex]
  (when (and (vector?            n)
             (dex/dex-in-bounds? n dex))
        (nth n dex)))

(defn remove-nth-item
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (remove-nth-item [:a :b :c] 2)
  ;
  ; @example
  ; (remove-nth-item [:a :b :c :d :e] 2)
  ; =>
  ; [:a :b :d :e]
  ;
  ; @return (vector)
  [n dex]
  (when (and (vector?            n)
             (dex/dex-in-bounds? n dex))
        (vec (concat (subvec n 0 dex)
                     (subvec n (inc dex))))))

(defn remove-nth-items
  ; @param (vector) n
  ; @param (integers in vector) dexes
  ;
  ; @usage
  ; (remove-nth-item [:a :b :c] [0 2])
  ;
  ; @example
  ; (remove-nth-item [:a :b :c :d :e] [0 2])
  ; =>
  ; [:b :d :e]
  ;
  ; @return (vector)
  [n dexes]
  (letfn [(remove-nth-items-f [o dex x]
                              (if (check/contains-item? dexes dex)
                                  (return o)
                                  (conj   o x)))]
         (reduce-kv remove-nth-items-f [] n)))

(defn duplicate-nth-item
  ; @param (vector) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (duplicate-nth-item [:a :b :c] 0)
  ;
  ; @example
  ; (duplicate-nth-item [:a :b :c :d :e] 2)
  ; =>
  ; [:a :b :c :c :d :e]
  ;
  ; @return (vector)
  [n dex]
  (letfn [(f [result item-dex item]
             (if (= item-dex dex)
                 (conj result item item)
                 (conj result item)))]
         (reduce-kv f [] n)))

(defn duplicate-nth-items
  ; @param (vector) n
  ; @param (integers in vector) dexes
  ;
  ; @usage
  ; (duplicate-nth-items [:a :b :c] [0 2])
  ;
  ; @example
  ; (duplicate-nth-items [:a :b :c :d :e] [0 2])
  ; =>
  ; [:a :a :b :c :c :d :e]
  ;
  ; @return (vector)
  [n dexes])
  ; TODO

(defn replace-nth-item
  ; @param (vector) n
  ; @param (integer) dex
  ; @param (*) x
  ;
  ; @usage
  ; (replace-nth-item [:a :b :c] 0 :x)
  ;
  ; @example
  ; (replace-nth-item [:a :b :c :d] 1 :x)
  ; =>
  ; [:a :x :c :d]
  ;
  ; @example
  ; (replace-nth-item [:a :b :c :d] 999 :x)
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  [n dex x]
  (if (and (vector?            n)
           (dex/dex-in-bounds? n dex))
      (vec (concat (subvec n 0 dex)
                   [x]
                   (subvec n (inc dex))))
      (return n)))
