
(ns fruits.namespace.detect)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn detect
  ; @param (namespaced keyword) sample
  ;
  ; @usage
  ; (detect ::this)
  ; =>
  ; :my-namespace
  ;
  ; @usage
  ; (detect :my-namespace/this)
  ; =>
  ; :my-namespace
  ;
  ; @return (keyword)
  [sample]
  (if (-> sample keyword?)
      (-> sample namespace keyword)))
