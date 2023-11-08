
(ns syntax.api
    (:require [syntax.check       :as check]
              [syntax.comment     :as comment]
              [syntax.convert     :as convert]
              [syntax.core        :as core]
              [syntax.interpreter :as interpreter]
              [syntax.tags        :as tags]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; syntax.check
(def position-escaped?   check/position-escaped?)
(def position-commented? check/position-commented?)
(def position-quoted?    check/position-quoted?)

; syntax.comment
(def remove-comments comment/remove-comments)

; syntax.convert
(def to-snake-case convert/to-snake-case)
(def ToCamelCase   convert/ToCamelCase)

; syntax.core
(def paren   core/paren)
(def brace   core/brace)
(def bracket core/bracket)
(def percent core/percent)
(def quotes  core/quotes)

; syntax.interpreter
(def tag-matrix interpreter/tag-matrix)

; syntax.tags
(def tag-position           tags/tag-position)
(def tag-count              tags/tag-count)
(def tags-balanced?         tags/tags-balanced?)
(def open-tag-position      tags/open-tag-position)
(def close-tag-position     tags/close-tag-position)
(def open-brace-position    tags/open-brace-position)
(def close-brace-position   tags/close-brace-position)
(def open-bracket-position  tags/open-bracket-position)
(def close-bracket-position tags/close-bracket-position)
(def open-paren-position    tags/open-paren-position)
(def close-paren-position   tags/close-paren-position)
