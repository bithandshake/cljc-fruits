
(ns audit.check
    (:require [audit.patterns :as patterns]
              [regex.api      :refer [re-match?]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ip-address-valid?
  ; @description
  ; Returns true if the given value is a valid IP address.
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

(defn pin-code-valid?
  ; @description
  ; Returns true if the given value is a valid PIN code.
  ;
  ; PIN code qualified as valid if ...
  ; ... only contains digits.
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

(defn password-valid?
  ; @description
  ; Returns true if the given value is a valid password.
  ;
  ; Password qualified as valid if ...
  ; ... its length is in a certain domain.
  ; ... contains at least one uppercase letter.
  ; ... contains at least one lowercase letter.
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
   (password-valid? 8 32))

  ([n min]
   (password-valid? min 32))

  ([n min max]
   (let [pattern (patterns/password-pattern min max)]
        (re-match? (str n) pattern))))

(defn email-address-valid?
  ; @description
  ; Returns true if the given value is a valid email address.
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

(defn phone-number-valid?
  ; @description
  ; Returns true if the given value is a valid phone number.
  ;
  ; Phone number qualified as valid if ...
  ; ... its length is in a certain domain.
  ; ... its first character is a plus sign.
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
   (phone-number-valid? 4 20))

  ([n min]
   (phone-number-valid? min 20))

  ([n min max]
   (let [pattern (patterns/phone-number-pattern min max)]
        (re-match? (str n) pattern))))
