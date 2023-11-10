
(ns vector.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn nonempty?
  ; @param (*) n
  ;
  ; @usage
  ; (nonempty? [:a])
  ;
  ; @example
  ; (nonempty? [])
  ; =>
  ; false
  ;
  ; @example
  ; (nonempty? [:a])
  ; =>
  ; true
  ;
  ; @return (boolean)
  ; Is n a nonempty vector?
  [n]
  (and (-> n vector?)
       (-> n empty? not)))
