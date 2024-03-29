
(ns fruits.base64.convert
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-header
  ; @description
  ; Returns the header of the given Base64 string.
  ;
  ; @param (string) base64
  ;
  ; @usage
  ; (to-header "data:application/pdf;base64,...")
  ; =>
  ; "data:application/pdf;base64,"
  ;
  ; @return (string)
  [base64]
  (-> base64 (string/to-first-occurence "," {:return? false})
             (string/if-contains-part   "data:")))

(defn to-body
  ; @description
  ; Returns the body of the given Base64 string.
  ;
  ; @param (string) base64
  ;
  ; @usage
  ; (to-body "data:application/pdf;base64,...")
  ; =>
  ; "..."
  ;
  ; @return (string)
  [base64]
  (-> base64 (string/if-contains-part      "data:")
             (string/after-first-occurence "," {:return? false})))

(defn to-mime-type
  ; @description
  ; Returns the MIME type of the given Base64 string.
  ;
  ; @param (string) base64
  ;
  ; @usage
  ; (to-mime-type "data:application/pdf;base64,...")
  ; =>
  ; "application/pdf"
  ;
  ; @return (string)
  [base64]
  (-> base64 (string/after-first-occurence "data:" {:return? false})
             (string/after-first-occurence ";"     {:return? false})))
