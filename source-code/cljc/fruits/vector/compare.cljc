
(ns fruits.vector.compare
    (:require [fruits.vector.contain :as contain]
              [fruits.vector.remove  :as remove]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn similars
  ; @description
  ; Returns the items that are present in both the given 'a' and 'b' vectors.
  ;
  ; @param (vector) a
  ; @param (vector) b
  ;
  ; @usage
  ; (similars [:a :b :c] [:c :d :e])
  ; =>
  ; [:c]
  ;
  ; @return (vector)
  [a b]
  (let [a (mixed/to-vector a)
        b (mixed/to-vector b)]
       (letfn [(f0 [result x]
                   (if (contain/contains-item? b x)
                       (conj result x)
                       (->   result)))]
              (reduce f0 [] a))))

(defn contains-similars?
  ; @description
  ; Returns TRUE if there are items that are present in both the given 'a' and 'b' vectors.
  ;
  ; @param (vector) a
  ; @param (vector) b
  ;
  ; @usage
  ; (contains-similars? [:a :b :c] [:c :d :e])
  ; =>
  ; true
  ;
  ; @usage
  ; (contains-similars? [:a :b :c] [:d :e :f])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [a b]
  (-> (similars a b) empty? not))

(defn difference
  ; @description
  ; Returns the items of the given 'a' vector that are not present in the given 'b' vector.
  ;
  ; @param (vector) a
  ; @param (vector) b
  ;
  ; @usage
  ; (difference [:a :b :c] [:b :c])
  ; =>
  ; [:a]
  ;
  ; @return (vector)
  [a b]
  (remove/remove-items a b))
