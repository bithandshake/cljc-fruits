
(ns form.api
    (:require [form.check    :as check]
              [form.generate :as generate]
              [form.patterns :as patterns]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; form.check
(def pin-valid?           check/pin-valid?)
(def password-valid?      check/password-valid?)
(def email-address-valid? check/email-address-valid?)
(def phone-number-valid?  check/phone-number-valid?)

; form.generate
(def generate-pin      generate/generate-pin)
(def generate-password generate/generate-password)

; form.patterns
(def pin-pattern           patterns/pin-pattern)
(def password-pattern      patterns/password-pattern)
(def email-address-pattern patterns/email-address-pattern)
(def phone-number-pattern  patterns/phone-number-pattern)
