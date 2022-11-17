
(ns keyword.convert
    (:require [candy.api :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-string
  ; @param (keyword) n
  ;
  ; @usage
  ; (to-string :a/b)
  ;
  ; @example
  ; (to-string :a/b)
  ; =>
  ; "a/b"
  ;
  ; @return (string)
  [n]
 ;(apply str (rest (str n)))
  (if (keyword? n)
      (if-let [namespace (namespace n)]
              (str namespace "/" (name n))
              (name n))
      (return n)))
