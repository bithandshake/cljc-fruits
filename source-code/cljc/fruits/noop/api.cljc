
(ns fruits.noop.api
    (:require [fruits.noop.last :as last]
              [fruits.noop.null :as null]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.noop.last/*)
(def param  last/param)
(def return last/return)

; @redirect (fruits.noop.null/*)
(def none null/none)
