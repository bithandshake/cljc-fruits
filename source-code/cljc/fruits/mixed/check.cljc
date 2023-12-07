
(ns fruits.mixed.check
    (:refer-clojure :exclude [empty?]))
    
;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn empty?
  ; @param (*) n
  ;
  ; @usage
  ; (empty? nil)
  ;
  ; @example
  ; (empty? nil)
  ; =>
  ; true
  ;
  ; @example
  ; (empty? "")
  ; =>
  ; true
  ;
  ; @example
  ; (empty? [])
  ; =>
  ; true
  ;
  ; @example
  ; (empty? {})
  ; =>
  ; true
  ;
  ; @example
  ; (empty? {:a "A"})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  ; The 'clojure.core/empty?' function can applied only on seqable values.
  ; (Nonseqable values couldn't be empty! E.g., :keyword)
  (and (-> n seqable?)
       (-> n clojure.core/empty?)))

(defn nonempty?
  ; @param (*) n
  ;
  ; @usage
  ; (nonempty? nil)
  ;
  ; @example
  ; (nonempty? nil)
  ; =>
  ; false
  ;
  ; @example
  ; (nonempty? "")
  ; =>
  ; false
  ;
  ; @example
  ; (nonempty? [])
  ; =>
  ; false
  ;
  ; @example
  ; (nonempty? {})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  ; The 'clojure.core/empty?' function can applied only on seqable values.
  ; (A) If the value of 'n' is not seqable, then it's considered as nonempty (e.g., keyword, integer, etc.).
  ; (B) If the value of 'n' is seqable, then it checks whether it is empty (e.g., nil, map, string, vector, etc.)
  (or ; (A)
      (-> n seqable? not)
      ; (B)
      (-> n clojure.core/empty? not)))
