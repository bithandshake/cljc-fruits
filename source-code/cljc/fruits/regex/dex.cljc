
(ns fruits.regex.dex
    (:require [fruits.string.api :as string]))

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
  ; @BUG (#9081)
  ; Lookaround assertions can cause incorrect results!
  ; E.g., In the example below, the given 'x' pattern only matches the second occurence of the number 42,
  ;       but its result is simply the number (42) that can be found at first index also,
  ;       because the 'string/first-dex-of' function doesn't take Lookaround assertions into account:
  ;       (first-dex-of "a42 - b42" #"(?<=b)42") => 1
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
  ; @BUG (#9081)
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
  ; @BUG (#9081)
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
