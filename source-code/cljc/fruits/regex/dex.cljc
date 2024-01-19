
(ns fruits.regex.dex
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-dex-of
  ; @bug (#9081)
  ; - Lookaround assertions can cause incorrect result!
  ; - E.g., In the usage below, the given 'x' pattern only matches the second occurence of the number 42,
  ;         but its result is simply the number (42) that can be found at the first index also.
  ;         The 'fruits.regex.api/first-dex-of' function uses the 'fruits.string.api/first-dex-of' function to
  ;         find the the first occurence of the match, and it doesn't take lookaround assertions into account:
  ;         (fruits.string.api/first-dex-of "a42 - b42" #"(?<=b)42") => 1
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (first-dex-of "abc 123" #"[\d]+")
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
  ; @bug (#9081)
  ; Lookaround assertions can cause incorrect result!
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (last-dex-of "abc 123 def 456" #"[\d]+")
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
  ; @bug (#9081)
  ; Lookaround assertions can cause incorrect result!
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (integer) th
  ;
  ; @usage
  ; (nth-dex-of "abc 123 def 456" #"[\d]{3,}" 1)
  ; =>
  ; 12
  ;
  ; @return (integer)
  [n x th]
  (let [n (str n)
        x (re-pattern x)]
       (if (nat-int? th)
           (loop [cursor 0 skip 0]
                 (if-let [first-dex (-> n (string/keep-range cursor)
                                          (first-dex-of x))]
                         (if (= skip th)
                             (+ cursor first-dex)
                             (recur (+ first-dex cursor 1)
                                    (inc skip))))))))
