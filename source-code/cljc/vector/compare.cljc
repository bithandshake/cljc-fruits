
(ns vector.compare
    (:require [vector.item   :as item]
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
             (if (item/contains-item? b x)
                 (conj result x)
                 (->   result)))]
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
