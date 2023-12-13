
(ns fruits.map.match
    (:require [fruits.map.compare :as compare]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn every-key?
  ; @description
  ; Returns TRUE if the given 'f' function returns TRUE for every key in the given 'n' map.
  ;
  ; @param (map) n
  ; @param (function) f
  ;
  ; @usage
  ; (every-key? {:a "A" :b "B"} keyword?)
  ;
  ; @example
  ; (every-key? {:a "A" :b "B"} keyword?)
  ; =>
  ; true
  ;
  ; @example
  ; (every-key? {:a "A" :b "B" "c" "C"} keyword?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n f]
  (every? (fn [[k _]] (f k)) n))

(defn any-key-matches?
  ; @description
  ; Returns TRUE if the given 'test-f' function returns TRUE for any key of the given 'n' map.
  ;
  ; @param (map) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (any-key-matches? {:a "A"} keyword?)
  ;
  ; @example
  ; (any-key-matches? {:a "A" "b" "B"} string?)
  ; =>
  ; true
  ;
  ; @example
  ; (any-key-matches? {:a "A" :b "B"} string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n test-f]
  (letfn [(f0 [%] (-> % first test-f))]
         (boolean (some f0 n))))

(defn any-value-matches?
  ; @description
  ; Returns TRUE if the given 'test-f' function returns TRUE for any value of the given 'n' map.
  ;
  ; @param (map) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (any-value-matches? {:a "A"} string?)
  ;
  ; @example
  ; (any-value-matches? {:a :A :b "B"} string?)
  ; =>
  ; true
  ;
  ; @example
  ; (any-value-matches? {:a "A" :b "B"} keyword?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n test-f]
  (letfn [(f0 [%] (-> % second test-f))]
         (boolean (some f0 n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn all-keys-match?
  ; @description
  ; Returns TRUE if the given 'test-f' function returns TRUE for all keys of the given 'n' map.
  ;
  ; @param (map) n
  ; @param (function) test-f
  ;
  ; @example
  ; (all-keys-match? {:a "A"} keyword?)
  ;
  ; @example
  ; (all-keys-match? {:a "A" :b "B"} keyword?)
  ; =>
  ; true
  ;
  ; @example
  ; (all-keys-match? {:a "A" "b" "B"} keyword?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n test-f]
  (letfn [(f0 [%] (-> % first test-f))]
         (every? f0 n)))

(defn all-values-match?
  ; @description
  ; Returns TRUE if the given 'test-f' function returns TRUE for all values of the given 'n' map.
  ;
  ; @param (map) n
  ; @param (function) test-f
  ;
  ; @example
  ; (all-values-match? {:a "A"} string?)
  ;
  ; @example
  ; (all-values-match? {:a "A" :b "B"} string?)
  ; =>
  ; true
  ;
  ; @example
  ; (all-values-match? {:a :A :b "B"} string?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n test-f]
  (letfn [(f0 [%] (-> % second test-f))]
         (every? f0 n)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-match-key
  ; @important
  ; Clojure maps are unordered data structures.
  ;
  ; @description
  ; Returns the first key of the given 'n' map for which the given 'test-f' function returns TRUE when applied on the corresponding value of the key.
  ;
  ; @param (map) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (first-match-key {:a "A"} string?)
  ;
  ; @example
  ; (first-match-key {:a "A" :b "B"} string?)
  ; =>
  ; :a
  ;
  ; @example
  ; (first-match-key {:a "A" :b "B"} keyword?)
  ; =>
  ; nil
  ;
  ; @return (*)
  [n test-f]
  (letfn [(f0 [%] (if (-> % second test-f)
                      (-> % first)))]
         (some f0 n)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-matching-key
  ; @important
  ; Clojure maps are unordered data structures.
  ;
  ; @description
  ; Returns the first key of the given 'n' map for which the given 'test-f' function returns TRUE when applied on all keys of the 'n' map.
  ;
  ; @param (map) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (first-matching-key {:a "A"} keyword?)
  ;
  ; @example
  ; (first-matching-key {:a "A" "b" "B"} string?)
  ; =>
  ; "b"
  ;
  ; @example
  ; (first-matching-key {:a "A" :b "B"} string?)
  ; =>
  ; nil
  ;
  ; @return (*)
  [n test-f]
  (letfn [(f0 [%] (if (-> % first test-f)
                      (-> % first)))]
         (some f0 n)))

(defn first-matching-value
  ; @important
  ; Clojure maps are unordered data structures.
  ;
  ; @description
  ; Returns the first value of the given 'n' map for which the given 'test-f' function returns TRUE when applied to all values of the 'n' map.
  ;
  ; @param (map) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (first-matching-value {:a "A"} string?)
  ;
  ; @example
  ; (first-matching-value {:a :A :b "B"} string?)
  ; =>
  ; "B"
  ;
  ; @example
  ; (first-matching-value {:a {:id "apple"} :b {:id "banana"}} #(= "apple" (:id %)))
  ; =>
  ; {:id "apple"}
  ;
  ; @example
  ; (first-matching-value {:a "A" :b "B"} keyword?)
  ; =>
  ; nil
  ;
  ; @return (*)
  [n test-f]
  (letfn [(f0 [%] (if (-> % second test-f)
                      (-> % second)))]
         (some f0 n)))

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
  ;
  ; @example
  ; (matches-pattern? {:a "A" :b "B"} {:a "A"})
  ; =>
  ; true
  ;
  ; @example
  ; (matches-pattern? {:a "A" :b "B"} {:a "A" :c "C"})
  ; =>
  ; false
  ;
  ; @example
  ; (matches-pattern? {:a "A" :b "B"} {:a "A"} {:strict-matching? true})
  ; =>
  ; false
  ;
  ; @example
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
