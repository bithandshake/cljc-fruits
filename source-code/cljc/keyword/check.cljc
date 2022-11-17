
(ns keyword.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn namespaced?
  ; @param (keyword) n
  ;
  ; @usage
  ; (namespaced? :a/b)
  ;
  ; @example
  ; (namespaced? :a)
  ; =>
  ; false
  ;
  ; @example
  ; (namespaced? :a/b)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (keyword?  n)
       (namespace n)))
