
(ns logical.type)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn nonfalse?
  ; @param (*) n
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
  ; @example
  ; (nontrue? nil)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (not= n true))
