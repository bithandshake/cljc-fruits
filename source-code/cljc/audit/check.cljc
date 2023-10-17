
(ns audit.check
    (:require [audit.patterns :as patterns]
              [regex.api      :refer [re-match?]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn email-address-valid?
  ; @description
  ; Returns TRUE if the given value is a valid email address.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (email-address-valid? "user@email.com")
  ;
  ; @example
  ; (email-address-valid? "user@email.com")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (let [pattern (patterns/email-address-pattern)]
       (re-match? (str n) pattern)))

(defn ip-address-valid?
  ; @description
  ; Returns TRUE if the given value is a valid IP address.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (ip-address-valid? "0.0.0.0")
  ;
  ; @example
  ; (ip-address-valid? "0.0.0.0")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (let [pattern (patterns/ip-address-pattern)]
       (re-match? (str n) pattern)))

(defn latin-name-valid?
  ; @description
  ; Returns TRUE if the given value is a valid latin name.
  ;
  ; Latin name is declared as valid if ...
  ; ... its length is in a certain domain,
  ; ... contains only latin characters, accented latin characters, digits,
  ;     underscrores, hyphens, apostrophes, periods and spaces.
  ;
  ; @param (integer)(opt) min
  ; Default: 2
  ; @param (integer)(opt) max
  ; Default: 32
  ;
  ; @usage
  ; (latin-name-valid? "John O'Reilly")
  ;
  ; @example
  ; (latin-name-valid? "John O'Reilly")
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n]
   (latin-name-valid? n 2 32))

  ([n min]
   (latin-name-valid? n min 32))

  ([n min max]
   (let [pattern (patterns/latin-name-pattern min max)]
        (re-match? (str n) pattern))))

(defn password-valid?
  ; @description
  ; Returns TRUE if the given value is a valid password.
  ;
  ; Password is declared as valid if ...
  ; ... its length is in a certain domain,
  ; ... contains at least one uppercase letter,
  ; ... contains at least one lowercase letter,
  ; ... contains at least one digit.
  ;
  ; Accented characters and the following special characters are allowed: .-_!?#*
  ;
  ; @param (*) n
  ; @param (integer)(opt) min
  ; Default: 8
  ; @param (integer)(opt) max
  ; Default: 32
  ;
  ; @usage
  ; (password-valid? "Password1")
  ;
  ; @usage
  ; (password-valid? "Password1" 6)
  ;
  ; @usage
  ; (password-valid? "Password1" 6 16)
  ;
  ; @example
  ; (password-valid? "Password1" 6 16)
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n]
   (password-valid? n 8 32))

  ([n min]
   (password-valid? n min 32))

  ([n min max]
   (let [pattern (patterns/password-pattern min max)]
        (re-match? (str n) pattern))))

(defn phone-number-valid?
  ; @description
  ; Returns TRUE if the given value is a valid phone number.
  ;
  ; Phone number is declared as valid if ...
  ; ... its length is in a certain domain,
  ; ... its first character is a "+" character.
  ;
  ; @param (*) n
  ; @param (integer)(opt) min
  ; Default: 4
  ; @param (integer)(opt) max
  ; Default: 20
  ;
  ; @usage
  ; (phone-number-valid? "+36420001234")
  ;
  ; @usage
  ; (phone-number-valid? "+36420001234" 6)
  ;
  ; @usage
  ; (phone-number-valid? "+36420001234" 6 24)
  ;
  ; @example
  ; (phone-number-valid? "+36420001234" 6 24)
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n]
   (phone-number-valid? n 4 20))

  ([n min]
   (phone-number-valid? n min 20))

  ([n min max]
   (let [pattern (patterns/phone-number-pattern min max)]
        (re-match? (str n) pattern))))

(defn pin-code-valid?
  ; @description
  ; Returns TRUE if the given value is a valid PIN code.
  ;
  ; PIN code is declared as valid if ...
  ; ... only contains digits,
  ; ... it has a certain length.
  ;
  ; @param (*) n
  ; @param (integer)(opt) length
  ; Default: 4
  ;
  ; @usage
  ; (pin-code-valid? "0042")
  ;
  ; @usage
  ; (pin-code-valid? "420069" 6)
  ;
  ; @example
  ; (pin-code-valid? "420069" 6)
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n]
   (pin-code-valid? n 4))

  ([n length]
   (let [pattern (patterns/pin-code-pattern length)]
        (re-match? (str n) pattern))))

(defn security-code-valid?
  ; @description
  ; Returns TRUE if the given value is a valid security code.
  ;
  ; Security code is declared as valid if ...
  ; ... only contains digits,
  ; ... it has a certain length.
  ;
  ; @param (*) n
  ; @param (integer)(opt) length
  ; Default: 6
  ;
  ; @usage
  ; (security-code-valid? "004269")
  ;
  ; @usage
  ; (security-code-valid? "420069" 6)
  ;
  ; @example
  ; (security-code-valid? "420069" 6)
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n]
   (security-code-valid? n 6))

  ([n length]
   (let [pattern (patterns/security-code-pattern length)]
        (re-match? (str n) pattern))))

(defn username-valid?
  ; @description
  ; Returns TRUE if the given value is a valid username.
  ;
  ; Username is declared as valid if ...
  ; ... its length is in a certain domain,
  ; ... contains only latin characters, digits, underscrores and hyphens.
  ;
  ; @param (*) n
  ; @param (integer)(opt) min
  ; Default: 4
  ; @param (integer)(opt) max
  ; Default: 16
  ;
  ; @usage
  ; (username-valid? "WinnieThePooh_69")
  ;
  ; @usage
  ; (username-valid? "WinnieThePooh_69" 6)
  ;
  ; @usage
  ; (username-valid? "WinnieThePooh_69" 6 32)
  ;
  ; @example
  ; (username-valid? "WinnieThePooh_69" 6 32)
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n]
   (username-valid? n 4 16))

  ([n min]
   (username-valid? n min 16))

  ([n min max]
   (let [pattern (patterns/username-pattern min max)]
        (re-match? (str n) pattern))))
