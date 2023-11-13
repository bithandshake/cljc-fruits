
(ns regex.dex
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-dex-of
  ; @param (*) n
  ; @param (regex pattern) pattern
  ;
  ; @example
  ; (first-dex-of "abc 123" #"[\d]{1,}")
  ; =>
  ; 4
  ;
  ; @return (integer)
  [n pattern]
  (let [n (str n)]
       (if-let [match (re-find pattern n)]
               (cond (vector? match) (string/first-dex-of n (first match))
                     (string? match) (string/first-dex-of n        match)))))

(defn last-dex-of
  ; @param (*) n
  ; @param (regex pattern) pattern
  ;
  ; @example
  ; (last-dex-of "abc 123 def 456" #"[\d]{1,}")
  ; =>
  ; 12
  ;
  ; @return (integer)
  [n pattern]
  (let [n (str n)]
       (if-let [match (re-find pattern n)]
               (cond (vector? match) (string/last-dex-of n (first match))
                     (string? match) (string/last-dex-of n        match)))))

(defn nth-dex-of
  ; @param (*) n
  ; @param (regex pattern) pattern
  ;
  ; @example
  ; (nth-dex-of "abc 123 def 456" #"[\d]{3,}" 1)
  ; =>
  ; 12
  ;
  ; @return (integer)
  [n pattern dex]
  (let [n (str n)]
       (when (>= dex 0)
             (letfn [(f [cursor skip]
                        (if-let [first-dex (-> n (string/keep-range cursor)
                                                 (first-dex-of pattern))]
                                (if (= skip dex)
                                    (+ cursor first-dex)
                                    (f (+ first-dex cursor 1)
                                       (inc skip)))))]
                    (f 0 0)))))
