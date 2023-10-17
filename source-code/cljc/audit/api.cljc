
(ns audit.api
    (:require [audit.check    :as check]
              [audit.generate :as generate]
              [audit.patterns :as patterns]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; audit.check
(def email-address-valid? check/email-address-valid?)
(def ip-address-valid?    check/ip-address-valid?)
(def latin-name-valid?    check/latin-name-valid?)
(def password-valid?      check/password-valid?)
(def phone-number-valid?  check/phone-number-valid?)
(def pin-code-valid?      check/pin-code-valid?)
(def security-code-valid? check/security-code-valid?)
(def username-valid?      check/username-valid?)

; audit.generate
(def generate-password      generate/generate-password)
(def generate-pin-code      generate/generate-pin-code)
(def generate-security-code generate/generate-security-code)

; audit.patterns
(def email-address-pattern patterns/email-address-pattern)
(def ip-address-pattern    patterns/ip-address-pattern)
(def latin-name-pattern    patterns/latin-name-pattern)
(def password-pattern      patterns/password-pattern)
(def phone-number-pattern  patterns/phone-number-pattern)
(def pin-code-pattern      patterns/pin-code-pattern)
(def security-code-pattern patterns/security-code-pattern)
(def username-pattern      patterns/username-pattern)
