
(ns string.apply
    (:require [seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn apply-on-range
  ; @description
  ; Applies the given 'f' function on a specific range of the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) start
  ; @param (integer)(opt) end
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
  ([n start f]
   (apply-on-range n start nil f))

  ([n start end f]
   (let [n     (str n)
         start (seqable/normalize-cursor n (-> start))
         end   (seqable/normalize-cursor n (-> end (or (count n))))
         start (min start end)
         end   (max start end)]
        (str (-> n (subs 0 start))
             (-> n (subs start end) f)
             (-> n (subs end))))))
