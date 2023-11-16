
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
  ; (namespaced? :a/b)
  ; =>
  ; true
  ;
  ; @example
  ; (namespaced? :a)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (keyword?  n)
       (namespace n)))

(defn nonnamespaced?
  ; @param (keyword) n
  ;
  ; @usage
  ; (nonnamespaced? :a)
  ;
  ; @example
  ; (nonnamespaced? :a)
  ; =>
  ; true
  ;
  ; @example
  ; (nonnamespaced? :a/b)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (-> n namespaced? not))
