
(ns uri.api
    (:require [uri.check   :as check]
              [uri.core    :as core]
              [uri.convert :as convert]
              [uri.query   :as query]
              [uri.type    :as type]
              [uri.valid   :as valid]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; uri.check
(def match-template? check/match-template?)

; uri.convert
(def to-protocol     convert/to-protocol)
(def to-domain       convert/to-domain)
(def to-subdomain    convert/to-subdomain)
(def to-tld          convert/to-tld)
(def to-tail         convert/to-tail)
(def to-parent       convert/to-parent)
(def to-relative     convert/to-relative)
(def to-absolute     convert/to-absolute)
(def to-path         convert/to-path)
(def to-path-params  convert/to-path-params)
(def to-fragment     convert/to-fragment)
(def to-query-string convert/to-query-string)
(def to-query-params convert/to-query-params)
(def to-encoded      convert/to-encoded)

; uri.core
(def use-query-string core/use-query-string)

; uri.query
(def query-params->query-string query/query-params->query-string)
(def query-string->query-params query/query-string->query-params)

; uri.type
(def domain? type/domain?)

; uri.valid
(def valid-uri    valid/valid-uri)
(def valid-domain valid/valid-domain)
(def valid-path   valid/valid-path)
