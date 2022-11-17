
(ns string.convert
    (:require [candy.api :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-integer
  ; @param (integer or string) n
  ;
  ; @usage
  ; (to-integer "420")
  ;
  ; @example
  ; (to-integer "420")
  ; =>
  ; 420
  ;
  ; @return (integer)
  [n]
  #?(:cljs (cond (string?  n) (js/parseInt n)
                 (integer? n) (return      n))
     :clj  (cond (string?  n) (Integer. (re-find #"\d+" n))
                 (integer? n) (return      n))))
