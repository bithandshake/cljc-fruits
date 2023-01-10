
(ns syntax.api
    (:require [syntax.comment :as comment]
              [syntax.convert :as convert]
              [syntax.core    :as core]
              [syntax.tags    :as tags]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; syntax.comment
(def remove-comments comment/remove-comments)

; syntax.convert
(def to-snake-case convert/to-snake-case)
(def ToCamelCase   convert/ToCamelCase)

; syntax.core
(def paren   core/paren)
(def bracket core/bracket)
(def percent core/percent)
(def quotes  core/quotes)

; syntax.tags
(def open-tag-position      tags/open-tag-position)
(def close-tag-position     tags/close-tag-position)
(def open-brace-position    tags/open-brace-position)
(def close-brace-position   tags/close-brace-position)
(def open-bracket-position  tags/open-bracket-position)
(def close-bracket-position tags/close-bracket-position)
(def open-paren-position    tags/open-paren-position)
(def close-paren-position   tags/close-paren-position)
