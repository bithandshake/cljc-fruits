
(ns fruits.syntax.api
    (:require [fruits.syntax.convert :as convert]
              [fruits.syntax.wrap    :as wrap]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.syntax.convert/*)
(def to-camelCase  convert/to-camelCase)
(def to-kebab-case convert/to-kebab-case)
(def to-PascalCase convert/to-PascalCase)
(def to-snake_case convert/to-snake_case)

; @redirect (fruits.syntax.wrap/*)
(def paren   wrap/paren)
(def brace   wrap/brace)
(def bracket wrap/bracket)
(def percent wrap/percent)
(def quotes  wrap/quotes)
