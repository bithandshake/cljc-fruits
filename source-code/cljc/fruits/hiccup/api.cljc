
(ns fruits.hiccup.api
    (:require [fruits.hiccup.attributes :as attributes]
              [fruits.hiccup.content    :as content]
              [fruits.hiccup.convert    :as convert]
              [fruits.hiccup.parse      :as parse]
              [fruits.hiccup.type       :as type]
              [fruits.hiccup.walk       :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.hiccup.attributes/*)
(def get-attributes attributes/get-attributes)
(def get-style      attributes/get-style)
(def set-style      attributes/set-style)
(def join-class     attributes/join-class)
(def value          attributes/value)

; @redirect (fruits.hiccup.content/*)
(def content-length content/content-length)

; @redirect (fruits.hiccup.convert/*)
(def to-string convert/to-string)

; @redirect (fruits.hiccup.parse/*)
(def parse-css             parse/parse-css)
(def unparse-css           parse/unparse-css)
(def unparse-class-vectors parse/unparse-class-vectors)
(def parse-newlines        parse/parse-newlines)
(def unparse-newlines      parse/unparse-newlines)

; @redirect (fruits.hiccup.type/*)
(def hiccup?   type/hiccup?)
(def tag-name? type/tag-name?)

; @redirect (fruits.hiccup.walk/*)
(def walk             walk/walk)
(def explode          walk/explode)
(def put              walk/put)
(def put-with         walk/put-with)
(def put-with-indexed walk/put-with-indexed)
