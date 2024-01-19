
(ns fruits.namespace.api
    (:require [fruits.namespace.detect :as detect]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.namespace.detect/*)
(def detect detect/detect)
