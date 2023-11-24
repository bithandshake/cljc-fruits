
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
  ; @param (integer)(opt) offset
  ; Default: 0
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
  ([n x]
   (first-dex-of n x 0))

  ([n x offset]
   (let [n (str n)
         x (str x)]
        (clojure.string/index-of n x offset))))

(defn last-dex-of
  ; @description
  ; Returns the index of the last occurence of the given 'x' value (converted to string)
  ; in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (*) x
  ; @param (integer)(opt) offset
  ; Default: 0
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
  ([n x]
   (last-dex-of n x 0))

  ([n x offset]
   (let [n (str n)
         x (str x)]
        (clojure.string/last-index-of n x offset))))

(defn nth-dex-of
  ; @description
  ; Returns the index of the nth occurence of the given 'x' value (converted to string) in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (*) x
  ; @param (integer) th
  ; @param (integer)(opt) offset
  ; Default: 0
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
  ([n x th]
   (nth-dex-of n x th 0))

  ([n x th offset]
   (let [n (str n)
         x (str x)]
        (when (>= th 0)
              (letfn [(f [cursor skip]
                         (if-let [first-dex (-> n (subs cursor)
                                                  (clojure.string/index-of x))]
                                 (if (= skip th)
                                     (+ cursor first-dex)
                                     (f (+ first-dex cursor 1)
                                        (inc skip)))))]
                     (f offset 0))))))
