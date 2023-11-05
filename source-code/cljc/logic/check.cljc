
(ns logic.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

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
