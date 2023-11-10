
(ns vector.item)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn last-item
  ; @param (vector) n
  ;
  ; @usage
  ; (last-item [:a :b :c])
  ;
  ; @example
  ; (last-item [:a :b :c])
  ; =>
  ; :c
  ;
  ; @return (*)
  [n]
  (last n))

(defn first-item
  ; @param (vector) n
  ;
  ; @usage
  ; (first-item [:a :b :c])
  ;
  ; @example
  ; (first-item [:a :b :c])
  ; =>
  ; :a
  ;
  ; @return (*)
  [n]
  (first n))

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
  (letfn [(f [%] (= % x))]
         (not (some f n))))

(defn only-item?
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (only-item? [:c] :c)
  ;
  ; @example
  ; (only-item? [:b] :b)
  ; =>
  ; true
  ;
  ; @example
  ; (only-item? [:a :b] :b)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (= n [x]))

(defn item-last?
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (item-last? [:a :b :c] :c)
  ;
  ; @example
  ; (item-last? [:a :b] :b)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (-> n last (= x)))

(defn item-first?
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (item-first? [:a :b :c] :a)
  ;
  ; @example
  ; (item-first? [:a :b] :a)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (-> n first (= x)))
