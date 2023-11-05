
(ns string.dex
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn dex-in-bounds?
  ; @description
  ; - Returns TRUE if the given 'dex' value is falls between 0 and the highest possible index value ('(-> n count dec)').
  ; - Cursors are different from indexes in strings.
  ;   A cursor could be at the very end of the string while an index could only point to the last character at the end.
  ;
  ; @param (*) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dex-in-bounds? "abc" 2)
  ;
  ; @example
  ; (dex-in-bounds? "abc" 2)
  ; =>
  ; true
  ;
  ; @example
  ; (dex-in-bounds? "abc" 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n dex]
  (let [n (str n)]
       (and (-> dex nat-int?)
            (-> n count (> dex)))))

(defn dex-out-of-bounds?
  ; @description
  ; - Returns TRUE if the given 'dex' value is NOT falls between 0 and the highest possible index value ('(-> n count dec)').
  ; - Cursors are different from indexes in strings.
  ;   A cursor could be at the very end of the string while an index could only point to the last character at the end.
  ;
  ; @param (*) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dex-out-of-bounds? "abc" 3)
  ;
  ; @example
  ; (dex-out-of-bounds? "abc" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (dex-out-of-bounds? "abc" 2)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n dex]
  (let [n (str n)]
       (or (-> dex nat-int? not)
           (-> n count (<= dex)))))

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
  ; (nth-dex-of "abc abc abc" "a" 1)
  ; =>
  ; 4
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
