
(ns fruits.logic.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not-true?
  ; @description
  ; Returns TRUE if the given 'n' value is not a TRUE boolean.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (not-true? nil)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (not= n true))

(defn not-false?
  ; @description
  ; Returns TRUE if the given 'n' value is not a FALSE boolean.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (not-false? nil)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (not= n false))
