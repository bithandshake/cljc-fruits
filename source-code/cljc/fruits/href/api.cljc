
(ns fruits.href.api
    (:require [fruits.href.link :as link]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.href.link/*)
(def email-address       link/email-address)
(def phone-number        link/phone-number)
(def google-maps-address link/google-maps-address)
(def ftp-uri             link/ftp-uri)
(def http-uri            link/http-uri)
(def https-uri           link/https-uri)
