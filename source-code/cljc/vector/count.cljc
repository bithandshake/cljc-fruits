
(ns vector.count)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

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
       (-> n count (>= x))))

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
       (-> n count (<= x))))

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
  (and (-> a vector?)
       (-> b vector?)
       (> (-> b count)
          (-> a count))))

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
