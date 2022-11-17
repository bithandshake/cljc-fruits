
(ns regex.core
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn re-match?
  ; @param (string) n
  ; @param (regex pattern) pattern
  ;
  ; @example
  ; (re-match? "123" #"^[\d]{1,}$")
  ; =>
  ; true
  ;
  ; @example
  ; (re-match? "abc" #"^[\d]{1,}$")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n pattern]
  (and (string? n)
       (some? ; Returns the match, if any, of string to pattern ...
              (re-matches pattern n))))

(defn re-mismatch?
  ; @param (string) n
  ; @param (regex pattern) pattern
  ;
  ; @example
  ; (re-mismatch? "123" #"^[\d]{1,}$")
  ; =>
  ; false
  ;
  ; @example
  ; (re-mismatch? "abc" #"^[\d]{1,}$")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n pattern]
  (or (not (string? n))
      (nil? ; Returns the match, if any, of string to pattern ...
            (re-matches pattern n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-dex-of
  ; @param (string) n
  ; @param (regex pattern) pattern
  ;
  ; @example
  ; (first-dex-of "abc 123" #"[\d]{1,}")
  ; =>
  ; 4
  ;
  ; @return (integer)
  [n pattern]
  (when (string? n)
        (if-let [match (re-find pattern n)]
                (string/first-dex-of n match))))

(defn last-dex-of
  ; @param (string) n
  ; @param (regex pattern) pattern
  ;
  ; @example
  ; (last-dex-of "abc 123 def 456" #"[\d]{1,}")
  ; =>
  ; 12
  ;
  ; @return (integer)
  [n pattern]
  (when (string? n)
        (if-let [match (re-find pattern n)]
                (string/last-dex-of n match))))

(defn nth-dex-of
  ; @param (string) n
  ; @param (regex pattern) pattern
  ;
  ; @example
  ; (nth-dex-of "abc 123 def 456" #"[\d]{1,}" 2)
  ; =>
  ; 12
  ;
  ; @return (integer)
  [n pattern dex]
  (when (and (string? n)
             (>= dex 1))
        (letfn [(f [cursor lap]
                   (if-let [first-dex (-> n (string/part  cursor)
                                            (first-dex-of pattern))]
                           (if (= lap dex)
                               (+ cursor first-dex)
                               (f (+ first-dex cursor 1)
                                  (inc lap)))))]
               (f 0 1))))
