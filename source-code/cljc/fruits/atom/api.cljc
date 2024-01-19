
(ns fruits.atom.api
    (:require [fruits.atom.update :as update]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.atom.update/*)
(def not! update/not!)
(def inc! update/inc!)
(def dec! update/dec!)
