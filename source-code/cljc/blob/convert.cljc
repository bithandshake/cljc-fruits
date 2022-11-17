
(ns blob.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-object-url
  ; @param (object) blob
  ;
  ; @usage
  ; (to-object-url ...)
  ;
  ; @return (object)
  [blob]
  #?(:cljs (.createObjectURL js/URL blob)))
