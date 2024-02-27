
(ns fruits.vector.count
    (:require [fruits.mixed.api   :as mixed]
              [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-count?
  ; @description
  ; Returns TRUE if the given 'n' vector has as many items as the given 'x' value.
  ;
  ; @param (vector) n
  ; @param (integer) x
  ;
  ; @usage
  ; (item-count? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-vector n)]
       (-> n count (= x))))

(defn item-count-min?
  ; @description
  ; Returns TRUE if the given 'n' vector has at least as many items as the given 'x' value.
  ;
  ; @param (vector) n
  ; @param (integer) x
  ;
  ; @usage
  ; (item-count-min? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @usage
  ; (item-count-min? [:a :b] 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-vector n)]
       (-> n count (>= x))))

(defn item-count-max?
  ; @description
  ; Returns TRUE if the given 'n' vector has at most as many items as the given 'x' value.
  ;
  ; @param (vector) n
  ; @param (integer) x
  ;
  ; @usage
  ; (item-count-max? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @usage
  ; (item-count-max? [:a :b :c :d] 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-vector n)]
       (-> n count (<= x))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-count-same?
  ; @description
  ; Returns TRUE if the given vectors have the same item count.
  ;
  ; @param (list of vectors) abc
  ;
  ; @usage
  ; (item-count-same? [:a :b :c] [:a :b :c])
  ; =>
  ; true
  ;
  ; @usage
  ; (item-count-same? [:a :b :c] [:a :b :c :d])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [& abc]
  (let [abc (map mixed/to-vector abc)]
       (loop [sample nil dex 0]
             (cond (seqable/dex-out-of-bounds? abc dex) (-> true)
                   (-> sample nil?)                     (recur (-> abc (nth dex) count) (inc dex))
                   (-> abc (nth dex) count (= sample))  (recur (-> abc (nth dex) count) (inc dex))
                   :else false))))

(defn item-count-inc?
  ; @description
  ; Returns TRUE if the given vectors are provided in increasing order of their item count.
  ;
  ; @param (list of vectors) abc
  ;
  ; @usage
  ; (item-count-inc? [:a :b :c] [:a :b :c :d])
  ; =>
  ; true
  ;
  ; @usage
  ; (item-count-inc? [:a :b :c :d] [:a :b :c])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [& abc]
  (let [abc (map mixed/to-vector abc)]
       (loop [sample nil dex 0]
             (cond (seqable/dex-out-of-bounds? abc dex) (-> true)
                   (-> sample nil?)                     (recur (-> abc (nth dex) count) (inc dex))
                   (-> abc (nth dex) count (> sample))  (recur (-> abc (nth dex) count) (inc dex))
                   :else false))))

(defn item-count-dec?
  ; @description
  ; Returns TRUE if the given vectors are provided in decreasing order of their item count.
  ;
  ; @param (list of vectors) abc
  ;
  ; @usage
  ; (item-count-dec? [:a :b :c :d] [:a :b :c])
  ; =>
  ; true
  ;
  ; @usage
  ; (item-count-dec? [:a :b :c] [:a :b :c :d])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [& abc]
  (let [abc (map mixed/to-vector abc)]
       (loop [sample nil dex 0]
             (cond (seqable/dex-out-of-bounds? abc dex) (-> true)
                  (-> sample nil?)                     (recur (-> abc (nth dex) count) (inc dex))
                  (-> abc (nth dex) count (< sample))  (recur (-> abc (nth dex) count) (inc dex))
                  :else false))))
