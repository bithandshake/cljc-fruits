
(ns vector.nth
    (:require [seqable.api    :as seqable]
              [vector.contain :as contain]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn nth-item
  ; @param (vector) n
  ; @param (integer) th
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
  [n th]
  (if (vector? n)
      (let [th (seqable/normalize-dex n th)]
           (nth n th))))

(defn remove-nth-item
  ; @param (vector) n
  ; @param (integer) th
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
  [n th]
  (if (vector? n)
      (let [th (seqable/normalize-dex n th)]
           (vec (concat (subvec n 0 th)
                        (subvec n (inc th)))))))

(defn remove-nth-items
  ; @param (vector) n
  ; @param (integers in vector) ths
  ;
  ; @usage
  ; (remove-nth-items [:a :b :c] [0 2])
  ;
  ; @example
  ; (remove-nth-items [:a :b :c :d :e] [0 2])
  ; =>
  ; [:b :d :e]
  ;
  ; @return (vector)
  [n ths]
  (if (vector? n)
      (letfn [(f0 [result dex x]
                  (if (contain/contains-item? ths dex)
                      (->   result)
                      (conj result x)))]
             (reduce-kv f0 [] n))))

(defn duplicate-nth-item
  ; @param (vector) n
  ; @param (integer) th
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
  [n th]
  (letfn [(f0 [result dex item]
              (if (= dex th)
                  (conj result item item)
                  (conj result item)))]
         (reduce-kv f0 [] n)))

(defn duplicate-nth-items
  ; @param (vector) n
  ; @param (integers in vector) ths
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
  [n ths])
  ; TODO

(defn replace-nth-item
  ; @param (vector) n
  ; @param (integer) th
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
  [n th x]
  (if (vector? n)
      (let [th (seqable/normalize-dex n th)]
           (vec (concat (subvec n 0 th)
                        [x]
                        (subvec n (inc th)))))))
