
(ns fruits.seqable.check
    (:refer-clojure :exclude [empty?]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not-seqable?
  ; @description
  ; Returns TRUE if the given 'n' value is not seqable.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (not-seqable? 420)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-seqable? "420")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (-> n seqable? not))

(defn empty?
  ; @description
  ; Returns TRUE if the given 'n' value is an empty seqable value.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (empty? "")
  ; =>
  ; true
  ;
  ; @usage
  ; (empty? "abc")
  ; =>
  ; false
  ;
  ; @usage
  ; (empty? :a)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n seqable?)
       (-> n clojure.core/empty?)))

(defn not-empty?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonempty seqable value.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (not-empty? "420")
  ; =>
  ; true
  ;
  ; @usage
  ; (not-empty? 420)
  ; =>
  ; false
  ;
  ; @usage
  ; (not-empty? "")
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
  ; @note
  ; An N item long sequence has N+1 cursors and N indices.
  ; E.g., Indices of "abc": 0, 1, 2
  ;       Cursors of "abc": 0, 1, 2, 3
  ;
  ; @description
  ; Returns TRUE if the given 'x' value is a cursor (integer).
  ;
  ; @param (*) x
  ;
  ; @usage
  ; (cursor? 42)
  ; =>
  ; true
  ;
  ; @usage
  ; (cursor? -3)
  ; =>
  ; true
  ;
  ; @usage
  ; (cursor? :a)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [x]
  (integer? x))

(defn dex?
  ; @note
  ; An N item long sequence has N+1 cursors and N indices.
  ; E.g., Indices of "abc": 0, 1, 2
  ;       Cursors of "abc": 0, 1, 2, 3
  ;
  ; @note
  ; Negative index values are distances from the end of a sequence.
  ;
  ; @description
  ; Returns TRUE if the given 'x' value is an index (integer).
  ;
  ; @param (*) x
  ;
  ; @usage
  ; (dex? 42)
  ; =>
  ; true
  ;
  ; @usage
  ; (dex? -3)
  ; =>
  ; true
  ;
  ; @usage
  ; (dex? :a)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [x]
  (integer? x))
