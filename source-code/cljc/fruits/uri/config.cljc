
(ns fruits.uri.config)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (regex pattern)
(def PORT-PATTERN #"[\d]{1,8}")

; @constant (regex pattern)
; https://en.wikipedia.org/wiki/Uniform_Resource_Identifier
; A scheme consisting of a sequence of characters beginning with a letter and followed
; by any combination of letters, digits, plus (+), period (.), or hyphen (-).
(def SCHEME-PATTERN #"[a-zA-Z\-\+\.\d]{1,8}")

; @constant (regex pattern)
; BUG#6610
; If the scheme part is missing from the URI, the part before the first ":"
; character might be a hostname, and the part right after the ':' character
; might be a port!
; To determine whether the part before the first ':' character is a scheme
; or a hostname the functions use a strict scheme pattern, which cannot be
; matched with a hostname.
(def STRICT-SCHEME-PATTERN #"[a-zA-Z\-\d]{1,8}")

; @constant (regex pattern)
;
; The subdomain part ...
; ... is not necessary.
; ... can contains lowercase letters, uppercase letters and/or digits and the '-' char.
; ... must be min. 1 char long!
; ... must end with a '.' char!
; The domain part ...
; ... can contains lowercase letters, uppercase letters and/or digits and the '-' char.
; ... must be min. 1 char long!
; ... must end with a '.' char!
; The TLD part ...
; ... can contains lowercase and/or uppercase letters
; ... must be min. 1 char long!
; The trailing slash is allowed but not necessary!
(def DOMAIN-PATTERN #"([a-zA-Z\d-]{1,}\.)?[a-zA-Z\d-]{1,}\.[a-zA-Z]{1,}/?")
