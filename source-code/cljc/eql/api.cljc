
(ns eql.api
    (:require [eql.core :as core]
              [eql.type :as type]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (eql.core)
(def id->document-link   core/id->document-link)
(def document-link->id   core/document-link->id)
(def id->document-entity core/id->document-entity)
(def document-entity->id core/document-entity->id)
(def id->placeholder     core/id->placeholder)
(def append-to-query     core/append-to-query)

; @redirect (eql.type)
(def document-link?   type/document-link?)
(def document-entity? type/document-entity?)
