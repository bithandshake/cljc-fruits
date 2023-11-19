
(ns vector.set)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn sum-items-by
  ; @description
  ; - Sums the derived values of items in the given 'n' vector.
  ; - Values are derived by applying the given 'f' function on the item.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (sum-items-by [{:value 10} {:value 5}] :value)
  ;
  ; @example
  ; (sum-items-by [{:value 10} {:value 5}] :value)
  ; =>
  ; 15
  ;
  ; @return (integer)
  [n f]
  (letfn [(f0 [sum x] (let [v (f x)]
                           (if (-> v integer?)
                               (-> sum (+ v))
                               (-> sum))))]
         (reduce f0 0 n)))

(defn gap-items
  ; @param (*) n
  ; @param (*) delimiter
  ;
  ; @usage
  ; (gap-items [:A :B] :x)
  ;
  ; @example
  ; (gap-items [:A :B :C :D] :x)
  ; =>
  ; [:A :x :B :x :C :x :D]
  ;
  ; @return (vector)
  [n delimiter]
  (letfn [(f [result dex x]
             (if (-> dex zero?)
                 [x]
                 (conj result delimiter x)))]
         (reduce-kv f [] n)))

(defn repeat-item
  ; @param (*) n
  ; @param (integer) count
  ;
  ; @usage
  ; (repeat-item :a 5)
  ;
  ; @example
  ; (repeat-item :a 5)
  ; =>
  ; [:a :a :a :a :a]
  ;
  ; @return (vector)
  [n count]
  (vec (repeat count n)))
