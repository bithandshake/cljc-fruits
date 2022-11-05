
(ns namespace.core
    (:require [mid-fruits.keyword :as keyword]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn detect
  ; @param (namespaced keyword) sample
  ;
  ; @usage
  ;  (detect ::this)
  ;
  ; @return (keyword)
  [sample]
  (keyword/get-namespace sample))
