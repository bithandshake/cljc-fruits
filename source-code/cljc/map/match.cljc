
(ns map.match
    (:require [map.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn any-key-match?
  ; @param (map) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (any-key-match? {:a "A"} keyword?)
  ;
  ; @example
  ; (any-key-match? {:a "A" :b "B"} string?)
  ; =>
  ; false
  ;
  ; @example
  ; (any-key-match? {:a "A" "b" "B"} string?)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n test-f]
  (letfn [(f [%] (test-f (first %)))]
         (boolean (some f n))))

(defn any-value-match?
  ; @param (map) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (any-value-match? {:a "A"} string?)
  ;
  ; @example
  ; (any-value-match? {:a "A" :b "B"} keyword?)
  ; =>
  ; false
  ;
  ; @example
  ; (any-value-match? {:a :A :b "B"} string?)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n test-f]
  (letfn [(f [%] (test-f (second %)))]
         (boolean (some f n))))

(defn all-values-match?
  ; @param (map) n
  ; @param (function) test-f
  ;
  ; @example
  ; (all-values-match? {:a "A"} string?)
  ;
  ; @example
  ; (all-values-match? {:a :A :b "B"} string?)
  ; =>
  ; false
  ;
  ; @example
  ; (all-values-match? {:a "A" :b "B"} string?)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n test-f]
  (letfn [(f [%] (test-f (second %)))]
         (every? f n)))

(defn get-first-match-key
  ; @param (map) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (get-first-match-key {:a "A"} keyword?)
  ;
  ; @example
  ; (get-first-match-key {:a "A" :b "B"} string?)
  ; =>
  ; nil
  ;
  ; @example
  ; (get-first-match-key {:a "A" "b" "B"} string?)
  ; =>
  ; "b"
  ;
  ; @return (*)
  [n test-f]
  (letfn [(f [%] (if (test-f (second %)) (first %)))]
         (some f n)))

(defn get-first-match-value
  ; @param (map) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (get-first-match-value {:a "A"} string?)
  ;
  ; @example
  ; (get-first-match-value {:a "A" :b "B"} keyword?)
  ; =>
  ; nil
  ;
  ; @example
  ; (get-first-match-value {:a :A :b "B"} string?)
  ; =>
  ; "B"
  ;
  ; @example
  ; (get-first-match-value {:a {:id "apple"} :b {:id "banana"}} #(= "apple" (:id %)))
  ; =>
  ; {:id "apple"}
  ;
  ; @return (*)
  [n test-f]
  (letfn [(f [%] (if (test-f (second %)) (second %)))]
         (some f n)))

(defn match-pattern?
  ; @param (map) ns
  ; @param (map) pattern
  ; @param (map)(opt) options
  ; {:strict-matching? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (match-pattern? {:a "A" :b "B"} {:a "A"})
  ;
  ; @example
  ; (match-pattern? {:a "A" :b "B"} {:a "A"})
  ; =>
  ; true
  ;
  ; @example
  ; (match-pattern? {:a "A" :b "B"} {:a "A" :c "C"})
  ; =>
  ; false
  ;
  ; @example
  ; (match-pattern? {:a "A" :b "B"} {:a "A"} {:strict-matching? true})
  ; =>
  ; false
  ;
  ; @example
  ; (match-pattern? {:a "A" :b "B"} {:a "A" :b "B"} {:strict-matching? true})
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n pattern]
   (match-pattern? n pattern {}))

  ([n pattern {:keys [strict-matching?]}]
   (let [difference (core/difference n pattern)]
            ; If strict-matching? is true ...
        (or (and (not strict-matching?)
                 (= (count n)
                    (+ (count difference)
                       (count pattern))))
            ; If strict-matching? is false ...
            (and (boolean strict-matching?)
                 (= (count n)
                    (count pattern))
                 (empty? difference))))))
