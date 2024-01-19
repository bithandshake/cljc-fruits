
(ns fruits.string.apply
    (:require [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn apply-on-range
  ; @description
  ; Applies the given 'f' function on a specific range of the given 'n' string.
  ;
  ; @param (string) n
  ; @param (integer) start
  ; @param (integer)(opt) end
  ; @param (function) f
  ;
  ; @usage
  ; (apply-on-range "abcdef" 3 4 to-uppercase)
  ; =>
  ; "abcDef"
  ;
  ; @return (string)
  ([n start f]
   (let [n   (str n)
         end (count n)]
        (apply-on-range n start end f)))

  ([n start end f]
   (let [n     (str n)
         start (seqable/normalize-cursor n start {:adjust? true :mirror? true})
         end   (seqable/normalize-cursor n end   {:adjust? true :mirror? true})
         start (min start end)
         end   (max start end)]
        (str (-> n (subs 0 start))
             (-> n (subs start end) f)
             (-> n (subs end))))))
