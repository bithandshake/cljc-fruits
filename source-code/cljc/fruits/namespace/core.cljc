
(ns fruits.namespace.core
    (:require [fruits.keyword.api :as keyword]))

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
