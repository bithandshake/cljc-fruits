
(ns form.patterns)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn pin-pattern
  ; @description
  ; Returns a regex pattern that could be used for validate user input PIN codes.
  ;
  ; PIN code qualified as valid if ...
  ; ... only contains digits.
  ; ... it has a certain length.
  ;
  ; @param (integer)(opt) length
  ; Default: 4
  ;
  ; @usage
  ; (pin-pattern)
  ;
  ; @usage
  ; (pin-pattern 6)
  ;
  ; @example
  ; (pin-pattern 6)
  ; =>
  ; #"[\d]{6,6}"
  ;
  ; @return (regex pattern)
  ([]
   (pin-pattern 4))

  ([length]
   (re-pattern (str "[\\d]{"length","length"}"))))

(defn password-pattern
  ; @description
  ; Returns a regex pattern that could be used for validate user input passwords.
  ;
  ; Password qualified as valid if ...
  ; ... its length is in a certain domain.
  ; ... contains at least one uppercase letter.
  ; ... contains at least one lowercase letter.
  ; ... contains at least one digit.
  ;
  ; Allowed characters: ".-_!?#*"
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
   (re-pattern (str "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d\\.\\-\\_\\!\\?\\#\\*]{"min","max"}$"))))

(defn email-address-pattern
  ; @description
  ; Returns a regex pattern that could be used for validate user input email addresses.
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

(defn phone-number-pattern
  ; @description
  ; Returns a regex pattern that could be used for validate user input phone numbers.
  ;
  ; Phone number qualified as valid if ...
  ; ... its length is in a certain domain.
  ; ... its first letter is a plus sign.
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
