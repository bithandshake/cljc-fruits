
(ns fruits.keyword.name)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-name
  ; @param (keyword) n
  ;
  ; @usage
  ; (get-name :a)
  ;
  ; @example
  ; (get-name :a)
  ; =>
  ; :a
  ;
  ; @example
  ; (get-name :a/b)
  ; =>
  ; :b
  ;
  ; @return (keyword)
  [n]
  (if (-> n keyword?)
      (-> n name keyword)))
