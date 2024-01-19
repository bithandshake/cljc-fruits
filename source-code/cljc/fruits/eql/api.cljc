
(ns fruits.eql.api
    (:require [fruits.eql.convert :as convert]
              [fruits.eql.type :as type]
              [fruits.eql.append :as append]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.eql.append/*)
(def append-to-query append/append-to-query)

; @redirect (fruits.eql.convert/*)
(def id->document-link   convert/id->document-link)
(def document-link->id   convert/document-link->id)
(def id->document-entity convert/id->document-entity)
(def document-entity->id convert/document-entity->id)
(def id->placeholder     convert/id->placeholder)

; @redirect (fruits.eql.type/*)
(def document-link?   type/document-link?)
(def document-entity? type/document-entity?)
