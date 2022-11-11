
(ns loop.core
    (:require [candy.api :refer [param return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn reduce-kv-indexed
  ; Az f függvény harmadik paraméterként megkapja az aktuális ciklus számát
  ;
  ; @param (function) f
  ; @param (*) initial
  ; @param (map) map
  ;
  ; @usage
  ;  (reduce-kv-indexed (fn [o dex k v]) nil {})
  ;
  ; @return (*)
  [f initial map]
  (first (reduce-kv (fn [[o dex] k v]
                        [(f o dex k v)
                         (inc dex)])
                    [initial 0]
                    (param map))))

(defn reduce-indexed
  ; Az f függvény harmadik paraméterként megkapja az aktuális ciklus számát
  ;
  ; @param (function) f
  ; @param (*) initial
  ; @param (collection) coll
  ;
  ; @usage
  ;  (reduce-indexed (fn [o dex x]) nil [:a :b])
  ;
  ; @return (*)
  [f initial coll]
  (first (reduce (fn [[o dex] x]
                     [(f o dex x)
                      (inc dex)])
                 [initial 0]
                 (param coll))))

(defn some-indexed
  ; @param (function) test-f
  ; @param (collection) coll
  ;
  ; @usage
  ;  (some-indexed (fn [dex x]) [...])
  ;
  ; @example
  ;  (some-indexed #(if (= 3    %1)
  ;                     (return %2))
  ;                 [:a :b :c :d :e])
  ;  =>
  ;  :d
  ;
  ; @example
  ;  (some-indexed #(if (= :d   %2)
  ;                     (return %1))
  ;                 [:a :b :c :d :e])
  ;  =>
  ;  3
  ;
  ; @return (*)
  [test-f coll]
  (letfn [(some-indexed-f [test-f coll dex]
                          (if-let [result (test-f dex (get coll dex))]
                                  (return result)
                                  (when-not (= dex (-> coll count dec))
                                            (some-indexed-f test-f coll (inc dex)))))]
         (some-indexed-f test-f coll 0)))

(defn do-while
  ; @param (function) f
  ; @param (*) n
  ;  Az f függvény első paramétere
  ; @param (function) test-f
  ;  A teszt-függvény, aminek ha igaz a kimenete, akkor a ciklus megáll
  ;
  ; @example
  ;  (do-while (fn [{:keys [my-numbers x] :as n}]
  ;                (if (vector/contains-item? my-numbers x))
  ;                    (assoc  n :x (inc x))
  ;                    (update n :my-numbers vector/conj-item x))
  ;            {:my-numbers [0 1 2 4]
  ;             :x 0}
  ;            (fn [%] (= (count (:my-numbers %1)) 5))))
  ;  =>
  ;  {:my-numbers [0 1 2 4 3] :x 3}
  ;
  ; @example
  ;  (do-while #(inc %)
  ;             0
  ;            #(> % 3))
  ;  =>
  ;  4
  ;
  ; @return (*)
  [f n test-f]
  (let [result (f n)]
       (if (test-f result)
           (return result)
           (do-while f result test-f))))
