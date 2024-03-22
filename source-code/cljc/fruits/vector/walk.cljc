
(ns fruits.vector.walk
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->items
  ; @description
  ; - Applies the given 'f' function on each item of the given 'n' vector.
  ; - The 'f' function takes an item and optionally the current result and/or the corresponding index as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt)
  ; {:on-self? (boolean)(opt)
  ;   If TRUE, applies the given 'f' function on the given 'n' value also.
  ;  :provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false
  ;  :provide-result? (boolean)(opt)
  ;   If TRUE, provides the current result to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->items [:a :b :c] name)
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @return (vector)
  ([n f]
   (->items n f {}))

  ([n f {:keys [on-self? provide-dex? provide-result?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [result dex x] (if provide-result? (if provide-dex? (f result dex x) (f result x))
                                                       (if provide-dex? (f        dex x) (f        x))))
                (f1 [result dex x] (conj result (f0 result dex x)))]
               (reduce-kv f1 [] (if on-self? (f0 [] nil n)
                                             (->        n)))))))

(defn ->items-by
  ; @description
  ; - Applies the given 'f' function on items of the given 'n' vector that for the given 'test-f' returns TRUE.
  ; - The 'f' function takes an item and optionally the current result and/or the corresponding index as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (map)(opt)
  ; {:on-self? (boolean)(opt)
  ;   If TRUE, applies the given 'f' function on the given 'n' value also.
  ;  :provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false
  ;  :provide-result? (boolean)(opt)
  ;   If TRUE, provides the current result to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->items-by [0 1 2] even? inc)
  ; =>
  ; [1 1 3]
  ;
  ; @return (vector)
  ([n test-f f]
   (->items-by n test-f f {}))

  ([n test-f f {:keys [on-self? provide-dex? provide-result?]}]
   (let [n      (mixed/to-vector n)
         test-f (mixed/to-ifn test-f)
         f      (mixed/to-ifn f)]
        (letfn [(f0 [result dex x] (if provide-result? (if provide-dex? (f result dex x) (f result x))
                                                       (if provide-dex? (f        dex x) (f        x))))
                (f1 [result dex x] (if (test-f x) (f0 result dex x) x))
                (f2 [result dex x] (conj result (f1 result dex x)))]
               (reduce-kv f2 [] (if on-self? (f1 nil nil n)
                                             (->         n)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->>items
  ; @description
  ; - Applies the given 'f' function on each item of the given 'n' vector (recursivelly).
  ; - The 'f' function takes an item and optionally the current result and/or the corresponding index or path as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt)
  ; {:on-self? (boolean)(opt)
  ;   If TRUE, applies the given 'f' function on the given 'n' value also.
  ;  :provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false
  ;  :provide-path? (boolean)(opt)
  ;   If TRUE, provides the corresponding path to the given 'f' function.
  ;   Default: false
  ;  :provide-result? (boolean)(opt)
  ;   If TRUE, provides the current result to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->>items [:a :b :c [:d :e {:e :f}]] name)
  ; =>
  ; ["a" "b" "c" ["d" "e" {:e "f"}]]
  ;
  ; @return (vector)
  ([n f]
   (->>items n f {}))

  ([n f {:keys [on-self? provide-dex? provide-path? provide-result?]}]
   ; Applies the 'f' function on values of maps also, because they are equivalents to items of vectors.
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [result path x] (if provide-result? (if provide-dex? (f result (last path) x) (if provide-path? (f result path x) (f result x)))
                                                        (if provide-dex? (f        (last path) x) (if provide-path? (f        path x) (f        x)))))
                (f1 [result path x] (cond (-> on-self?)        (f0 result path x)
                                          (-> path empty? not) (f0 result path x)
                                          :return              (->             x)))
                (f2 [result path x] (cond (-> x vector?)       (reduce-kv #(conj  %1    (f2 %1 (conj path %2) %3)) [] (f1 result path x))
                                          (-> x map?)          (reduce-kv #(assoc %1 %2 (f2 %1 (conj path %2) %3)) {} (f1 result path x))
                                          :return                                                                     (f1 result path x)))]
               (f2 [] [] n)))))

(defn ->>items-by
  ; @description
  ; - Applies the given 'f' function on items of the given 'n' vector (recursivelly) that for the given 'test-f' returns TRUE.
  ; - The 'f' function takes an item and optionally the current result and/or the corresponding index or path as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (map)(opt)
  ; {:on-self? (boolean)(opt)
  ;   If TRUE, applies the given 'f' function on the given 'n' value also.
  ;  :provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false
  ;  :provide-path? (boolean)(opt)
  ;   If TRUE, provides the corresponding path to the given 'f' function.
  ;   Default: false
  ;  :provide-result? (boolean)(opt)
  ;   If TRUE, provides the current result to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->>items-by [0 1 2 [3 4 5]] integer? inc)
  ; =>
  ; [1 2 3 [4 5 6]]
  ;
  ; @return (vector)
  ([n test-f f]
   (->>items-by n test-f f {}))

  ([n test-f f {:keys [on-self? provide-dex? provide-path? provide-result?]}]
   ; Applies the 'f' function on values of maps also, because they are equivalents to items of vectors.
   (let [n      (mixed/to-vector n)
         test-f (mixed/to-ifn test-f)
         f      (mixed/to-ifn f)]
        (letfn [(f0 [result path x] (if provide-result? (if provide-dex? (f result (last path) x) (if provide-path? (f result path x) (f result x)))
                                                        (if provide-dex? (f        (last path) x) (if provide-path? (f        path x) (f        x)))))
                (f1 [result path x] (if   (-> x test-f)        (f0 result path x) x))
                (f2 [result path x] (cond (-> on-self?)        (f1 result path x)
                                          (-> path empty? not) (f1 result path x)
                                          :return              (->             x)))
                (f3 [result path x] (cond (-> x vector?)       (reduce-kv #(conj  %1    (f3 %1 (conj path %2) %3)) [] (f2 result path x))
                                          (-> x map?)          (reduce-kv #(assoc %1 %2 (f3 %1 (conj path %2) %3)) {} (f2 result path x))
                                          :return                                                                     (f2 result path x)))]
               (f3 [] [] n)))))
