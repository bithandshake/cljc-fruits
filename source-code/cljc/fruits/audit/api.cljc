
(ns fruits.audit.api
    (:require [fruits.audit.check    :as check]
              [fruits.audit.generate :as generate]
              [fruits.audit.patterns :as patterns]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.audit.check)
(def email-address-valid? check/email-address-valid?)
(def ip-address-valid?    check/ip-address-valid?)
(def latin-name-valid?    check/latin-name-valid?)
(def password-valid?      check/password-valid?)
(def phone-number-valid?  check/phone-number-valid?)
(def pin-code-valid?      check/pin-code-valid?)
(def security-code-valid? check/security-code-valid?)
(def user-agent-valid?    check/user-agent-valid?)
(def username-valid?      check/username-valid?)

; @redirect (fruits.audit.generate)
(def generate-password      generate/generate-password)
(def generate-pin-code      generate/generate-pin-code)
(def generate-security-code generate/generate-security-code)

; @redirect (fruits.audit.patterns)
(def email-address-pattern patterns/email-address-pattern)
(def ip-address-pattern    patterns/ip-address-pattern)
(def latin-name-pattern    patterns/latin-name-pattern)
(def password-pattern      patterns/password-pattern)
(def phone-number-pattern  patterns/phone-number-pattern)
(def pin-code-pattern      patterns/pin-code-pattern)
(def security-code-pattern patterns/security-code-pattern)
(def user-agent-pattern    patterns/user-agent-pattern)
(def username-pattern      patterns/username-pattern)
