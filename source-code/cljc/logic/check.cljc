
(ns logic.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn nontrue?
  ; @param (*) n
  ;
  ; @usage
  ; (nontrue? :x)
  ;
  ; @example
  ; (nontrue? nil)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (not= n true))

(defn nonfalse?
  ; @param (*) n
  ;
  ; @usage
  ; (nonfalse? nil)
  ;
  ; @example
  ; (nonfalse? nil)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (not= n false))
