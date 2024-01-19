
(ns fruits.regex.replace
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn replace-match
  ; @description
  ; Replaces matches of the given 'x' pattern in the given 'n' string with the given 'y' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (string) y
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (replace-match "abc" #"[b]*" "x")
  ; =>
  ; "axc"
  ;
  ; @return (string)
  ([n x y]
   (replace-match n x y {}))

  ([n x y options]
   ; The 'fruits.string.api/replace-part' function takes regex patterns as well.
   (string/replace-part n x y options)))
