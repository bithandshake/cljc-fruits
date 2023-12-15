
(ns fruits.vector.result
    (:require [fruits.vector.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-result
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (first-result [:a :b :c "d"] keyword?)
  ;
  ; @example
  ; (first-result [:a :b :c "d"] keyword?)
  ; =>
  ; true
  ;
  ; @example
  ; (first-result [:a :b :c "d"] #(if (keyword? %) (name %)))
  ; =>
  ; "a"
  ;
  ; @return (*)
  [n f]
  (if (check/nonempty? n)
      (loop [dex 0]
            (if-let [result (-> n (nth dex) f)]
                    (-> result)
                    (if (-> dex (< (-> n count dec)))
                        (-> dex inc recur))))))

(defn last-result
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (last-result [:a :b :c "d"] keyword?)
  ;
  ; @example
  ; (last-result [:a :b :c "d"] keyword?)
  ; =>
  ; true
  ;
  ; @example
  ; (last-result [:a :b :c "d"] #(if (keyword? %) (name %)))
  ; =>
  ; "c"
  ;
  ; @return (*)
  [n f]
  (if (check/nonempty? n)
      (loop [dex (-> n count dec)]
            (if-let [result (-> n (nth dex) f)]
                    (-> result)
                    (if (-> dex (not= 0))
                        (-> dex dec recur))))))

(defn nth-result
  ; @param (vector) n
  ; @param (function) f
  ; @param (integer) th
  ;
  ; @usage
  ; (nth-result [:a :b :c "d"] keyword? 1)
  ;
  ; @example
  ; (nth-result [:a :b :c "d"] keyword? 1)
  ; =>
  ; true
  ;
  ; @example
  ; (nth-result [:a :b :c "d"] #(if (keyword? %) (name %)) 1)
  ; =>
  ; "b"
  ;
  ; @return (*)
  [n f th]
  (if (and (check/nonempty? n)
           (nat-int? th))
      (loop [dex 0 match-count 0]
            (if-let [result (-> n (nth dex) f)]
                    (cond (-> th  (= match-count))
                          (-> result)
                          (-> dex (< (-> n count dec)))
                          (recur (inc dex)
                                 (inc match-count)))
                    (cond (-> dex (< (-> n count dec)))
                          (recur (inc dex) match-count))))))
