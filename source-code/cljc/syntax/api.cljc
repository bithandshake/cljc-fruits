
(ns syntax.api
    (:require [syntax.convert :as convert]
              [syntax.core    :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; syntax.convert
(def to-snake-case convert/to-snake-case)
(def ToCamelCase   convert/ToCamelCase)

; syntax.core
(def paren   core/paren)
(def brace   core/brace)
(def bracket core/bracket)
(def percent core/percent)
(def quotes  core/quotes)
