
(ns audit.api
    (:require [audit.check    :as check]
              [audit.generate :as generate]
              [audit.patterns :as patterns]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; audit.check
(def ip-address-valid?    check/ip-address-valid?)
(def pin-code-valid?      check/pin-code-valid?)
(def password-valid?      check/password-valid?)
(def email-address-valid? check/email-address-valid?)
(def phone-number-valid?  check/phone-number-valid?)

; audit.generate
(def generate-pin-code generate/generate-pin-code)
(def generate-password generate/generate-password)

; audit.patterns
(def ip-address-pattern    patterns/ip-address-pattern)
(def pin-code-pattern      patterns/pin-code-pattern)
(def password-pattern      patterns/password-pattern)
(def email-address-pattern patterns/email-address-pattern)
(def phone-number-pattern  patterns/phone-number-pattern)
