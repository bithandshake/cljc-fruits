
(ns reader.api
    (:require [reader.prepare :as prepare]
              [reader.read    :as read]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; reader.prepare
(def prepare-edn  prepare/prepare-edn)
(def prepare-json prepare/prepare-json)

; reader.read
(def read-edn  read/read-edn)
(def read-json read/read-json)
