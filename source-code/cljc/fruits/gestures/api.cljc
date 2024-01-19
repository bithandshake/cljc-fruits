
(ns fruits.gestures.api
    (:require [fruits.gestures.convert :as convert]
              [fruits.gestures.resolve :as resolve]
              [fruits.gestures.type    :as type]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.gestures.convert/*)
(def item-label->copy-label convert/item-label->copy-label)

; @redirect (fruits.gestures.resolve/*)
(def resolve-variable resolve/resolve-variable)

; @redirect (fruits.gestures.type/*)
(def ordered-label? type/ordered-label?)
