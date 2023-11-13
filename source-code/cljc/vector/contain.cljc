
(ns vector.contain)

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
  (letfn [(f [%] (= % x))]
         (boolean (some f n))))

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
  (letfn [(f [%] (not= % x))]
         (every? f n)))
