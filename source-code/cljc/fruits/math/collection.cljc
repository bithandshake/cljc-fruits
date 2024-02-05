
(ns fruits.math.collection
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn collection-average
  ; @description
  ; Returns the average of the number type items in the given 'n' collection.
  ;
  ; @param (seqable) n
  ;
  ; @usage
  ; (collection-average [100 14 3 55])
  ; =>
  ; 43
  ;
  ; @usage
  ; (collection-average ["0" 1 "a" nil])
  ; =>
  ; 0.5
  ;
  ; @usage
  ; (collection-average ["0" "a"])
  ; =>
  ; 0
  ;
  ; @return (number)
  [n]
  (let [n (mixed/to-seqable n)]
       (letfn [(f0 [[sum count] x]
                   (if (number? x)
                       [(+ sum x) (inc count)]
                       [sum count]))]
              (let [[sum count] (reduce f0 [0 0] n)]
                   (/ sum count)))))

(defn collection-minimum
  ; @description
  ; Returns the minimum of the number type items in the given 'n' collection.
  ;
  ; @param (seqable) n
  ;
  ; @usage
  ; (collection-minimum [100 14 3 55])
  ; =>
  ; 3
  ;
  ; @usage
  ; (collection-minimum ["0" 1 "a" nil])
  ; =>
  ; 1
  ;
  ; @usage
  ; (collection-minimum ["0" "a"])
  ; =>
  ; nil
  ;
  ; @return (number)
  [n]
  (let [n (mixed/to-seqable n)]
       (->> n (filter number?)
              (apply min))))

(defn collection-maximum
  ; @description
  ; Returns the maximum of the number type items in the given 'n' collection.
  ;
  ; @param (seqable) n
  ;
  ; @usage
  ; (collection-maximum [100 14 3 55])
  ; =>
  ; 100
  ;
  ; @usage
  ; (collection-maximum ["0" 1 "a" nil])
  ; =>
  ; 1
  ;
  ; @usage
  ; (collection-maximum ["0" "a"])
  ; =>
  ; nil
  ;
  ; @return (number)
  [n]
  (let [n (mixed/to-seqable n)]
       (->> n (filter number?)
              (apply max))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn average
  ; @description
  ; Returns the average of the given number type parameters.
  ;
  ; @param (list of numbers) abc
  ;
  ; @usage
  ; (average 100 30 20)
  ; =>
  ; 50
  ;
  ; @return (number)
  [& abc]
  (collection-average abc))

(defn minimum
  ; @description
  ; Returns the minimum of the given number type parameters.
  ;
  ; @param (list of numbers) abc
  ;
  ; @usage
  ; (minimum -4.20 2 0)
  ; =>
  ; 2
  ;
  ; @return (number)
  [& abc]
  (collection-minimum abc))

(defn maximum
  ; @description
  ; Returns the maximum of the given number type parameters.
  ;
  ; @param (list of numbers) abc
  ;
  ; @usage
  ; (maximum -4.20 2 0)
  ; =>
  ; 2
  ;
  ; @return (number)
  [& abc]
  (collection-maximum abc))
