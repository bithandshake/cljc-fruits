
(ns fruits.keyword.name)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-name
  ; @description
  ; Returns the name value of the given 'n' keyword.
  ;
  ; @param (keyword) n
  ;
  ; @usage
  ; (get-name :a)
  ; =>
  ; :a
  ;
  ; @usage
  ; (get-name :a/b)
  ; =>
  ; :b
  ;
  ; @return (keyword)
  [n]
  (if (-> n keyword?)
      (-> n name keyword)))
