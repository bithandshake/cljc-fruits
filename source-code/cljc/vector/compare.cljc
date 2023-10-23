
(ns vector.compare
    (:require [vector.check  :as check]
              [vector.remove :as remove]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn similars
  ; @param (vector) a
  ; @param (vector) b
  ;
  ; @usage
  ; (similars [:a :b :c] [:c :d :e])
  ;
  ; @example
  ; (similars [:a :b :c] [:c :d :e])
  ; =>
  ; [:c]
  ;
  ; @return (vector)
  [a b]
  (letfn [(f [result x]
             (if (check/contains-item? b x)
                 (vec (conj result x))
                 (-> result)))]
         (reduce f [] a)))

(defn contains-similars?
  ; @param (vector) a
  ; @param (vector) b
  ;
  ; @usage
  ; (contains-similars? [:a :b :c] [:c :d :e])
  ;
  ; @example
  ; (contains-similars? [:a :b :c] [:c :d :e])
  ; =>
  ; true
  ;
  ; @example
  ; (contains-similars? [:a :b :c] [:d :e :f])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [a b]
  (-> a (similars b)
        (empty?)
        (not)))

(defn difference
  ; @param (vector) a
  ; @param (vector) b
  ;
  ; @usage
  ; (difference [:a :b :c] [:b :c])
  ;
  ; @example
  ; (difference [:a :b :c] [:b :c])
  ; =>
  ; [:a]
  ;
  ; @return (vector)
  [a b]
  (remove/remove-items a b))

(defn keep-items
  ; @param (vector) n
  ; @param (vector) xyz
  ;
  ; @usage
  ; (keep-items [:a :b :c :d] [:b :c])
  ;
  ; @example
  ; (keep-items [:a :b :c :d] [:b :c])
  ; =>
  ; [:b :c]
  ;
  ; @return (vector)
  [n xyz]
  (letfn [(f [result x]
             (if (check/contains-item? xyz x)
                 (conj result x)
                 (->   result)))]
         (reduce f [] n)))

(defn keep-items-by
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (keep-items-by [:a :b "c" "d"] keyword?)
  ;
  ; @example
  ; (keep-items-by [:a :b "c" "d"] keyword?)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n f]
  (letfn [(f0 [result x]
              (if (f x)
                  (conj result x)
                  (->   result)))]
         (reduce f0 [] n)))
