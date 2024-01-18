
(ns fruits.html.api
    (:refer-clojure :exclude [empty?])
    (:require [fruits.html.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.html.check)
(def blank?     check/blank?)
(def not-blank? check/not-blank?)
(def empty?     check/empty?)
(def not-empty? check/not-empty?)
