
(ns map.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn nonempty?
  ; @description
  ; Returns TRUE in case of the given 'n' value is a nonempty map.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (nonempty? {})
  ;
  ; @example
  ; (nonempty? {})
  ; =>
  ; false
  ;
  ; @example
  ; (nonempty? {:a "A"})
  ; =>
  ; true
  ;
  ; @example
  ; (nonempty? [:a])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n map?)
       (-> n empty? not)))
