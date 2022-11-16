
(ns gestures.type
    (:require [regex.api :refer [re-match?]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ordered-label?
  ; @param (string) n
  ;
  ; @usage
  ; (ordered-label? "My item #3")
  ;
  ; @return (boolean)
  [n]
  (re-match? n #".*\#\d$"))
