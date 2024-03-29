
(ns fruits.loop.walk)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn <-walk
  ; @description
  ; Iterates over the given functions and provides them the output of the previous iteration as parameter.
  ;
  ; @param (*) initial
  ; @param (list of functions) fns
  ;
  ; @usage
  ; (<-walk {:a "A"}
  ;         (fn [%] (merge {:b "B"} %))
  ;         (fn [%] (merge {:c "C"} %)))
  ; =>
  ; {:a "A" :b "B" :c "C"}
  ;
  ; @usage
  ; (<-walk [:a]
  ;         (fn [%] (conj % :b))
  ;         (fn [%] (conj % :c)))
  ; =>
  ; [:a :b :c]
  ;
  ; @usage
  ; (<-walk {:a 0}
  ;         (fn [%] (update % :a inc))
  ;         (fn [%] (update % :a inc)))
  ; =>
  ; {:a 2}
  ;
  ; @return (*)
  [initial & fns]
  (letfn [(f0 [result f] (f result))]
         (reduce f0 initial fns)))
