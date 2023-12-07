
(ns fruits.reader.api
    (:require [fruits.reader.prepare :as prepare]
              [fruits.reader.read    :as read]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.reader.prepare)
(def prepare-edn  prepare/prepare-edn)
(def prepare-json prepare/prepare-json)

; @redirect (fruits.reader.read)
(def read-edn  read/read-edn)
(def read-json read/read-json)
