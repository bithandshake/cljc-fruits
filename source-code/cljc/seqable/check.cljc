
(ns seqable.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn nonseqable?
  ; @param (*) n
  ;
  ; @example
  ; (nonseqable? 420)
  ; =>
  ; true
  ;
  ; @example
  ; (nonseqable? "420")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (-> n seqable? not))

(defn nonempty?
  ; @param (*) n
  ;
  ; @example
  ; (nonempty? 420)
  ; =>
  ; false
  ;
  ; @example
  ; (nonempty? "420")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n seqable?)
       (-> n empty? not)))
