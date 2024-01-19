
(ns fruits.logic.gates)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn nor
  ; @description
  ; Returns TRUE if all the given parameters are FALSE.
  ;
  ; @param (list of *) abc
  ;
  ; @usage
  ; (nor true false false)
  ; =>
  ; false
  ;
  ; @usage
  ; (nor false false false)
  ; =>
  ; true
  ;
  ; @usage
  ; (nor false nil)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [& abc]
  (not-any? boolean abc))
