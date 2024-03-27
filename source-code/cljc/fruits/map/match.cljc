
(ns fruits.map.match
    (:require [fruits.map.compare :as compare]
              [fruits.mixed.api   :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn any-key-matches?
  ; @description
  ; Returns TRUE if the given 'f' function returns TRUE for any key of the given 'n' map.
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (any-key-matches? {:a "A" "b" "B"} string?)
  ; =>
  ; true
  ;
  ; @usage
  ; (any-key-matches? {:a "A" :b "B"} string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n f]
   (any-key-matches? n f {}))

  ([n f {:keys [provide-value?]}]
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [[k v]] (if provide-value? (f k v) (f k)))]
               (-> (some f0 n) boolean)))))

(defn any-value-matches?
  ; @description
  ; Returns TRUE if the given 'f' function returns TRUE for any value of the given 'n' map.
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (any-value-matches? {:a :A :b "B"} string?)
  ; =>
  ; true
  ;
  ; @usage
  ; (any-value-matches? {:a "A" :b "B"} keyword?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n f]
   (any-value-matches? n f {}))

  ([n f {:keys [provide-key?]}]
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [[k v]] (if provide-key? (f k v) (f v)))]
               (-> (some f0 n) boolean)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn all-keys-match?
  ; @description
  ; Returns TRUE if the given 'f' function returns TRUE for all keys of the given 'n' map.
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (all-keys-match? {:a "A" :b "B"} keyword?)
  ; =>
  ; true
  ;
  ; @usage
  ; (all-keys-match? {:a "A" "b" "B"} keyword?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n f]
   (all-keys-match? n f {}))

  ([n f {:keys [provide-value?]}]
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [[k v]] (if provide-value? (f k v) (f k)))]
               (every? f0 n)))))

(defn all-values-match?
  ; @description
  ; Returns TRUE if the given 'f' function returns TRUE for all values of the given 'n' map.
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (all-values-match? {:a "A" :b "B"} string?)
  ; =>
  ; true
  ;
  ; @usage
  ; (all-values-match? {:a :A :b "B"} string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n f]
   (all-values-match? n f {}))

  ([n f {:keys [provide-key?]}]
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [[k v]] (if provide-key? (f k v) (f v)))]
               (every? f0 n)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not-all-keys-match?
  ; @description
  ; Returns TRUE if the given 'f' function does not return TRUE for all keys of the given 'n' map.
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (not-all-keys-match? {:a "A" "b" "B"} keyword?)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-all-keys-match? {:a "A" :b "B"} keyword?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n f]
   (not-all-keys-match? n f {}))

  ([n f options]
   (-> (all-keys-match? n f options) not)))

(defn not-all-values-match?
  ; @description
  ; Returns TRUE if the given 'f' function does not return TRUE for all values of the given 'n' map.
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (not-all-values-match? {:a "A" :b :B} string?)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-all-values-match? {:a "A" :b "B"} string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n f]
   (not-all-values-match? n f {}))

  ([n f options]
   (-> (all-values-match? n f options) not)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-matching-key
  ; @note
  ; Clojure maps are unordered data structures.
  ;
  ; @description
  ; Returns the first key of the given 'n' map for which the given 'f' function returns TRUE.
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-value? (boolean)(opt)
  ;   If TRUE, provides the corresponding value to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (first-matching-key {:a "A" "b" "B"} string?)
  ; =>
  ; "b"
  ;
  ; @usage
  ; (first-matching-key {:a "A" :b "B"} string?)
  ; =>
  ; nil
  ;
  ; @return (*)
  ([n f]
   (first-matching-key n f {}))

  ([n f {:keys [provide-value?]}]
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [[k v]] (if provide-value? (if (f k v) k) (if (f k) k)))]
               (some f0 n)))))

(defn first-matching-value
  ; @note
  ; Clojure maps are unordered data structures.
  ;
  ; @description
  ; Returns the first value of the given 'n' map for which the given 'f' function returns TRUE.
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:provide-key? (boolean)(opt)
  ;   If TRUE, provides the corresponding key to the given 'f' function.
  ;   Default: false}
  ;
  ; @usage
  ; (first-matching-value {:a :A :b "B"} string?)
  ; =>
  ; "B"
  ;
  ; @usage
  ; (first-matching-value {:a {:id "apple"} :b {:id "banana"}} #(= "apple" (:id %)))
  ; =>
  ; {:id "apple"}
  ;
  ; @usage
  ; (first-matching-value {:a "A" :b "B"} keyword?)
  ; =>
  ; nil
  ;
  ; @return (*)
  ([n f]
   (first-matching-value n f {}))

  ([n f {:keys [provide-key?]}]
   (let [n (mixed/to-map n)
         f (mixed/to-ifn f)]
        (letfn [(f0 [[k v]] (if provide-key? (if (f k v) v) (if (f v) v)))]
               (some f0 n)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn matches-pattern?
  ; @description
  ; Returns TRUE if all key-value pairs in the 'pattern' map are present in the 'n' map and, optionally, checks for strict matching.
  ;
  ; @param (map) n
  ; @param (map) pattern
  ; @param (map)(opt) options
  ; {:strict-matching? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (matches-pattern? {:a "A" :b "B"} {:a "A"})
  ; =>
  ; true
  ;
  ; @usage
  ; (matches-pattern? {:a "A" :b "B"} {:a "A" :c "C"})
  ; =>
  ; false
  ;
  ; @usage
  ; (matches-pattern? {:a "A" :b "B"} {:a "A"} {:strict-matching? true})
  ; =>
  ; false
  ;
  ; @usage
  ; (matches-pattern? {:a "A" :b "B"} {:a "A" :b "B"} {:strict-matching? true})
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n pattern]
   (matches-pattern? n pattern {}))

  ([n pattern {:keys [strict-matching?]}]
   (let [difference (compare/difference n pattern)]
        (or ; If 'strict-matching?' is TRUE ...
            (and (not strict-matching?)
                 (= (count n)
                    (+ (count difference)
                       (count pattern))))
            ; If 'strict-matching?' is FALSE ...
            (and (boolean strict-matching?)
                 (= (count n)
                    (count pattern))
                 (empty? difference))))))
