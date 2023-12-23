
(ns fruits.uri.patterns)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (regex pattern)
(def PORT-PATTERN #"[\d]{1,8}")

; @constant (regex pattern)
;
; @description
; https://en.wikipedia.org/wiki/Uniform_Resource_Identifier
; A scheme consisting of a sequence of characters beginning with a letter and followed
; by any combination of letters, digits, plus (+), period (.), or hyphen (-).
(def SCHEME-PATTERN #"[a-zA-Z][a-zA-Z\-\+\.\d]{1,7}")

; @constant (regex pattern)
;
; @description
; The subdomain part ...
; ... is not necessary.
; ... can contain lowercase letters, uppercase letters and/or digits and hyphens (-).
; ... must be at least 1 character long!
; ... must end with a period (.) character!
; The domain part ...
; ... can contain lowercase letters, uppercase letters and/or digits and hyphens (-).
; ... must be at least 1 character long!
; ... must end with a period (.) character!
; The TLD part ...
; ... can contain lowercase and/or uppercase letters
; ... must be at least 1 character long!
; The trailing slash is allowed but not necessary!
(def DOMAIN-PATTERN #"([a-zA-Z\d-]+\.)?[a-zA-Z\d-]+\.[a-zA-Z]+/?")
