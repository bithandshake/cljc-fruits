
(ns fruits.vector.set
    (:require [fruits.noop.api :refer [return]]))

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

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn sum-items
  ; @description
  ; - Sums the items (if number) in the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (sum-items [10 5])
  ;
  ; @example
  ; (sum-items [10 5])
  ; =>
  ; 15
  ;
  ; @return (integer)
  [n f]
  (letfn [(f0 [sum x]
              (if (-> x number?)
                  (-> sum (+ x))
                  (-> sum)))]
         (reduce f0 0 n)))

(defn sum-items-by
  ; @description
  ; - Sums the derived values of items (if number) in the given 'n' vector.
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
                   (if (-> v number?)
                       (-> sum (+ v))
                       (-> sum))))]
         (reduce f0 0 n)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn gap-items
  ; @description
  ; Inserts the given 'delimiter' value between each two items in the given 'n' vector.
  ;
  ; @param (*) n
  ; @param (*) delimiter
  ;
  ; @usage
  ; (gap-items [:a :b :c :d] :x)
  ;
  ; @example
  ; (gap-items [:a :b :c :d] :x)
  ; =>
  ; [:a :x :b :x :c :x :d]
  ;
  ; @return (vector)
  [n delimiter]
  ; XXX#6799 (source-code/cljc/fruits/vector/core.cljc)
  (letfn [(f0 [result dex x]
              (if (-> dex zero?)
                  [x]
                  (conj result delimiter x)))]
         (vec (reduce-kv f0 [] n))))

(defn prefix-items
  ; @description
  ; Inserts the given 'prefix' item before each item in the given 'n' vector.
  ;
  ; @param (*) n
  ; @param (*) prefix
  ;
  ; @usage
  ; (prefix-items [:a :b :c :d] :x)
  ;
  ; @example
  ; (prefix-items [:a :b :c :d] :x)
  ; =>
  ; [:x :a :x :b :x :c :x :d]
  ;
  ; @return (vector)
  [n prefix]
  ; XXX#6799 (source-code/cljc/fruits/vector/core.cljc)
  (letfn [(f0 [result x] (conj result prefix x))]
         (vec (reduce f0 [] n))))

(defn suffix-items
  ; @description
  ; Inserts the given 'suffix' item after each item in the given 'n' vector.
  ;
  ; @param (*) n
  ; @param (*) suffix
  ;
  ; @usage
  ; (suffix-items [:a :b :c :d] :x)
  ;
  ; @example
  ; (suffix-items [:a :b :c :d] :x)
  ; =>
  ; [:a :x :b :x :c :x :d :x]
  ;
  ; @return (vector)
  [n suffix]
  ; XXX#6799 (source-code/cljc/fruits/vector/core.cljc)
  (letfn [(f0 [result x] (conj result x suffix))]
         (vec (reduce f0 [] n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

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
