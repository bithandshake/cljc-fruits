
(ns fruits.audit.patterns
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn email-address-pattern
  ; @description
  ; Returns a regex pattern that matches valid email addresses.
  ;
  ; @usage
  ; (email-address-pattern)
  ;
  ; @example
  ; (email-address-pattern)
  ; =>
  ; #"[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?"
  ;
  ; @return (regex pattern)
  []
  (re-pattern "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?"))

(defn ip-address-pattern
  ; @description
  ; Returns a regex pattern that matches valid IP addresses.
  ;
  ; @usage
  ; (ip-address-pattern)
  ;
  ; @example
  ; (ip-address-pattern)
  ; =>
  ; #"^(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})$"
  ;
  ; @return (regex pattern)
  []
  (re-pattern (str "^(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})$")))

(defn latin-name-pattern
  ; @description
  ; - Returns a regex pattern that matches valid latin names.
  ; - A latin name is declared as valid if ...
  ;   ... its length is in a certain domain,
  ;   ... contains only latin characters, accented latin characters, digits,
  ;       underscrores, hyphens, apostrophes, periods and spaces.
  ;
  ; @param (integer)(opt) min
  ; Default: 2
  ; @param (integer)(opt) max
  ; Default: 32
  ;
  ; @usage
  ; (latin-name-pattern)
  ;
  ; @usage
  ; (latin-name-pattern 3)
  ;
  ; @usage
  ; (latin-name-pattern 3 18)
  ;
  ; @example
  ; (latin-name-pattern 3 18)
  ; =>
  ; #"[A-Za-zÀ-Ýà-ý0-9_\-\']{3,18}"
  ;
  ; @return (regex pattern)
  ([]
   (latin-name-pattern 2 32))

  ([min]
   (latin-name-pattern min 32))

  ([min max]
   (re-pattern (str "[A-Za-zÀ-Ýà-ý0-9\\_\\-\\'\\.\\s]{"min","max"}"))))

(defn password-pattern
  ; @description
  ; - Returns a regex pattern that matches valid passwords.
  ; - A password is declared as valid if ...
  ;   ... its length is in a certain domain,
  ;   ... contains at least one uppercase letter,
  ;   ... contains at least one lowercase letter,
  ;   ... contains at least one digit.
  ; - Accented characters and the following special characters are allowed: .-_!?#*
  ;
  ; @param (integer)(opt) min
  ; Default: 8
  ; @param (integer)(opt) max
  ; Default: 32
  ;
  ; @usage
  ; (password-pattern)
  ;
  ; @usage
  ; (password-pattern 6)
  ;
  ; @usage
  ; (password-pattern 6 16)
  ;
  ; @example
  ; (password-pattern 6 16)
  ; =>
  ; #"^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d\.\-\_\!\?\#\*]{6,16}$"
  ;
  ; @return (regex pattern)
  ([]
   (password-pattern 8 32))

  ([min]
   (password-pattern min 32))

  ([min max]
   (re-pattern (str "^(?=.*[a-ÿ])(?=.*[A-Ÿ])(?=.*\\d)[a-ÿA-Ÿ\\d\\.\\-\\_\\!\\?\\#\\*]{"min","max"}$"))))

(defn phone-number-pattern
  ; @description
  ; - Returns a regex pattern that matches valid phone numbers.
  ; - A phone number is declared as valid if ...
  ;   ... its length is in a certain domain,
  ;   ... its first letter is a '+' character.
  ;
  ; @param (integer)(opt) min
  ; Default: 4
  ; @param (integer)(opt) max
  ; Default: 20
  ;
  ; @usage
  ; (phone-number-pattern)
  ;
  ; @usage
  ; (phone-number-pattern 6)
  ;
  ; @usage
  ; (phone-number-pattern 6 24)
  ;
  ; @example
  ; (phone-number-pattern 6 24)
  ; =>
  ; #"\+\d{6,24}"
  ;
  ; @return (regex pattern)
  ([]
   (phone-number-pattern 4 20))

  ([min]
   (phone-number-pattern min 20))

  ([min max]
   (re-pattern (str "\\+\\d{"min","max"}"))))

(defn pin-code-pattern
  ; @description
  ; - Returns a regex pattern that matches valid PIN codes.
  ; - A PIN code is declared as valid if ...
  ;   ... only contains digits,
  ;   ... it has a certain length.
  ;
  ; @param (integer)(opt) length
  ; Default: 4
  ;
  ; @usage
  ; (pin-code-pattern)
  ;
  ; @usage
  ; (pin-code-pattern 6)
  ;
  ; @example
  ; (pin-code-pattern 6)
  ; =>
  ; #"[\d]{6,6}"
  ;
  ; @return (regex pattern)
  ([]
   (pin-code-pattern 4))

  ([length]
   (re-pattern (str "[\\d]{"length","length"}"))))

(defn security-code-pattern
  ; @description
  ; - Returns a regex pattern that matches valid security codes.
  ; - A security code is declared as valid if ...
  ;   ... only contains digits,
  ;   ... it has a certain length.
  ;
  ; @param (integer)(opt) length
  ; Default: 6
  ;
  ; @usage
  ; (security-code-pattern)
  ;
  ; @usage
  ; (security-code-pattern 8)
  ;
  ; @example
  ; (security-code-pattern 8)
  ; =>
  ; #"[\d]{8,8}"
  ;
  ; @return (regex pattern)
  ([]
   (security-code-pattern 6))

  ([length]
   (re-pattern (str "[\\d]{"length","length"}"))))

(defn user-agent-pattern
  ; @description
  ; Returns a regex pattern that matches valid user agent strings.
  ;
  ; @param (strings in vector)(opt) allowed-agents
  ; Default: ["Mozilla" "Chrome" "Safari"]
  ;
  ; @usage
  ; (user-agent-pattern)
  ;
  ; @usage
  ; (user-agent-pattern ["Mozilla" "Chrome" "Safari" "My-agent"])
  ;
  ; @example
  ; (user-agent-pattern ["Mozilla" "Chrome" "Safari" "My-agent"])
  ; =>
  ; #""
  ;
  ; @return (regex pattern)
  ([]
   (user-agent-pattern ["Mozilla" "Chrome" "Safari"]))

  ([allowed-agents]
   (as-> allowed-agents % (string/join % "|")
                          (str "^("%")")
                          (re-pattern %))))

(defn username-pattern
  ; @description
  ; - Returns a regex pattern that matches valid usernames.
  ; - A username is declared as valid if ...
  ;   ... its length is in a certain domain,
  ;   ... contains only latin characters, digits, underscrores and hyphens.
  ;
  ; @param (integer)(opt) min
  ; Default: 4
  ; @param (integer)(opt) max
  ; Default: 16
  ;
  ; @usage
  ; (username-pattern)
  ;
  ; @usage
  ; (username-pattern 6)
  ;
  ; @usage
  ; (username-pattern 6 24)
  ;
  ; @example
  ; (username-pattern 6 24)
  ; =>
  ; #"[A-Za-z0-9_\-]{6,24}"
  ;
  ; @return (regex pattern)
  ([]
   (username-pattern 4 16))

  ([min]
   (username-pattern min 16))

  ([min max]
   (re-pattern (str "[A-Za-z0-9\\_\\-]{"min","max"}"))))
