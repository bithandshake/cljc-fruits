
(ns regex.replace
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn replace-part
  ; @param (*) n
  ; @param (regex pattern) x
  ; @param (*) y
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (replace-part "abc" #"[b]{0,}" "x")
  ; =>
  ; "axc"
  ;
  ; @return (string)
  ([n x y]
   (replace-part n x y {}))

  ([n x y options]
   ; The string.api/replace-part function can take regex patterns as well
   (string/replace-part n x y options)))
