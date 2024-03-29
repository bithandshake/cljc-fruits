
(ns fruits.gestures.type
    (:require [fruits.regex.api :as regex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ordered-label?
  ; @param (string) n
  ;
  ; @usage
  ; (ordered-label? "My item #3")
  ; =>
  ; true
  ;
  ; @usage
  ; (ordered-label? "My item")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (regex/re-match? n #".*\#\d+$"))
