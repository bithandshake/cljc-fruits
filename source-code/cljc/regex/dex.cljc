
(ns regex.dex
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-dex-of
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @example
  ; (first-dex-of "abc 123" #"[\d]{1,}")
  ; =>
  ; 4
  ;
  ; @return (integer)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (if-let [match (re-find x n)]
               (cond (vector? match) (string/first-dex-of n (first match))
                     (string? match) (string/first-dex-of n        match)))))

(defn last-dex-of
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @example
  ; (last-dex-of "abc 123 def 456" #"[\d]{1,}")
  ; =>
  ; 12
  ;
  ; @return (integer)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (if-let [match (re-find x n)]
               (cond (vector? match) (string/last-dex-of n (first match))
                     (string? match) (string/last-dex-of n        match)))))

(defn nth-dex-of
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (integer) th
  ;
  ; @example
  ; (nth-dex-of "abc 123 def 456" #"[\d]{3,}" 1)
  ; =>
  ; 12
  ;
  ; @return (integer)
  [n x th]
  (let [n (str n)
        x (re-pattern x)]
       (when (>= th 0)
             (letfn [(f [cursor skip]
                        (if-let [first-dex (-> n (string/keep-range cursor)
                                                 (first-dex-of x))]
                                (if (= skip th)
                                    (+ cursor first-dex)
                                    (f (+ first-dex cursor 1)
                                       (inc skip)))))]
                    (f 0 0)))))
