
(ns fruits.keyword.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn namespaced?
  ; @description
  ; Returns TRUE if the given 'n' value is a namespaced keyword.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (namespaced? :a/b)
  ; =>
  ; true
  ;
  ; @usage
  ; (namespaced? :a)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n keyword)
       (-> n namespace some?)))

(defn not-namespaced?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonnamespaced keyword.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (not-namespaced? :a)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-namespaced? :a/b)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n keyword)
       (-> n namespace nil?)))
