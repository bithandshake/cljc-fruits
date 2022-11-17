
(ns css.parse
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn unparse
  ; @param (map) n
  ;
  ; @example
  ; (unparse {:opacity 1 :width "100%"})
  ; =>
  ; "opacity: 1; width: 100%;"
  ;
  ; @return (string)
  [n]
  (letfn [(f [style k v] (str style (name k) ": " (if (keyword? v) (name v) v) "; "))]
         (string/trim (reduce-kv f "" n))))
