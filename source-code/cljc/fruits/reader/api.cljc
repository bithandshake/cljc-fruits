
(ns fruits.reader.api
    (:require [fruits.reader.prepare :as prepare]
              [fruits.reader.parse :as parse]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.reader.prepare/*)
(def prepare-edn  prepare/prepare-edn)
(def prepare-json prepare/prepare-json)

; @redirect (fruits.reader.parse/*)
(def parse-edn  parse/parse-edn)
(def parse-json parse/parse-json)
