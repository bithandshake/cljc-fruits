
(ns fruits.vector.set
    (:require [fruits.mixed.api :as mixed]
              [fruits.noop.api  :refer [return]]))

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
  ; =>
  ; [:a :b :c :d :e :f :g :h :i]
  ;
  ; @usage
  ; (flat-items [[:a :b :c] [:d :e :f [:g :h :i]]] name)
  ; =>
  ; ["a" "b" "c" "d" "e" "f" "g" "h" "i"]
  ;
  ; @return (vector)
  ([n]
   (flat-items n return))

  ([n convert-f]
   (let [n         (mixed/to-vector n)
         convert-f (mixed/to-ifn convert-f)]
        (letfn [(f0 [result x]
                    ; Without converting the result into a vector in every iteration, conjoining would work backwards.
                    (vec (if (-> x vector?)
                             (-> result (concat (flat-items x convert-f)))
                             (-> result (conj   (convert-f  x))))))]
               (reduce f0 [] n)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn sum-items
  ; @description
  ; Sums the number type items in the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (sum-items [10 5])
  ; =>
  ; 15
  ;
  ; @return (integer)
  [n]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [sum x]
                   (if (-> x number?)
                       (-> sum (+ x))
                       (-> sum)))]
              (reduce f0 0 n))))

(defn sum-items-by
  ; @description
  ; - Sums the derived number type values of items in the given 'n' vector.
  ; - Values are derived by applying the given 'f' function on the item.
  ;
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (sum-items-by [{:value 10} {:value 5}] :value)
  ; =>
  ; 15
  ;
  ; @return (integer)
  [n f]
  (let [n (mixed/to-vector n) 
        f (mixed/to-ifn f)]
       (letfn [(f0 [sum x]
                   (let [v (f x)]
                        (if (-> v number?)
                            (+  sum v)
                            (-> sum))))]
              (reduce f0 0 n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn gap-items
  ; @description
  ; Inserts the given 'delimiter' value between every two items in the given 'n' vector.
  ;
  ; @param (*) n
  ; @param (*) delimiter
  ;
  ; @usage
  ; (gap-items [:a :b :c :d] :x)
  ; =>
  ; [:a :x :b :x :c :x :d]
  ;
  ; @return (vector)
  [n delimiter]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [result dex x]
                   (if (-> dex zero?)
                       [x]
                       (conj result delimiter x)))]
              (reduce-kv f0 [] n))))

(defn prefix-items
  ; @description
  ; Inserts the given 'prefix' item before every item in the given 'n' vector.
  ;
  ; @param (*) n
  ; @param (*) prefix
  ;
  ; @usage
  ; (prefix-items [:a :b :c :d] :x)
  ; =>
  ; [:x :a :x :b :x :c :x :d]
  ;
  ; @return (vector)
  [n prefix]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [result x] (conj result prefix x))]
              (reduce f0 [] n))))

(defn suffix-items
  ; @description
  ; Inserts the given 'suffix' item after every item in the given 'n' vector.
  ;
  ; @param (*) n
  ; @param (*) suffix
  ;
  ; @usage
  ; (suffix-items [:a :b :c :d] :x)
  ; =>
  ; [:a :x :b :x :c :x :d :x]
  ;
  ; @return (vector)
  [n suffix]
  (let [n (mixed/to-vector n)]
       (letfn [(f0 [result x] (conj result x suffix))]
              (reduce f0 [] n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn repeat-item
  ; @param (*) x
  ; @param (integer) count
  ;
  ; @usage
  ; (repeat-item :a 5)
  ; =>
  ; [:a :a :a :a :a]
  ;
  ; @return (vector)
  [x count]
  (cond (-> count integer? not) (-> [])
        :repeat-item
        (vec (repeat count x))))
