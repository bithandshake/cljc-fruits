
(ns hiccup.api
    (:require [hiccup.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; hiccup.core
(def hiccup?        core/hiccup?)
(def tag-name?      core/tag-name?)
(def walk           core/walk)
(def explode        core/explode)
(def get-attributes core/get-attributes)
(def get-style      core/get-style)
(def set-style      core/set-style)
(def to-string      core/to-string)
(def content-length core/content-length)
(def join-class     core/join-class)
(def value          core/value)
(def unparse-css    core/unparse-css)
