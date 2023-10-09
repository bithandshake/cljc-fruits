
(ns audit.patterns)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn email-address-pattern
  ; @description
  ; Returns a regex pattern that matches with valid email addresses.
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
  ; Returns a regex pattern that matches with valid IP address.
  ;
  ; @usage
  ; (ip-address-pattern)
  ;
  ; @example
  ; (ip-address-pattern)
  ; =>
  ; #"[\d]{1,3}\.[\d]{1,3}\.[\d]{1,3}\.[\d]{1,3}"
  ;
  ; @return (regex pattern)
  []
  (re-pattern (str "[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}")))

(defn pin-code-pattern
  ; @description
  ; Returns a regex pattern that matches with valid PIN codes.
  ;
  ; PIN code qualified as valid if ...
  ; ... only contains digits.
  ; ... it has a certain length.
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

(defn password-pattern
  ; @description
  ; Returns a regex pattern that matches with valid passwords.
  ;
  ; Password qualified as valid if ...
  ; ... its length is in a certain domain.
  ; ... contains at least one uppercase letter.
  ; ... contains at least one lowercase letter.
  ; ... contains at least one digit.
  ;
  ; Accented characters and the following special characters are allowed: .-_!?#*
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
  ; Returns a regex pattern that matches with valid phone numbers.
  ;
  ; Phone number qualified as valid if ...
  ; ... its length is in a certain domain.
  ; ... its first letter is a "+" character.
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
