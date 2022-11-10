
(ns hiccup.api
    (:require [hiccup.attributes :as attributes]
              [hiccup.content    :as content]
              [hiccup.convert    :as convert]
              [hiccup.parse      :as parse]
              [hiccup.type       :as type]
              [hiccup.walk       :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; hiccup.attributes
(def get-attributes attributes/get-attributes)
(def get-style      attributes/get-style)
(def set-style      attributes/set-style)
(def join-class     attributes/join-class)
(def value          attributes/value)

; hiccup.content
(def content-length content/content-length)

; hiccup.convert
(def to-string convert/to-string)

; hiccup.parse
(def unparse-css parse/unparse-css)

; hiccup.type
(def hiccup?   core/hiccup?)
(def tag-name? core/tag-name?)

; hiccup.walk
(def walk    walk/walk)
(def explode walk/explode)
