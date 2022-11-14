
(ns uri.api
    (:require [uri.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; uri.core
(def uri->protocol              core/uri->protocol)
(def uri->domain                core/uri->domain)
(def uri->subdomain             core/uri->subdomain)
(def uri->tld                   core/uri->tld)
(def uri->tail                  core/uri->tail)
(def uri->parent-uri            core/uri->parent-uri)
(def uri->local-uri             core/uri->local-uri)
(def uri->trimmed-uri           core/uri->trimmed-uri)
(def uri->path                  core/uri->path)
(def uri->trimmed-path          core/uri->trimmed-path)
(def uri->path-parts            core/uri->path-parts)
(def uri->path-params           core/uri->path-params)
(def uri->fragment              core/uri->fragment)
(def uri->query-string          core/uri->query-string)
(def query-params->query-string core/query-params->query-string)
(def query-string->query-params core/query-string->query-params)
(def uri->query-params          core/uri->query-params)
(def string->uri                core/string->uri)
(def string->uri-part           core/string->uri-part)
(def uri<-query-string          core/uri<-query-string)
(def path->match-template?      core/path->match-template?)
(def valid-uri                  core/valid-uri)
(def valid-path                 core/valid-path)
