
(ns fruits.loop.pairs)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

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
  ; Iterates over the given parameters (pair by pair) and passes them
  ; to the given 'f' function with the result of the previous iteration.
  ;
  ; @param (function) f
  ; @param (map) initial
  ; @param (list of * pairs) pairs
  ;
  ; @usage
  ; (defn my-f [n k v] (assoc n k v))
  ; (apply-pairs my-f {} :a "A" :b "B")
  ; =>
  ; {:a "A" :b "B"}
  ;
  ; @return (*)
  [f initial & pairs]
  (reduce-pairs f initial pairs))
