
(ns base64.api
    (:require [base64.convert :as convert]
              [base64.core    :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; base64.convert
(def to-header    convert/to-header)
(def to-body      convert/to-body)
(def to-mime-type convert/to-mime-type)

; base64.core
(def wrap core/wrap)
