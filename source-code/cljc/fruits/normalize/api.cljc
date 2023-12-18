
(ns fruits.normalize.api
    (:require [fruits.normalize.clean :as clean]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.normalize.clean/*)
(def deaccent             clean/deaccent)
(def remove-special-chars clean/remove-special-chars)
(def replace-blank-chars  clean/replace-blank-chars)
(def clean-text           clean/clean-text)
