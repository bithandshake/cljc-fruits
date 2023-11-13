
(ns string.dex
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-dex-of
  ; @description
  ; Returns the index of the first occurence of the given 'x' value (converted to string)
  ; in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (*) x
  ;
  ; @usage
  ; (first-dex-of "abc abc" "a")
  ;
  ; @example
  ; (first-dex-of "abc abc" "a")
  ; =>
  ; 0
  ;
  ; @example
  ; (first-dex-of "abc abc" "")
  ; =>
  ; 0
  ;
  ; @example
  ; (first-dex-of "abc abc" nil)
  ; =>
  ; 0
  ;
  ; @example
  ; (first-dex-of "abc abc" "d")
  ; =>
  ; nil
  ;
  ; @example
  ; (first-dex-of [[]] "]")
  ; =>
  ; 2
  ;
  ; @example
  ; (first-dex-of "abc - abc" "a")
  ; =>
  ; 6
  ;
  ; @return (integer)
  [n x]
  (clojure.string/index-of (str n)
                           (str x)))

(defn last-dex-of
  ; @description
  ; Returns the index of the last occurence of the given 'x' value (converted to string)
  ; in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (*) x
  ;
  ; @usage
  ; (last-dex-of "abc abc" "a")
  ;
  ; @example
  ; (last-dex-of "abc abc" "a")
  ; =>
  ; 4
  ;
  ; @example
  ; (last-dex-of "abc abc" "")
  ; =>
  ; 7
  ;
  ; @example
  ; (last-dex-of "abc abc" nil)
  ; =>
  ; 7
  ;
  ; @example
  ; (last-dex-of "abc abc" "d")
  ; =>
  ; nil
  ;
  ; @example
  ; (last-dex-of [[]] "]")
  ; =>
  ; 3
  ;
  ; @example
  ; (last-dex-of "abc - abc" "a")
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n x]
  (clojure.string/last-index-of (str n)
                                (str x)))

(defn nth-dex-of
  ; @description
  ; Returns the index of the nth occurence of the given 'x' value (converted to string)
  ; in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (*) x
  ; @param (integer) dex
  ;
  ; @usage
  ; (nth-dex-of "abc abc" "a" 1)
  ;
  ; @example
  ; (nth-dex-of "abc abc abc" "a" 1)
  ; =>
  ; 4
  ;
  ; @example
  ; (nth-dex-of "abc - abc - abc" "a" 2 3)
  ; =>
  ; 12
  ;
  ; @return (integer)
  [n x dex]
  (let [n (str n)
        x (str x)]
       (when (>= dex 0)
             (letfn [(f [cursor skip]
                        (if-let [first-dex (-> n (subs cursor)
                                                 (clojure.string/index-of x))]
                                (if (= skip dex)
                                    (+ cursor first-dex)
                                    (f (+ first-dex cursor 1)
                                       (inc skip)))))]
                    (f 0 0)))))
