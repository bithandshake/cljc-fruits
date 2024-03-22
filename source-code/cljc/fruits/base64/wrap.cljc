
(ns fruits.base64.wrap
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn wrap
  ; @description
  ; Prepends a Base64 header to the given Base64 body.
  ;
  ; @param (string) n
  ; @param (string) mime-type
  ;
  ; @usage
  ; (wrap "..." "application/pdf")
  ; =>
  ; "data:application/pdf;base64,..."
  ;
  ; @usage
  ; (wrap "" "application/pdf")
  ; =>
  ; nil
  ;
  ; @usage
  ; (wrap nil "application/pdf")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n mime-type]
  (if (string/not-empty? n)
      (str "data:"mime-type";base64,"n)))
