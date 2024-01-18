
(ns fruits.vector.count
    (:require [fruits.seqable.api :as seqable]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn count?
  ; @description
  ; Returns TRUE if the given 'n' vector has as many items as the given 'x' value.
  ;
  ; @param (vector) n
  ; @param (integer) x
  ;
  ; @usage
  ; (count? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-vector n)]
       (-> n count (= x))))

(defn count-min?
  ; @description
  ; Returns TRUE if the given 'n' vector has at least as many items as the given 'x' value.
  ;
  ; @param (vector) n
  ; @param (integer) x
  ;
  ; @usage
  ; (count-min? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @usage
  ; (count-min? [:a :b] 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-vector n)]
       (-> n count (>= x))))

(defn count-max?
  ; @description
  ; Returns TRUE if the given 'n' vector has at most as many items as the given 'x' value.
  ;
  ; @param (vector) n
  ; @param (integer) x
  ;
  ; @usage
  ; (count-max? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @usage
  ; (count-max? [:a :b :c :d] 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-vector n)]
       (-> n count (<= x))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn count-same?
  ; @description
  ; Returns TRUE if the given vectors have the same item count.
  ;
  ; @param (list of vectors) abc
  ;
  ; @usage
  ; (count-same? [:a :b :c] [:a :b :c])
  ; =>
  ; true
  ;
  ; @usage
  ; (count-same? [:a :b :c] [:a :b :c :d])
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

(defn count-inc?
  ; @description
  ; Returns TRUE if the given vectors are provided in increasing order of their item count.
  ;
  ; @param (list of vectors) abc
  ;
  ; @usage
  ; (count-inc? [:a :b :c] [:a :b :c :d])
  ; =>
  ; true
  ;
  ; @usage
  ; (count-inc? [:a :b :c :d] [:a :b :c])
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

(defn count-dec?
  ; @description
  ; Returns TRUE if the given vectors are provided in decreasing order of their item count.
  ;
  ; @param (list of vectors) abc
  ;
  ; @usage
  ; (count-dec? [:a :b :c :d] [:a :b :c])
  ; =>
  ; true
  ;
  ; @usage
  ; (count-dec? [:a :b :c] [:a :b :c :d])
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
