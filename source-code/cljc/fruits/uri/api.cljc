
(ns fruits.uri.api
    (:require [fruits.uri.check   :as check]
              [fruits.uri.convert :as convert]
              [fruits.uri.core    :as core]
              [fruits.uri.query   :as query]
              [fruits.uri.valid   :as valid]
              [fruits.uri.patterns :as patterns]
              [fruits.uri.pretty  :as pretty]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.uri.check)
(def url-path-template-matches? check/url-path-template-matches?)

; @redirect (fruits.uri.convert)
(def to-lowercase        convert/to-lowercase)
(def to-scheme           convert/to-scheme)
(def to-nonschemed       convert/to-nonschemed)
(def to-hostname         convert/to-hostname)
(def to-port             convert/to-port)
(def to-domain           convert/to-domain)
(def to-subdomain        convert/to-subdomain)
(def to-tld              convert/to-tld)
(def to-url-tail         convert/to-url-tail)
(def to-parent-url       convert/to-parent-url)
(def to-relative-url     convert/to-relative-url)
(def to-absolute-url     convert/to-absolute-url)
(def to-url-path         convert/to-url-path)
(def to-url-path-params  convert/to-url-path-params)
(def to-url-fragment     convert/to-url-fragment)
(def to-url-query-string convert/to-url-query-string)
(def to-url-query-params convert/to-url-query-params)
(def to-encoded-url      convert/to-encoded-url)

; @redirect (fruits.uri.core)
(def use-url-query-string core/use-url-query-string)

; @redirect (fruits.uri.patterns)
(def PORT-PATTERN   patterns/PORT-PATTERN)
(def SCHEME-PATTERN patterns/SCHEME-PATTERN)
(def DOMAIN-PATTERN patterns/DOMAIN-PATTERN)

; @redirect (fruits.uri.pretty)
(def pretty-url pretty/pretty-url)

; @redirect (fruits.uri.query)
(def url-query-params->url-query-string query/url-query-params->url-query-string)
(def url-query-string->url-query-params query/url-query-string->url-query-params)

; @redirect (fruits.uri.valid)
(def valid-url      valid/valid-url)
(def valid-url-path valid/valid-url-path)
