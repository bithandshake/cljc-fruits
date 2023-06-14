
(ns base64.check
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn wrapped?
  ; @param (*) n
  ;
  ; @usage
  ; (wrapped? "data:application/pdf;base64,...")
  ;
  ; @example
  ; (wrapped? "data:application/pdf;base64,...")
  ; =>
  ; true
  ;
  ; @example
  ; (wrapped? "...")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (string/starts-with? n "data:"))

(defn encoded?
  ; @param (*) n
  ;
  ; @usage
  ; (encoded? "...")
  ;
  ; @return (boolean)
  ; Returns true if the given value is base64 encoded.
  [n])
  ; TODO
