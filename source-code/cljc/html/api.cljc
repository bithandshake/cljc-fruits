
(ns html.api
    (:refer-clojure :exclude [empty?])
    (:require [html.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (html.check)
(def blank?    check/blank?)
(def nonblank? check/nonblank?)
(def empty?    check/empty?)
(def nonempty? check/nonempty?)
