
(ns fruits.vector.sort
    (:require [fruits.mixed.api  :as mixed]
              [fruits.vector.dex :as dex]
              [fruits.seqable.api :as seqable]
              [fruits.vector.get :as get]
              [fruits.vector.walk :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn order-comparator
  ; @description
  ; Returns TRUE if the rest of the arguments are present in the given 'n' vector
  ; in the same same order as they provided.
  ;
  ; @param (vector) n
  ; @param (list of *) abc
  ;
  ; @usage
  ; (order-comparator [:a :b :c] :a :b)
  ; =>
  ; true
  ;
  ; @usage
  ; (order-comparator [:a :b :c] :c :b)
  ; =>
  ; false
  ;
  ; @usage
  ; (order-comparator [:a :b :c] :a :x)
  ; =>
  ; true
  ;
  ; @usage
  ; (order-comparator [:a :b :c] :x :a)
  ; =>
  ; false
  ;
  ; @usage
  ; (order-comparator [:a :b :c] :a :a)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n & abc]
  ; Same items aren't considerd as they are sorted, to keep their original position stable.
  ; For example, the '<' comparator function also returns false on same items.
  (let [n (mixed/to-vector n)]
       (loop [dex 0 a nil]
             (if (seqable/dex-out-of-bounds? abc dex)
                 (-> true)
                 (if-let [b (dex/first-dex-of n (nth abc dex))]
                         (cond (= dex 0) (recur (inc dex) b)
                               (< a b)   (recur (inc dex) b)
                              ;(= a b)   (recur (inc dex) b)
                               (= a b)   (-> false)
                               (> a b)   (-> false))
                         (-> a some?)))))) ; <- Sorts unknown items to the end of the order.

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn reverse-items
  ; @description
  ; Returns the given 'n' vector with its items in reversed order.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (reverse-items [:a :b :c])
  ; =>
  ; [:c :b :a]
  ;
  ; @return (vector)
  [n]
  (let [n (mixed/to-vector n)]
       (-> n reverse vec)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn abc-items
  ; @description
  ; Returns the given 'n' vector with its items in alphabetical order.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (abc-items [:e :c :a :b :d])
  ; =>
  ; [:a :b :c :d :e]
  ;
  ; @usage
  ; (abc-items [:b "b" :a "a" nil])
  ; =>
  ; [nil "a" "b" :a :b]
  ;
  ; @return (vector)
  [n]
  (let [n (mixed/to-vector n)]
       (letfn [(sort-item-f [result x] (cond (string?  x) (update result :string-items     conj x)
                                             (keyword? x) (update result :keyword-items    conj x)
                                             :return      (update result :unsortable-items conj x)))
               (sort-items-f [n] (reduce sort-item-f {} n))]
              (let [{:keys [string-items keyword-items unsortable-items]} (sort-items-f n)]
                   ; The 'sort' function cannot compare strings to keywords!
                   (vec (concat unsortable-items (sort string-items)
                                                 (sort keyword-items)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn sort-items
  ; @description
  ; Returns the given 'n' vector with its items ordered with the given comparator function.
  ;
  ; @param (vector) n
  ; @param (function)(opt) comparator-f
  ;
  ; @usage
  ; (sort-items ["a" "c" "b"] fruits.string.api/abc?)
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @return (vector)
  ([n]
   (-> n sort vec))

  ([n comparator-f]
   ; @note (#0610)
   ; The output of the given 'comparator-f' function must be converted to boolean type!
   (let [n            (mixed/to-vector n)
         comparator-f (mixed/to-ifn comparator-f)]
        (letfn [(f0 [a b] (boolean (comparator-f a b)))]
               (vec (sort f0 n))))))

(defn items-sorted?
  ; @description
  ; Returns TRUE if the given vector's items are ordered with the given comparator function.
  ;
  ; @param (vector) n
  ; @param (function) comparator-f
  ;
  ; @usage
  ; (items-sorted? ["a" "c" "b"] fruits.string.api/abc?)
  ; =>
  ; false
  ;
  ; @usage
  ; (items-sorted? ["a" "b" "c"] fruits.string.api/abc?)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n comparator-f]
  ; @note (#0610)
  (let [n            (mixed/to-vector n)
        comparator-f (mixed/to-ifn comparator-f)]
       (letfn [(f0 [a b] (boolean (comparator-f a b)))]
              (= n (sort-items n f0)))))

(defn sort-items-by
  ; @description
  ; Returns the given 'n' vector with its items ordered with the given comparator function
  ; that compares not the items but their derived values by the 'f' function.
  ;
  ; @param (vector) n
  ; @param (function)(opt) comparator-f
  ; @param (function) value-f
  ;
  ; @usage
  ; (sort-items-by [{:a 3} {:a 2} {:a 1}] :a)
  ; =>
  ; [{:a 1} {:a 2} {:a 3}]
  ;
  ; @usage
  ; (sort-items-by [[1 2] [2 2] [2 3]] > first)
  ; =>
  ; [[2 2] [2 3] [1 2]]
  ;
  ; @return (vector)
  ([n value-f]
   (let [n       (mixed/to-vector n)
         value-f (mixed/to-ifn value-f)]
        (vec (sort-by value-f n))))

  ([n comparator-f value-f]
   ; @note (#0610)
   (let [n            (mixed/to-vector n)
         comparator-f (mixed/to-ifn comparator-f)
         value-f      (mixed/to-ifn value-f)]
        (letfn [(f0 [a b] (boolean (comparator-f a b)))]
               (vec (sort-by value-f f0 n))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn sort-items-by-dexes
  ; @description
  ; Returns the given 'n' vector with its items ordered by the given index vector
  ; that describes which items to keep and in what order.
  ;
  ; @param (vector) n
  ; @param (integers in vector) dexes
  ;
  ; @usage
  ; (sort-items-by-dexes [:a :b :c] [2 0 1])
  ; =>
  ; [:c :a :b]
  ;
  ; @usage
  ; (sort-items-by-dexes [:a :b :c] [2 0])
  ; =>
  ; [:c :a]
  ;
  ; @return (vector)
  [n dexes]
  (let [n     (mixed/to-vector n)
        dexes (mixed/to-vector dexes)
        dexes (map mixed/to-integer dexes)]
       (letfn [(f0 [result dex]
                   (if-let [item (get/nth-item n dex)]
                           (-> result (conj item))
                           (-> result)))]
              (reduce f0 [] dexes))))

(defn sorted-dexes
  ; @description
  ; - Takes two vectors and returns a new one that contains the positions of the second
  ;   vector's items in the first vector.
  ; - If an item of the second vector is not present in the first vector, it's position
  ;   won't be in the return vector.
  ;
  ; @param (vector) a
  ; @param (vector) b
  ;
  ; @usage
  ; (sorted-dexes [:a :b :c] [:c :a :b])
  ; =>
  ; [2 0 1]
  ;
  ; @usage
  ; (sorted-dexes [:a :b :c] [:c :a])
  ; =>
  ; [2 0]
  ;
  ; @usage
  ; (sorted-dexes [:a :b :c] [:c :a :b :d])
  ; =>
  ; [2 0 1]
  ;
  ; @return (integers in vector)
  [a b]
  (let [a (mixed/to-vector a)
        b (mixed/to-vector b)]
       (letfn [(f0 [dexes x]
                   (if-let [dex (dex/first-dex-of a x)]
                           (conj dexes dex)
                           (->   dexes)))]
              (reduce f0 [] b))))

(defn compared-items-sorted?
  ; @description
  ; - Compares two vectors by comparing their items (at the same index) with the comparator function.
  ; - When iterating over the two vectors if items at the same index are not match, it returns the output
  ;   of the comparator function that takes that two items.
  ; - If the elements in vectors 'a' and 'b' match in value and the number of elements is the same, it returns TRUE.
  ; - If the number of elements in vector 'a' is not equal to the number of elements in vector 'b', the comparison is
  ;   performed up to the lower number of elements (if the compared elements match if vector 'a' has the smaller number
  ;   of elements it returns TRUE; otherwise, it returns FALSE).
  ;
  ; @param (vector) a
  ; @param (vector) b
  ; @param (function) comparator-f
  ;
  ; @usage
  ; (compared-items-sorted? [0 1 3] [0 1 2] <)
  ; =>
  ; false
  ;
  ; @usage
  ; (compared-items-sorted? [0 1 3] [0 1 4] <)
  ; =>
  ; true
  ;
  ; @usage
  ; (compared-items-sorted? [0 1 3] [0 1 3] <)
  ; =>
  ; true
  ;
  ; @usage
  ; (compared-items-sorted? [] [] <)
  ; =>
  ; true
  ;
  ; @usage
  ; (compared-items-sorted? ["a" "b" "c"] ["d" "a"] fruits.string.api/abc?)
  ; =>
  ; true
  ;
  ; @usage
  ; (compared-items-sorted? ["a" "b" "c"] ["a" "b"] fruits.string.api/abc?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [a b comparator-f]
  (let [a            (mixed/to-vector a)
        b            (mixed/to-vector b)
        comparator-f (mixed/to-ifn comparator-f)
        max-count    (min (count a) (count b))]
       (letfn [(f0 [dex]
                   (let [x (get a dex)
                         y (get b dex)]
                        (if (= x y)
                            ; If the compared items are equal ...
                            (if (= (inc dex) max-count)
                                ; If NO more items to compare (and NO difference found)...
                                (<= (count a) (count b))
                                ; If more items to compare ...
                                (f0 (inc dex)))
                            ; If the compared items are NOT equal ...
                            (comparator-f x y))))]
              ; If the 'max-count' value is 0 that means both vectors are empty.
              (case max-count 0 (-> true)
                                (f0 0)))))
