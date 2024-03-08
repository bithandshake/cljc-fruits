
(ns fruits.vector.walk
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->items
  ; @description
  ; - Applies the given 'f' function on each item of the given 'n' vector.
  ; - The 'f' function takes an item and optionally the corresponding index as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt)
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
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

  ([n f {:keys [provide-dex?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [       dex x] (if provide-dex? (f dex x) (f x)))
                (f1 [result dex x] (conj result (f0 dex x)))]
               (reduce-kv f1 [] n)))))

(defn ->items-by
  ; @description
  ; - Applies the given 'f' function on items of the given 'n' vector that for the given 'test-f' returns TRUE.
  ; - The 'f' function takes an item and optionally the corresponding index as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (map)(opt)
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
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

  ([n test-f f {:keys [provide-dex?]}]
   (let [n      (mixed/to-vector n)
         test-f (mixed/to-ifn test-f)
         f      (mixed/to-ifn f)]
        (letfn [(f0 [       dex x] (if provide-dex? (f  dex x) (f x)))
                (f1 [       dex x] (if (test-f x) (f0 dex x) x))
                (f2 [result dex x] (conj result (f1 dex x)))]
               (reduce-kv f2 [] n)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->>items
  ; @description
  ; - Applies the given 'f' function on each item of the given 'n' vector (recursivelly).
  ; - The 'f' function takes an item and optionally the corresponding index or path as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt)
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false
  ;  :provide-path? (boolean)(opt)
  ;   If TRUE, provides the corresponding path to the given 'f' function.
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

  ([n f {:keys [provide-dex? provide-path?]}]
   ; Applies the 'f' function on values of maps also, because they are equivalents to items of vectors.
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [path x] (if provide-dex? (f (last path) x) (if provide-path? (f path x) (f x))))
                (f1 [path x] (cond (vector? x) (reduce-kv #(conj  %1    (f1 (conj path %2) %3)) [] x)
                                   (map?    x) (reduce-kv #(assoc %1 %2 (f1 (conj path %2) %3)) {} x)
                                   :return     (f0 path x)))]
               (f1 [] n)))))

(defn ->>items-by
  ; @description
  ; - Applies the given 'f' function on items of the given 'n' vector (recursivelly) that for the given 'test-f' returns TRUE.
  ; - The 'f' function takes an item and optionally the corresponding index or path as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (map)(opt)
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false
  ;  :provide-path? (boolean)(opt)
  ;   If TRUE, provides the corresponding path to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->>items [0 1 2 [3 4 5]] integer? inc)
  ; =>
  ; [1 2 3 [4 5 6]]
  ;
  ; @return (vector)
  ([n test-f f]
   (->>items-by n test-f f {}))

  ([n test-f f {:keys [provide-dex? provide-path?]}]
   ; Applies the 'f' function on values of maps also, because they are equivalents to items of vectors.
   (let [n      (mixed/to-vector n)
         test-f (mixed/to-ifn test-f)
         f      (mixed/to-ifn f)]
        (letfn [(f0 [path x] (if provide-dex? (f (last path) x) (if provide-path? (f path x) (f x))))
                (f1 [path x] (if (test-f x) (f0 path x) x))
                (f2 [path x] (let [x (f1 path x)] ; <- Applies the given 'f' function (if needed) on vector and map items also.
                                  (cond (vector? x) (reduce-kv #(conj  %1    (f2 (conj path %2) %3)) [] x)
                                        (map?    x) (reduce-kv #(assoc %1 %2 (f2 (conj path %2) %3)) {} x)
                                        :return x)))]
               (f2 [] n)))))
