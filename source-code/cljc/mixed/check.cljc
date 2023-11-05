
(ns mixed.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn nonblank?
  ; @param (*) n
  ;
  ; @example
  ; (nonblank? nil)
  ; =>
  ; false
  ;
  ; @example
  ; (nonblank? "")
  ; =>
  ; false
  ;
  ; @example
  ; (nonblank? [])
  ; =>
  ; false
  ;
  ; @example
  ; (nonblank? {})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  ; The 'empty?' function can only be applied to seqable values.
  ; (A) If the value of 'n' is not seqable, then it's considered nonempty (e.g., keyword, integer, etc.).
  ; (B) If the value of 'n' is seqable, then it checks whether it is empty (e.g., nil, map, string, vector, etc.)
  (or ; (A)
      (-> n seqable? not)
      ; (B)
      (-> n empty? not)))

(defn blank?
  ; @param (*) n
  ;
  ; @example
  ; (blank? nil)
  ; =>
  ; true
  ;
  ; @example
  ; (blank? "")
  ; =>
  ; true
  ;
  ; @example
  ; (blank? [])
  ; =>
  ; true
  ;
  ; @example
  ; (blank? {})
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  ; The 'empty?' function can only be applied to seqable values.
  ; (Nonseqable values couldn't be empty! E.g., :keyword)
  (and (-> n seqable?)
       (-> n empty?)))
