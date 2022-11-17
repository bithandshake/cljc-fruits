
(ns string.dex
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-dex-of
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
  ; @return (integer)
  [n x]
  (clojure.string/index-of (str n)
                           (str x)))

(defn last-dex-of
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
  ; @return (integer)
  [n x]
  (clojure.string/last-index-of (str n)
                                (str x)))

(defn nth-dex-of
  ; @param (*) n
  ; @param (*) x
  ; @param (integer) dex
  ;
  ; @usage
  ; (nth-dex-of "abc abc" "a" 1)
  ;
  ; @example
  ; (nth-dex-of "abc abc abc" "a" 2)
  ; =>
  ; 4
  ;
  ; @return (integer)
  [n x dex]
  (let [n (str n)
        x (str x)]
       (when (>= dex 1)
             (letfn [(f [cursor lap]
                        (if-let [first-dex (-> n (subs cursor)
                                                 (clojure.string/index-of x))]
                                (if (= lap dex)
                                    (+ cursor first-dex)
                                    (f (+ first-dex cursor 1)
                                       (inc lap)))))]
                    (f 0 1)))))
