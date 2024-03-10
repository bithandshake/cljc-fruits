
(ns fruits.vector.match
    (:require [fruits.mixed.api    :as mixed]
              [fruits.seqable.api  :as seqable]
              [fruits.vector.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn any-item-matches?
  ; @description
  ; Returns TRUE if any item in the given 'n' vector matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (any-item-matches? [:a "b" :c] string?)
  ; =>
  ; true
  ;
  ; @usage
  ; (any-item-matches? [:a :b :c] string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n f]
   (any-item-matches? n f {}))

  ([n f {:keys [provide-dex?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [dex x] (if provide-dex? (f dex x) (f x)))]
               (loop [dex 0]
                     (if (seqable/dex-in-bounds? n dex)
                         (let [x (nth n dex)]
                              (if (-> dex (f0 x))
                                  (-> true)
                                  (-> dex inc recur)))))))))

(defn no-item-matches?
  ; @description
  ; Returns TRUE if no item in the given 'n' vector matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (no-item-matches? [:a :b :c] string?)
  ; =>
  ; true
  ;
  ; @usage
  ; (no-item-matches? [:a "b" :c] string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n f]
   (no-item-matches? n f {}))

  ([n f options]
   (-> (any-item-matches? n f options) not)))

(defn all-items-match?
  ; @description
  ; Returns TRUE if every item in the given 'n' vector matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (all-items-match? ["a" "b" "c"] string?)
  ; =>
  ; true
  ;
  ; @usage
  ; (all-items-match? [:a "b" "c"] string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n f]
   (all-items-match? n f {}))

  ([n f {:keys [provide-dex?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [dex x] (if provide-dex? (f dex x) (f x)))]
               (loop [dex 0]
                     (or (seqable/dex-out-of-bounds? n dex)
                         (let [x (nth n dex)]
                              (if (-> dex (f0 x) not)
                                  (-> false)
                                  (-> dex inc recur)))))))))

(defn not-all-items-match?
  ; @description
  ; Returns TRUE if not every item in the given 'n' vector matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (not-all-items-match? [:a "b" "c"] string?)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-all-items-match? ["a" "b" "c"] string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n f]
   (not-all-items-match? n f {}))

  ([n f options]
   (-> (all-items-match? n f options) not)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-match
  ; @description
  ; Returns the first item in the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (first-match [:a :b :c "d"] keyword?)
  ; =>
  ; :a
  ;
  ; @return (*)
  ([n f]
   (first-match n f {}))

  ([n f {:keys [provide-dex?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [dex x] (if provide-dex? (f dex x) (f x)))]
               (loop [dex 0]
                     (if (seqable/dex-in-bounds? n dex)
                         (let [x (nth n dex)]
                              (cond (-> dex (f0 x)) (-> x)
                                    :recur          (-> dex inc recur)))))))))

(defn second-match
  ; @description
  ; Returns the second item in the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (second-match [:a :b :c "d"] keyword?)
  ; =>
  ; :b
  ;
  ; @return (*)
  ([n f]
   (second-match n f {}))

  ([n f {:keys [provide-dex?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [dex x] (if provide-dex? (f dex x) (f x)))]
               (loop [dex 0 match-count 0]
                     (if (seqable/dex-in-bounds? n dex)
                         (let [x (nth n dex)]
                              (cond (-> dex (f0 x) not)    (-> dex inc (recur match-count))
                                    (-> 2 (= match-count)) (-> x)
                                    :recur-after-match     (-> dex inc (recur (inc match-count)))))))))))

(defn last-match
  ; @description
  ; Returns the last item in the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (last-match [:a :b :c "d"] keyword?)
  ; =>
  ; :c
  ;
  ; @return (*)
  ([n f]
   (last-match n f {}))

  ([n f {:keys [provide-dex?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [dex x] (if provide-dex? (f dex x) (f x)))]
               (loop [dex (-> n count dec)]
                     (if (seqable/dex-in-bounds? n dex)
                         (let [x (nth n dex)]
                              (cond (-> dex (f0 x)) (-> x)
                                    :recur          (-> dex dec recur)))))))))

(defn nth-match
  ; @description
  ; Returns the nth item in the given 'n' vector that matches the given 'f' predicate function.
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
  ; (nth-match [:a :b :c "d"] keyword? 1)
  ; =>
  ; :b
  ;
  ; @return (*)
  ([n f th]
   (nth-match n f th {}))

  ([n f th {:keys [provide-dex?]}]
   (let [n  (mixed/to-vector n)
         f  (mixed/to-ifn f)
         th (mixed/to-integer th)]
        (letfn [(f0 [dex x] (if provide-dex? (f dex x) (f x)))]
               (loop [dex 0 match-count 0]
                     (if (seqable/dex-in-bounds? n dex)
                         (let [x (nth n dex)]
                              (cond (-> dex (f0 x) not)     (-> dex inc (recur match-count))
                                    (-> th (= match-count)) (-> x)
                                    :recur-after-match      (-> dex inc (recur (inc match-count)))))))))))

(defn all-matches
  ; @description
  ; Returns all items in the given 'n' vector that matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (all-matches [:a :b :c "d"] keyword?)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  ([n f]
   (all-matches n f {}))

  ([n f {:keys [provide-dex?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [        dex x] (if provide-dex? (f dex x) (f x)))
                (f1 [matches dex x] (if (-> dex (f0 x))
                                        (-> matches (conj x))
                                        (-> matches)))]
               (reduce-kv f1 [] n)))))

(defn match-count
  ; @description
  ; Returns how many items in the given 'n' vector matches the given 'f' predicate function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-dex? (boolean)(opt)
  ;   If TRUE, provides the corresponding index to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (match-count [:a :b "c"] keyword?)
  ; =>
  ; 2
  ;
  ; @return (integer)
  ([n f]
   (match-count n f {}))

  ([n f options]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn f)]
        (-> (all-matches n f options) count))))
