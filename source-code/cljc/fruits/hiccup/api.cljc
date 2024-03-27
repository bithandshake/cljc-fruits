
(ns fruits.hiccup.api
    (:require [fruits.hiccup.attributes :as attributes]
              [fruits.hiccup.content    :as content]
              [fruits.hiccup.convert    :as convert]
              [fruits.hiccup.parse      :as parse]
              [fruits.hiccup.put        :as put]
              [fruits.hiccup.type       :as type]
              [fruits.hiccup.walk       :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.hiccup.attributes/*)
(def get-attributes attributes/get-attributes)
(def get-style      attributes/get-style)
(def set-style      attributes/set-style)
(def join-class     attributes/join-class)
(def merge-event-fn attributes/merge-event-fn)
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

; @redirect (fruits.hiccup.put/*)
(def put              put/put)
(def put-with         put/put-with)
(def put-with-indexed put/put-with-indexed)

; @redirect (fruits.hiccup.walk/*)
(def walk walk/walk)
