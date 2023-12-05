
(ns vector.walk)

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
  ;   If TRUE, provides the corresponding index also to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->items [0 1 2] inc)
  ;
  ; @example
  ; (->items [:a :b :c] name)
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @return (vector)
  ([n f]
   (->items n f {}))

  ([n f {:keys [provide-dex?]}]
   (letfn [(f0 [  dex x] (if provide-dex? (f dex x) (f x)))
           (f1 [r dex x] (conj r (f0 dex x)))]
          (reduce-kv f1 [] n))))

(defn ->items-indexed
  ; @description
  ; - Applies the given 'f' function on each item of the given 'n' vector.
  ; - The 'f' function takes an item and the corresponding index as parameters.
  ;
  ; @param (map) n
  ; @param (function) f
  ;
  ; @usage
  ; (->items-indexed [0 1 2] (fn [dex %] (inc %)))
  ;
  ; @example
  ; (->items-indexed [:a :b :c] (fn [dex %] (name %)))
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @return (vector)
  [n f]
  (->items n f {:provide-dex? true}))

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
  ;   If TRUE, provides the corresponding index also to the given 'f' function.
  ;   Default: false
  ;  :provide-path? (boolean)(opt)
  ;   If TRUE, provides the corresponding path also to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (->>items [0 1 2 [3 4 {:x 6}]] inc)
  ;
  ; @example
  ; (->>items [:a :b :c [:d :e {:e :f}]] name)
  ; =>
  ; ["a" "b" "c" ["d" "e" {:e "f"}]]
  ;
  ; @return (vector)
  ([n f]
   (->>items n f {}))

  ([n f {:keys [provide-dex? provide-path?]}]
   ; Applies the 'f' function on values of maps also, because they are equivalents to items in vectors.
   (letfn [(f0 [p x] (if provide-dex? (f (last p) x) (if provide-path? (f p x) (f x))))
           (f1 [p x] (cond (vector? x) (reduce-kv #(conj  %1    (f1 (conj p %2) %3)) [] x)
                           (map?    x) (reduce-kv #(assoc %1 %2 (f1 (conj p %2) %3)) {} x)
                           :return     (f0 p x)))]
          (f1 [] n))))

(defn ->>items-indexed
  ; @description
  ; - Applies the given 'f' function on each item of the given 'n' vector (recursivelly).
  ; - The 'f' function takes an item and the corresponding index as parameter(s).
  ;
  ; @param (map) n
  ; @param (function) f
  ;
  ; @usage
  ; (->>items-indexed [0 1 2 [3 4 {:x 6}]] (fn [dex %] (inc %)))
  ;
  ; @example
  ; (->>items-indexed [:a :b :c [:d :e {:e :f}]] (fn [dex %] (name %)))
  ; =>
  ; ["a" "b" "c" ["d" "e" {:e "f"}]]
  ;
  ; @return (vector)
  [n f]
  (->>items n f {:provide-dex? true}))
