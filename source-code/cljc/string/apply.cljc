
(ns string.apply
    (:require [seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn apply-on-range
  ; @description
  ; Applies the given 'f' function on a specific range of the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) from
  ; @param (integer)(opt) to
  ; @param (function) f
  ;
  ; @usage
  ; (apply-on-range "abcdef" 3 4 to-uppercase)
  ;
  ; @example
  ; (apply-on-range "abcdef" 3 4 to-uppercase)
  ; =>
  ; "abcDef"
  ;
  ; @return (string)
  ([n from f]
   (apply-on-range n from (-> n str count) f))

  ([n from to f]
   (let [n    (str n)
         from (seqable/normalize-cursor n from)
         to   (seqable/normalize-cursor n to)
         from (min from to)
         to   (max from to)]
        (str (-> n (subs 0 from))
             (-> n (subs from to) f)
             (-> n (subs to))))))
