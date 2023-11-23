
(ns seqable.count)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn count-difference
  ; @param (seqable) a
  ; @param (seqable) b
  ;
  ; @usage
  ; (count-difference [:a :b] [:a :b :c])
  ;
  ; @example
  ; (count-difference [:a :b] [:a :b :c])
  ; =>
  ; 1
  ;
  ; @example
  ; (count-difference [:a :b :c] [:a :b])
  ; =>
  ; -1
  ;
  ; @example
  ; (count-difference "ab" "abc")
  ; =>
  ; 1
  ;
  ; @return (integer)
  [a b]
  (if (and (seqable? a)
           (seqable? b))
      (- (count a)
         (count b))))
