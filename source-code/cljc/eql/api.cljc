
(ns eql.api
    (:require [eql.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; eql.core
(def document-link?      core/document-link?)
(def id->document-link   core/id->document-link)
(def document-link->id   core/document-link->id)
(def document-entity?    core/document-entity?)
(def id->document-entity core/id->document-entity)
(def document-entity->id core/document-entity->id)
(def id->placeholder     core/id->placeholder)
(def append-to-query     core/append-to-query)
