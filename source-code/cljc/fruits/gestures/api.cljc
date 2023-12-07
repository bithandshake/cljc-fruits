
(ns fruits.gestures.api
    (:require [fruits.gestures.core :as core]
              [fruits.gestures.type :as type]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.gestures.core)
(def item-label->copy-label core/item-label->copy-label)
(def resolve-variable       core/resolve-variable)

; @redirect (fruits.gestures.type)
(def ordered-label? type/ordered-label?)
