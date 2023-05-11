
(ns form.api
    (:require [form.check    :as check]
              [form.generate :as generate]
              [form.patterns :as patterns]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; form.check
(def valid-pin?           check/valid-pin?)
(def valid-password?      check/valid-password?)
(def valid-email-address? check/valid-email-address?)
(def valid-phone-number?  check/valid-phone-number?)

; form.generate
(def generate-pin      generate/generate-pin)
(def generate-password generate/generate-password)

; form.patterns
(def pin-pattern           patterns/pin-pattern)
(def password-pattern      patterns/password-pattern)
(def email-address-pattern patterns/email-address-pattern)
(def phone-number-pattern  patterns/phone-number-pattern)
