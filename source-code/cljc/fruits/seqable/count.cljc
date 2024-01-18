
(ns fruits.seqable.count
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn count-difference
  ; @description
  ; Returns the difference in item count between the given 'a' and 'b' sequences.
  ;
  ; @param (seqable) a
  ; @param (seqable) b
  ;
  ; @usage
  ; (count-difference [:a :b] [:a :b :c])
  ; =>
  ; 1
  ;
  ; @usage
  ; (count-difference [:a :b :c] [:a :b])
  ; =>
  ; -1
  ;
  ; @usage
  ; (count-difference "ab" "abc")
  ; =>
  ; 1
  ;
  ; @usage
  ; (count-difference 123 123456)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [a b]
  (let [a (mixed/to-seqable a)
        b (mixed/to-seqable b)]
       (- (-> a count)
          (-> b count))))
