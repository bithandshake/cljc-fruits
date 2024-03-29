
(ns fruits.json.api
    (:require [fruits.json.convert :as convert]
              [fruits.json.update  :as update]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.json.convert/*)
(def to-clj convert/to-clj)

; @redirect (fruits.json.update/*)
(def unkeywordize-key     update/unkeywordize-key)
(def keywordize-key       update/keywordize-key)
(def underscore-key       update/underscore-key)
(def hyphenize-key        update/hyphenize-key)
(def camelCase-key        update/camelCase-key)
(def kebab-case-key       update/kebab-case-key)
(def PascalCase-key       update/PascalCase-key)
(def snake_case-key       update/snake_case-key)
(def unkeywordized-value? update/unkeywordized-value?)
(def keywordize-value     update/keywordize-value)
(def unkeywordize-value   update/unkeywordize-value)
(def trim-value           update/trim-value)
(def parse-number-value   update/parse-number-value)
(def unkeywordize-keys    update/unkeywordize-keys)
(def keywordize-keys      update/keywordize-keys)
(def underscore-keys      update/underscore-keys)
(def hyphenize-keys       update/hyphenize-keys)
(def camelCase-keys       update/camelCase-keys)
(def kebab-case-keys      update/kebab-case-keys)
(def PascalCase-keys      update/PascalCase-keys)
(def snake_case-keys      update/snake_case-keys)
(def unkeywordize-values  update/unkeywordize-values)
(def keywordize-values    update/keywordize-values)
(def trim-values          update/trim-values)
(def parse-number-values  update/parse-number-values)
(def remove-blank-values  update/remove-blank-values)
