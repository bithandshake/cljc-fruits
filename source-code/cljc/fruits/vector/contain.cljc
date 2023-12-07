
(ns fruits.vector.contain)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn contains-item?
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (contains-item? [:a :b] :a)
  ;
  ; @example
  ; (contains-item? [:a :b] :a)
  ; =>
  ; true
  ;
  ; @example
  ; (contains-item? [:a :b] :c)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (letfn [(f0 [%] (= % x))]
         (boolean (some f0 n))))

(defn not-contains-item?
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (not-contains-item? [:a :b] :a)
  ;
  ; @example
  ; (not-contains-item? [:a :b] :c)
  ; =>
  ; true
  ;
  ; @example
  ; (not-contains-item? [:a :b] :a)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (letfn [(f0 [%] (not= % x))]
         (every? f0 n)))
