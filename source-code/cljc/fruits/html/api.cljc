
(ns fruits.html.api
    (:refer-clojure :exclude [empty?])
    (:require [fruits.html.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.html.check)
(def blank?    check/blank?)
(def nonblank? check/nonblank?)
(def empty?    check/empty?)
(def nonempty? check/nonempty?)
