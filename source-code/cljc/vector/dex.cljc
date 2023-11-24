
(ns vector.dex
    (:require [seqable.api  :as seqable]
              [vector.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-dex-of
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (first-dex-of [:a :b :a :b] :b)
  ;
  ; @example
  ; (first-dex-of [:a :b :a :b] :b)
  ; =>
  ; 1
  ;
  ; @return (integer)
  [n x]
  (if (check/nonempty? n)
      (letfn [(f0 [dex]
                  (cond (seqable/dex-out-of-bounds? n dex) (-> nil)
                        (-> x (= (nth n dex)))             (-> dex)
                        :recur                             (f0 (inc dex))))]
             (f0 0))))

(defn last-dex-of
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (last-dex-of [:a :b :a :b] :b)
  ;
  ; @example
  ; (last-dex-of [:a :b :a :b] :b)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n x]
  (if (check/nonempty? n)
      (letfn [(f0 [dex]
                  (cond (seqable/dex-out-of-bounds? n dex) (-> nil)
                        (-> x (= (nth n dex)))             (-> dex)
                        :recur                             (f0 (dec dex))))]
             (f0 (-> n count dec)))))

(defn nth-dex-of
  ; @param (vector) n
  ; @param (*) x
  ; @param (integer) th
  ;
  ; @usage
  ; (nth-dex-of [:a :b :a :b :a :b] :b 1)
  ;
  ; @example
  ; (nth-dex-of [:a :b :a :b :a :b] :b 1)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n x th]
  (if (check/nonempty? n)
      (letfn [(f0 [dex match-count]
                  (cond (seqable/dex-out-of-bounds? n dex) (-> nil)
                        (-> x (not= (nth n dex)))          (f0 (inc dex) match-count)
                        (= th (inc match-count))           (-> dex)
                        :recur-after-match                 (f0 (inc dex) (inc match-count))))]
             (f0 0 0))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-dex-by
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (first-dex-by ["a" :b :c :d] keyword?)
  ;
  ; @example
  ; (first-dex-by ["a" :b :c :d] keyword?)
  ; =>
  ; 1
  ;
  ; @return (integer)
  [n test-f]
  (if (check/nonempty? n)
      (letfn [(f0 [dex]
                  (cond (seqable/cursor-out-of-bounds? n dex) (-> nil)
                        (-> n (nth dex) test-f)               (-> dex)
                        :recur                                (-> dex inc f0)))]
             (f0 0))))

(defn last-dex-by
  ; @param (vector) n
  ; @param (function) test-f
  ;
  ; @usage
  ; (last-dex-by [:a :b :c "d"] keyword?)
  ;
  ; @example
  ; (last-dex-by [:a :b :c "b"] keyword?)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n test-f]
  (if (check/nonempty? n)
      (letfn [(f0 [dex]
                  (cond (seqable/cursor-out-of-bounds? n dex) (-> nil)
                        (-> n (nth dex) test-f)               (-> dex)
                        :recur                                (-> dex dec f0)))]
             (f0 (-> n count dec)))))

(defn nth-dex-by
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (integer) th
  ;
  ; @usage
  ; (nth-dex-by ["a" :b :c :d] keyword? 1)
  ;
  ; @example
  ; (nth-dex-by ["a" :b :c :d] keyword? 1)
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n test-f th]
  (if (check/nonempty? n)
      (letfn [(f0 [dex match-count]
                  (cond (seqable/dex-out-of-bounds? n dex) (-> nil)
                        (-> n (nth dex) test-f not)        (f0 (inc dex) match-count)
                        (= th (inc match-count))           (-> dex)
                        :recur-after-match                 (f0 (inc dex) (inc match-count))))]
             (f0 0 0))))
