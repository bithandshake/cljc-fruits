
(ns json.api
    (:require [json.core :as core]))
;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; json.core
(def json->clj            core/json->clj)
(def unkeywordize-key     core/unkeywordize-key)
(def keywordize-key       core/keywordize-key)
(def underscore-key       core/underscore-key)
(def hyphenize-key        core/hyphenize-key)
(def CamelCase-key        core/CamelCase-key)
(def snake-case-key       core/snake-case-key)
(def unkeywordized-value? core/unkeywordized-value?)
(def keywordize-value     core/keywordize-value)
(def unkeywordize-value   core/unkeywordize-value)
(def trim-value           core/trim-value)
(def parse-number-value   core/parse-number-value)
(def unkeywordize-keys    core/unkeywordize-keys)
(def keywordize-keys      core/keywordize-keys)
(def underscore-keys      core/underscore-keys)
(def hyphenize-keys       core/hyphenize-keys)
(def CamelCase-keys       core/CamelCase-keys)
(def snake-case-keys      core/snake-case-keys)
(def unkeywordize-values  core/unkeywordize-values)
(def keywordize-values    core/keywordize-values)
(def trim-values          core/trim-values)
(def parse-number-values  core/parse-number-values)
(def remove-blank-values  core/remove-blank-values)
