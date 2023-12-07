
(ns fruits.syntax.api
    (:require [fruits.syntax.convert :as convert]
              [fruits.syntax.core    :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.syntax.convert)
(def to-snake-case convert/to-snake-case)
(def ToCamelCase   convert/ToCamelCase)

; @redirect (fruits.syntax.core)
(def paren   core/paren)
(def brace   core/brace)
(def bracket core/bracket)
(def percent core/percent)
(def quotes  core/quotes)
