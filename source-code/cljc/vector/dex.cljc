
(ns vector.dex
    (:require [vector.check :as check]))

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
                  (cond (-> x (= (nth n dex)))
                        (-> dex)
                        (-> dex (< (-> n count dec)))
                        (f0 (inc dex))))]
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
                  (cond (-> x (= (nth n dex)))
                        (-> dex)
                        (-> dex (not= 0))
                        (f0 (dec dex))))]
             (f0 (-> n count dec)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-dex-by
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (first-dex-by [:a :b :a :b] #(= % :b))
  ;
  ; @example
  ; (first-dex-by [:a :b :a :b] #(= % :b))
  ; =>
  ; 1
  ;
  ; @return (integer)
  [n f]
  (if (check/nonempty? n)
      (letfn [(f0 [dex]
                  (cond (-> n (nth dex) f)
                        (-> dex)
                        (-> dex (< (-> n count dec)))
                        (-> dex inc f0)))]
             (f0 0))))

(defn last-dex-by
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (last-dex-by [:a :b :a :b] #(= % :b))
  ;
  ; @example
  ; (last-dex-by [:a :b :a :b] #(= % :b))
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n f]
  (if (check/nonempty? n)
      (letfn [(f0 [dex]
                  (cond (-> n (nth dex) f)
                        (-> dex)
                        (-> dex (not= 0))
                        (-> dex dec f0)))]
             (f0 (-> n count dec)))))
