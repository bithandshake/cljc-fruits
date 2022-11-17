
(ns vector.check)

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

(defn min?
  ; @param (vector) n
  ; @param (integer) x
  ;
  ; @usage
  ; (min? [:a :b :c] 2)
  ;
  ; @example
  ; (min? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @example
  ; (min? [:a :b] 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (and (-> n vector?)
       (<= x (count n))))

(defn max?
  ; @param (vector) n
  ; @param (integer) x
  ;
  ; @usage
  ; (max? [:a :b :c] 2)
  ;
  ; @example
  ; (max? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @example
  ; (max? [:a :b :c :d] 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (and (-> n vector?)
       (>= x (count n))))

(defn longer?
  ; @param (vector) a
  ; @param (vector) b
  ;
  ; @usage
  ; (longer? [:a :b] [:a :b :c])
  ;
  ; @example
  ; (longer? [:a :b :c] [:a :b :c :d])
  ; =>
  ; true
  ;
  ; @example
  ; (longer? [:a :b :c :d] [:a :b])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [a b]
  (and (vector? a)
       (vector? b)
       (> (count b)
          (count a))))

(defn count?
  ; @param (vector) n
  ; @param (integer) x
  ;
  ; @usage
  ; (count? [:a :b :c] 3)
  ;
  ; @example
  ; (count? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (or (and (-> n vector?)
           (=  x (count n)))
      (and (= n [])
           (= x 0))))

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
  (= (last n) x))

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
  (= (first n) x))
