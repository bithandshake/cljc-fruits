
(ns fruits.seqable.check
    (:refer-clojure :exclude [empty?]))

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

(defn empty?
  ; @param (*) n
  ;
  ; @usage
  ; (empty? "")
  ;
  ; @example
  ; (empty? "")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n seqable?)
       (-> n clojure.core/empty?)))

(defn nonempty?
  ; @param (*) n
  ;
  ; @usage
  ; (nonempty? "420")
  ;
  ; @example
  ; (nonempty? "420")
  ; =>
  ; true
  ;
  ; @example
  ; (nonempty? 420)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n seqable?)
       (-> n clojure.core/empty? not)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn cursor?
  ; @param (*) x
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
  [x]
  (nat-int? x))

(defn dex?
  ; @param (*) x
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
  [x]
  (nat-int? x))
