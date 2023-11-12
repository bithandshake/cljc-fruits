
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

(defn cursor?
  ; @param (*) n
  ;
  ; @usage
  ; (cursor? 42)
  ;
  ; @example
  ; (cursor? 42)
  ; =>
  ; true
  ;
  ; @example
  ; (cursor? -3)
  ; =>
  ; false
  ;
  ; @example
  ; (cursor? :a)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (nat-int? n))

(defn dex?
  ; @param (*) n
  ;
  ; @usage
  ; (dex? 42)
  ;
  ; @example
  ; (dex? 42)
  ; =>
  ; true
  ;
  ; @example
  ; (dex? -3)
  ; =>
  ; false
  ;
  ; @example
  ; (dex? :a)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (nat-int? n))
