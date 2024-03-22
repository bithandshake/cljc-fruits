
(ns fruits.vector.result
    (:require [fruits.mixed.api    :as mixed]
              [fruits.seqable.api  :as seqable]
              [fruits.vector.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-result
  ; @description
  ; Returns the first item of the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (first-result [:a :b :c "d"] keyword?)
  ; =>
  ; true
  ;
  ; @usage
  ; (first-result [:a :b :c "d"] #(if (keyword? %) (name %)))
  ; =>
  ; "a"
  ;
  ; @return (*)
  ([n f]
   (first-result n f {}))

  ([n f {:keys [provide-dex?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [dex x] (if provide-dex? (f dex x) (f x)))]
               (loop [dex 0]
                     (if (seqable/dex-in-bounds? n dex)
                         (let [x (nth n dex)]
                              (if-let [result (-> dex (f0 x))]
                                      (-> result)
                                      (-> dex inc recur)))))))))

(defn second-result
  ; @description
  ; Returns the second item of the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (second-result [:a :b :c "d"] keyword?)
  ; =>
  ; true
  ;
  ; @usage
  ; (second-result [:a :b :c "d"] #(if (keyword? %) (name %)))
  ; =>
  ; "b"
  ;
  ; @return (*)
  ([n f]
   (second-result n f {}))

  ([n f {:keys [provide-dex?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [dex x] (if provide-dex? (f dex x) (f x)))]
               (loop [dex 0 match-count 0]
                     (if (seqable/dex-in-bounds? n dex)
                         (let [x (nth n dex)]
                              (if-let [result (-> dex (f0 x))]
                                      (if (-> 1 (= match-count))
                                          (-> result)
                                          (recur (inc dex)
                                                 (inc match-count)))
                                      (recur (inc dex) match-count)))))))))

(defn last-result
  ; @description
  ; Returns the last item of the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (last-result [:a :b :c "d"] keyword?)
  ; =>
  ; true
  ;
  ; @usage
  ; (last-result [:a :b :c "d"] #(if (keyword? %) (name %)))
  ; =>
  ; "c"
  ;
  ; @return (*)
  ([n f]
   (last-result n f {}))

  ([n f {:keys [provide-dex?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [dex x] (if provide-dex? (f dex x) (f x)))]
               (loop [dex (-> n count dec)]
                     (if (seqable/dex-in-bounds? n dex)
                         (let [x (nth n dex)]
                              (if-let [result (-> dex (f0 x))]
                                      (-> result)
                                      (-> dex dec recur)))))))))

(defn nth-result
  ; @description
  ; Returns the nth item of the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (integer) th
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (nth-result [:a :b :c "d"] keyword? 1)
  ; =>
  ; true
  ;
  ; @usage
  ; (nth-result [:a :b :c "d"] #(if (keyword? %) (name %)) 1)
  ; =>
  ; "b"
  ;
  ; @return (*)
  ([n f th]
   (nth-result n f th {}))

  ([n f th {:keys [provide-dex?]}]
   (let [n  (mixed/to-vector n)
         f  (mixed/to-ifn f)
         th (mixed/to-integer th)]
        (letfn [(f0 [dex x] (if provide-dex? (f dex x) (f x)))]
               (loop [dex 0 match-count 0]
                     (if (seqable/dex-in-bounds? n dex)
                         (let [x (nth n dex)]
                              (if-let [result (-> dex (f0 x))]
                                      (if (-> th (= match-count))
                                          (-> result)
                                          (recur (inc dex)
                                                 (inc match-count)))
                                      (recur (inc dex) match-count)))))))))

(defn all-results
  ; @description
  ; Returns all items of the given 'n' vector that match the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (all-results [:a :b :c "d"] keyword?)
  ; =>
  ; [true true true]
  ;
  ; @usage
  ; (all-results [:a :b :c "d"] #(if (keyword? %) (name %)))
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @return (vector)
  ([n f]
   (all-results n f {}))

  ([n f {:keys [provide-dex?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [        dex x] (if provide-dex? (f dex x) (f x)))
                (f1 [results dex x] (if-let [result (f0 dex x)]
                                            (-> results (conj result))
                                            (-> results)))]
               (reduce-kv f1 [] n)))))

(defn result-count
  ; @description
  ; Returns how many items of the given 'n' vector match the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (result-count [:a :b "c"] keyword?)
  ; =>
  ; 2
  ;
  ; @return (integer)
  ([n f]
   (result-count n f {}))

  ([n f options]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (-> (all-results n f options) count))))
