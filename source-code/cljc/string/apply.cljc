
(ns string.apply
    (:require [string.cursor :as cursor]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn apply-on-part
  ; @description
  ; Applies the given 'f' function on a specific part of the 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) from
  ; @param (integer)(opt) to
  ; @param (function) f
  ;
  ; @usage
  ; (apply-on-part "My string" 3 4 string.api/to-uppercase)
  ;
  ; @example
  ; (apply-on-part "My string" 3 4 string.api/to-uppercase)
  ; =>
  ; "My String"
  ;
  ; @return (string)
  ([n from f]
   (apply-on-part n from (-> n str count) f))

  ([n from to f]
   (let [n (str n)]
        (if (and (-> n empty? not)
                 (-> n (cursor/cursor-in-bounds? from))
                 (-> n (cursor/cursor-in-bounds? to)))
            (str (-> n (subs 0 from))
                 (-> n (subs from to) f)
                 (-> n (subs to)))
            (-> n)))))
