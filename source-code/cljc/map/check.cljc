
(ns map.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn nonempty?
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
