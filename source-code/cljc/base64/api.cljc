
(ns base64.api
    (:require [base64.check   :as check]
              [base64.convert :as convert]
              [base64.core    :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; base64.check
(def wrapped? check/wrapped?)
(def encoded? check/encoded?)

; base64.convert
(def to-mime-type convert/to-mime-type)
(def to-blob      convert/to-blob)

; base64.core
(def wrap    core/wrap)
(def encode  core/encode)
(def decode  core/decode)
(def save-as core/save-as)
