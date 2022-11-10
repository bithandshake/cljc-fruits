
(ns gestures.api
    (:require [gestures.core :as core]
              [gestures.type :as type]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; gestures.core
(def item-label->copy-label core/item-label->copy-label)
(def resolve-variable       core/resolve-variable)

; gestures.type
(def ordered-label? type/ordered-label?)
