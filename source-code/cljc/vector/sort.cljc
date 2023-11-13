
(ns vector.sort
    (:require [vector.dex :as dex]
              [vector.nth :as nth]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn reverse-items
  ; @description
  ; Returns the given vector but its items are in reversed order.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (reverse-items [:a :b :c])
  ;
  ; @example
  ; (reverse-items [:a :b :c])
  ; =>
  ; [:c :b :a]
  ;
  ; @return (vector)
  [n]
  (-> n reverse vec))

(defn abc-items
  ; @description
  ; Returns the given vector but its items are in alphabetical order.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (abc-items [:a :d :c :b])
  ;
  ; @example
  ; (abc-items [:b "b" :a "a" nil])
  ; =>
  ; [nil "a" "b" :a :b]
  ;
  ; @return (vector)
  [n]
  (letfn [(sort-item-f [result x] (cond (string?  x) (update result :string-items     conj x)
                                        (keyword? x) (update result :keyword-items    conj x)
                                        :return      (update result :unsortable-items conj x)))
          (sort-items-f [n] (reduce sort-item-f {} n))]
         (let [{:keys [string-items keyword-items unsortable-items]} (sort-items-f n)]
              ; (sort) cannot compare string to keyword!
              (vec (concat unsortable-items (sort string-items)
                                            (sort keyword-items))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn sort-items
  ; @description
  ; Returns the given vector but its items are ordered with the given comparator function.
  ;
  ; @param (vector) n
  ; @param (function)(opt) comparator-f
  ;
  ; @usage
  ; (sort-items ["a" "c" "b"] string/abc?)
  ;
  ; @example
  ; (sort-items ["a" "c" "b"] string/abc?)
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @return (vector)
  ([n]
   (-> n sort vec))

  ([n comparator-f]
   ; XXX#0610
   ; The return value of the comparator function has to be converted to boolean!
   (letfn [(compare-f [a b] (boolean (comparator-f a b)))]
          (vec (sort compare-f n)))))

(defn items-sorted?
  ; @description
  ; Returns TRUE if the given vector's items are ordered with the given comparator function.
  ;
  ; @param (vector) n
  ; @param (function) comparator-f
  ;
  ; @usage
  ; (items-sorted? ["a" "c" "b"] string/abc?)
  ;
  ; @example
  ; (items-sorted? ["a" "c" "b"] string/abc?)
  ; =>
  ; false
  ;
  ; @example
  ; (items-sorted? ["a" "b" "c"] string/abc?)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n comparator-f]
  ; XXX#0610
  (letfn [(compare-f [a b] (boolean (comparator-f a b)))]
         (= n (sort-items n compare-f))))

(defn sort-items-by
  ; @description
  ; Returns the given vector but its items are ordered with the given comparator function
  ; that compares not the items but their versions converted by the 'convert-f' function.
  ;
  ; @param (vector) n
  ; @param (function)(opt) comparator-f
  ; @param (function) convert-f
  ;
  ; @usage
  ; (sort-items-by [{:a 3} {:a 2} {:a 1}] :a)
  ;
  ; @example
  ; (sort-items-by [{:a 3} {:a 2} {:a 1}] :a)
  ; =>
  ; [{:a 1} {:a 2} {:a 3}]
  ;
  ; @example
  ; (sort-items-by [[1 2] [2 2] [2 3]] > first)
  ; =>
  ; [[2 2] [2 3] [1 2]]
  ;
  ; @return (vector)
  ([n convert-f]
   (vec (sort-by convert-f n)))

  ([n comparator-f convert-f]
   ; XXX#0610
   (letfn [(compare-f [a b] (boolean (comparator-f a b)))]
          (vec (sort-by convert-f compare-f n)))))

(defn sort-items-by-dexes
  ; @description
  ; Returns the given vector but its items are ordered by the given index vector
  ; that tells the function which items to keep in what order.
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
  (when (and (vector? n)
             (vector? dexes))
        (letfn [(f [result dex]
                   (if-let [item (nth/nth-item n dex)]
                           (conj result item)
                           (->   result)))]
               (reduce f [] dexes))))

(defn sorted-dexes
  ; @description
  ; Takes two vectors and returns a new one that contains the positions of the second
  ; vector's items in the first vector. If an item of the second vector is not represented
  ; in the first vector, it's position won't be in the return vector.
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
  (if (and (vector? a)
           (vector? b))
      (letfn [(f [dexes x]
                 (if-let [dex (dex/first-dex-of a x)]
                         (conj dexes dex)
                         (->   dexes)))]
             (reduce f [] b))))

(defn compared-items-sorted?
  ; @description
  ; - Compares two vectors by comparing their items (at the same index) with the comparator function.
  ; - When iterating over the two vectors if items at the same index are not match, it returns the output
  ;   of the comparator function that takes that two items.
  ; - If the elements in vectors 'a' and 'b' match in value and the number of elements is the same, it returns TRUE.
  ; - If the number of elements in vector 'a' is not equal to the number of elements in vector 'b', the comparison is
  ;   performed up to the lower number of elements (if the compared elements match if vector 'a' has the smaller number
  ;   of elements it returns TRUE, otherwise it returns FALSE).
  ;
  ; @param (vector) a
  ; @param (vector) b
  ; @param (function) comparator-f
  ;
  ; @usage
  ; (compared-items-sorted? [0 1 3] [0 1 2] <)
  ;
  ; @example
  ; (compared-items-sorted? [0 1 3] [0 1 2] <)
  ; =>
  ; false
  ;
  ; @example
  ; (compared-items-sorted? [0 1 3] [0 1 4] <)
  ; =>
  ; true
  ;
  ; @example
  ; (compared-items-sorted? [0 1 3] [0 1 3] <)
  ; =>
  ; true
  ;
  ; @example
  ; (compared-items-sorted? [] [] <)
  ; =>
  ; true
  ;
  ; @example
  ; (compared-items-sorted? ["a" "b" "c"] ["d" "a"] string/abc?)
  ; =>
  ; true
  ;
  ; @example
  ; (compared-items-sorted? ["a" "b" "c"] ["a" "b"] string/abc?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [a b comparator-f]
  (let [max-count (min (count a) (count b))]
       (letfn [(f [dex]
                  (let [x (get a dex)
                        y (get b dex)]
                       (if (= x y)
                           ; If the compared items are equal ...
                           (if (= (inc dex) max-count)
                               ; If NO more items to compare (and NO difference found)...
                               (<= (count a) (count b))
                               ; If more items to compare ...
                               (f (inc dex)))
                           ; If the compared items are NOT equal ...
                           (comparator-f x y))))]
              ; Ha a max-count értéke 0, akkor mind a két vektor üres.
              (case max-count 0 (-> true)
                                (f 0)))))
