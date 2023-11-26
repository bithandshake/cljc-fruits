
(ns vector.set
    (:require [noop.api :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn flat-items
  ; @description
  ; Flattens the nested vectors within the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (function)(opt) convert-f
  ; Default: return
  ;
  ; @usage
  ; (flat-items [[:a :b :c] [:d :e :f [:g :h :i]]])
  ;
  ; @example
  ; (flat-items [[:a :b :c] [:d :e :f [:g :h :i]]])
  ; =>
  ; [:a :b :c :d :e :f :g :h :i]
  ;
  ; @example
  ; (flat-items [[:a :b :c] [:d :e :f [:g :h :i]]] name)
  ; =>
  ; ["a" "b" "c" "d" "e" "f" "g" "h" "i"]
  ;
  ; @return (vector)
  ([n]
   (flat-items n return))

  ([n convert-f]
   (letfn [(f0 [result x]
               (if (-> x vector?)
                   (-> result (concat (flat-items x convert-f)))
                   (-> result (conj   (convert-f  x)))))]
          (->> n (reduce f0 []) vec))))

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
  (letfn [(f0 [sum x]
              (let [v (f x)]
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
  (letfn [(f0 [result dex x]
              (if (-> dex zero?)
                  [x]
                  (conj result delimiter x)))]
         (reduce-kv f0 [] n)))

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
