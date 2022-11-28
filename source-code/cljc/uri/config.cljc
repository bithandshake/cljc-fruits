
(ns uri.config)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (regex)
;
; The protocol part ...
; ... is not necessary.
; ... can contain lowercase and/or uppercase letters
; ... has to be min. 1, but max. 5 char long!
; ... must ends with "://"!
; The "www." part is not necessary!
; The subdomain part ...
; ... is not necessary.
; ... can contain lowercase letters, uppercase letters and/or digits and the "-" char.
; ... has to be min. 1 char long!
; ... must ends with a "." char!
; The domain part ...
; ... can contain lowercase letters, uppercase letters and/or digits and the "-" char.
; ... has to be min. 1 char long!
; ... must ends with a "." char!
; The TLD part can contain lowercase and/or uppercase letters (min. 1 char.)
; Trailing slash allowed but not necessary
(def DOMAIN-PATTERN #"([a-zA-Z]{1,5}://)?(www.)?([a-zA-Z\d-]{1,}\.)?[a-zA-Z\d-]{1,}\.[a-zA-Z]{1,}/?")

; @constant (regex)
;
; The subdomain part ...
; ... is not necessary .
; ... can contain lowercase letters, uppercase letters and/or digits and the "-" char.
; ... has to be min. 1 char long!
; ... must ends with a "." char!
; The domain part ...
; ... can contain lowercase letters, uppercase letters and/or digits and the "-" char.
; ... has to be min. 1 char long!
; ... must ends with a "." char!
; The TLD part ...
; ... can contain lowercase and/or uppercase letters
; ... has to be min. 1 char long!
; The trailing slash is allowed but not necessary!
(def STRICT-DOMAIN-PATTERN #"([a-zA-Z\d-]{1,}\.)?[a-zA-Z\d-]{1,}\.[a-zA-Z]{1,}/?")

; @constant (regex)
;
; The protocol ...
; ... can contain lowercase and/or uppercase letters.
; ... has to be min. 1, but max. 5 char long!
; ... must ends with "://"!
(def PROTOCOL-PATTERN #"[a-zA-Z]{1,5}://")
