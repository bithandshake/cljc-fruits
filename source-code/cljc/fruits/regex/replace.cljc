
(ns fruits.regex.replace
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn replace-match
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (*) y
  ; @param (map)(opt) options
  ; {:recur? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
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
