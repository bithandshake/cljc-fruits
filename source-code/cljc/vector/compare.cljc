
(ns vector.compare
    (:require [candy.api     :refer [return]]
              [vector.check  :as check]
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
  (letfn [(f [o x]
             (if (check/contains-item? b x)
                 (vec (conj            o x))
                 (return               o)))]
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
  (letfn [(f [o x]
             (if (check/contains-item? xyz x)
                 (conj   o x)
                 (return o)))]
         (reduce f [] n)))
