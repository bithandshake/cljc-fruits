
(ns fruits.base64.api
    (:require [fruits.base64.convert :as convert]
              [fruits.base64.core    :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.base64.convert)
(def to-header    convert/to-header)
(def to-body      convert/to-body)
(def to-mime-type convert/to-mime-type)

; @redirect (fruits.base64.core)
(def wrap core/wrap)
