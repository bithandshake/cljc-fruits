
(ns namespace.core
    (:require [keyword.api :as keyword]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn detect
  ; @param (namespaced keyword) sample
  ;
  ; @usage
  ; (detect ::this)
  ;
  ; @return (keyword)
  [sample]
  (keyword/get-namespace sample))
