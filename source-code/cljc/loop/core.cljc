
(ns loop.core
    (:require [candy.api :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn <-walk
  ; @description
  ; Takes the 'n' as initial value and iterates over the list of functions.
  ; Every function takes the previous function's result as an only argument.
  ;
  ; @param (*) n
  ; @param (list of functions) fs
  ;
  ; @usage
  ; (<-walk {...}
  ;         (fn [%] %)
  ;         (fn [%] %))
  ;
  ; @example
  ; (<-walk {:a "A"}
  ;         (fn [%] (merge {:b "B"} %))
  ;         (fn [%] (merge {:c "C"} %)))
  ; =>
  ; {:a "A" :b "B" :c "C"}
  ;
  ; @example
  ; (<-walk [:a]
  ;         (fn [%] (conj % :b))
  ;         (fn [%] (conj % :c)))
  ; =>
  ; [:a :b :c]
  ;
  ; @return (*)
  [n & fs]
  (letfn [(f [result f] (f result))]
         (reduce f n fs)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn reduce-kv-indexed
  ; @description
  ; The 'f' function gets the current item's index as its second parameter.
  ;
  ; @param (function) f
  ; @param (*) initial
  ; @param (map) map
  ;
  ; @usage
  ; (reduce-kv-indexed (fn [o dex k v]) nil {})
  ;
  ; @return (*)
  [f initial map]
  (letfn [(fi [[o dex] k v]
              [(f o dex k v)
               (inc dex)])]
         (first (reduce-kv fi [initial 0] map))))

(defn reduce-indexed
  ; @description
  ; The 'f' function gets the current item's index as its second parameter.
  ;
  ; @param (function) f
  ; @param (*) initial
  ; @param (collection) coll
  ;
  ; @usage
  ; (reduce-indexed (fn [o dex x]) nil [:a :b])
  ;
  ; @return (*)
  [f initial coll]
  (reduce-kv f initial coll))

(defn some-indexed
  ; @param (function) test-f
  ; @param (collection) coll
  ;
  ; @usage
  ; (some-indexed (fn [dex x]) [...])
  ;
  ; @example
  ; (some-indexed #(if (= 3    %1)
  ;                    (return %2))
  ;                [:a :b :c :d :e])
  ; =>
  ; :d
  ;
  ; @example
  ; (some-indexed #(if (= :d   %2)
  ;                    (return %1))
  ;                [:a :b :c :d :e])
  ; =>
  ; 3
  ;
  ; @return (*)
  [test-f coll]
  (letfn [(fi [test-f coll dex]
              (if-let [result (test-f dex (get coll dex))]
                      (return result)
                      (when-not (= dex (-> coll count dec))
                                (fi test-f coll (inc dex)))))]
         (fi test-f coll 0)))

(defn do-while
  ; @param (function) f
  ; @param (*) n
  ; The initial parameter of the 'f' function.
  ; @param (function) test-f
  ; If the 'test-f' functions returns with true the iteration stops.
  ;
  ; @example
  ; (do-while (fn [{:keys [my-numbers x] :as n}]
  ;               (if (vector/contains-item? my-numbers x)
  ;                   (assoc  n :x (inc x))
  ;                   (update n :my-numbers vector/conj-item x)))
  ;           {:my-numbers [0 1 2 4]
  ;            :x 0}
  ;           (fn [%] (= (count (:my-numbers %1)) 5)))
  ; =>
  ; {:my-numbers [0 1 2 4 3] :x 3}
  ;
  ; @example
  ; (do-while #(inc %)
  ;            0
  ;           #(> % 3))
  ; =>
  ; 4
  ;
  ; @return (*)
  [f n test-f]
  (let [result (f n)]
       (if (test-f     result)
           (return     result)
           (do-while f result test-f))))
