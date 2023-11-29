
(ns loop.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn <-walk
  ; @description
  ; Iterates over the given functions and provides them the previous iteration's output as parameter.
  ;
  ; @param (*) initial
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
  ; @example
  ; (<-walk {:a 0}
  ;         (fn [%] (update % :a inc))
  ;         (fn [%] (update % :a inc)))
  ; =>
  ; {:a 2}
  ;
  ; @return (*)
  [initial & fs]
  (letfn [(f0 [result f] (f result))]
         (reduce f0 initial fs)))

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
  ; (reduce-kv-indexed (fn [result dex k v]) nil {})
  ;
  ; @return (*)
  [f initial map]
  (letfn [(f0 [[result dex] k v]
              [(f result dex k v)
               (inc dex)])]
         (first (reduce-kv f0 [initial 0] map))))

(defn reduce-range
  ; @param (function) f
  ; @param (*) initial
  ; @param (integer)(opt) start
  ; @param (integer) end
  ;
  ; @usage
  ; (reduce-range (fn [result x]) nil 10)
  ;
  ; @usage
  ; (reduce-range (fn [result x]) nil 10 20)
  ;
  ; @return (*)
  ([f initial end]
   (reduce f initial (range end)))

  ([f initial start end]
   (reduce f initial (range start end))))

(defn reduce-indexed
  ; @description
  ; The 'f' function gets the current item's index as its second parameter.
  ;
  ; @param (function) f
  ; @param (*) initial
  ; @param (collection) coll
  ;
  ; @usage
  ; (reduce-indexed (fn [result dex x]) nil [:a :b])
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
  (letfn [(f0 [test-f coll dex]
              (if-let [result (test-f dex (get coll dex))]
                      (-> result)
                      (when-not (= dex (-> coll count dec))
                                (f0 test-f coll (inc dex)))))]
         (f0 test-f coll 0)))

(defn do-indexed
  ; @param (function) do-f
  ; @param (collection) coll
  ;
  ; @usage
  ; (do-indexed (fn [dex x]) [...])
  ;
  ; @usage
  ; (do-indexed (fn [dex x] (println x "is the" dex "th item of the collection"))
  ;             [:a :b :c :d :e])
  [do-f coll]
  (letfn [(f0 [dex x]
              (do-f dex x)
              (inc  dex))]
         (reduce f0 0 coll)
         (-> nil)))

(defn do-while
  ; @param (function) f
  ; @param (*) n
  ; The initial parameter of the 'f' function.
  ; @param (function) test-f
  ; If the 'test-f' functions returns true the iteration stops.
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
  ;           0
  ;           #(> % 3))
  ; =>
  ; 4
  ;
  ; @return (*)
  [f n test-f]
  (let [result (f n)]
       (if (-> result test-f)
           (-> result)
           (do-while f result test-f))))

(defn reduce-pairs
  ; @description
  ; Iterates over (pair by pair) the given collection (must contain even number of items)
  ; and passes them to the given 'f' function with the result of the previous iteration.
  ;
  ; @param (function) f
  ; @param (map) initial
  ; @param (collection with even number of items) pairs
  ;
  ; @usage
  ; (defn my-f [n k v] ...)
  ; (reduce-pairs my-f {} [:a "A" :b "B"])
  ;
  ; @example
  ; (defn my-f [n k v] (assoc n k v))
  ; (reduce-pairs my-f {} [:a "A" :b "B"])
  ; =>
  ; {:a "A" :b "B"}
  ;
  ; @return (*)
  [f initial pairs]
  (let [pairs-count (count pairs)]
       (letfn [(f0 [result lap] (let [cursor (* lap 2)]
                                     (if (> cursor pairs-count) result
                                         (let [a (nth pairs (- cursor 2))
                                               b (nth pairs (- cursor 1))]
                                              (f0 (f result a b)
                                                  (inc lap))))))]

              ; ...
              (cond (-> pairs-count (< 2)) initial
                    (-> pairs-count odd?)  initial
                    :recursion (f0 initial 1)))))

(defn apply-pairs
  ; @description
  ; Iterates over the given parameter pairs (pair by pair) and passes them
  ; to the given 'f' function with the result of the previous iteration.
  ;
  ; @param (function) f
  ; @param (map) initial
  ; @param (list of * pairs) pairs
  ;
  ; @usage
  ; (defn my-f [n k v] ...)
  ; (apply-pairs my-f {} :a "A" :b "B")
  ;
  ; @example
  ; (defn my-f [n k v] (assoc n k v))
  ; (apply-pairs my-f {} :a "A" :b "B")
  ; =>
  ; {:a "A" :b "B"}
  ;
  ; @return (*)
  [f initial & pairs]
  (reduce-pairs f initial pairs))
