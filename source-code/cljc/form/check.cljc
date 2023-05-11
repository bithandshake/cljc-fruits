
(ns form.check
    (:require [form.patterns :as patterns]
              [regex.api     :refer [re-match?]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn valid-pin?
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
  ; (valid-pin? "0042")
  ;
  ; @usage
  ; (valid-pin? "420069" 6)
  ;
  ; @example
  ; (valid-pin? "420069" 6)
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n]
   (valid-pin? n 4))

  ([n length]
   (let [pattern (patterns/pin-pattern length)]
        (re-match? (str n) pattern))))

(defn valid-password?
  ; @description
  ; Returns true if the given value is a valid password.
  ;
  ; Password qualified as valid if ...
  ; ... its length is in a certain domain.
  ; ... contains at least one uppercase letter.
  ; ... contains at least one lowercase letter.
  ; ... contains at least one digit.
  ;
  ; Allowed characters: ".-_!?#*"
  ;
  ; @param (*) n
  ; @param (integer)(opt) min
  ; Default: 8
  ; @param (integer)(opt) max
  ; Default: 32
  ;
  ; @usage
  ; (valid-password? "Password1")
  ;
  ; @usage
  ; (valid-password? "Password1" 6)
  ;
  ; @usage
  ; (valid-password? "Password1" 6 16)
  ;
  ; @example
  ; (valid-password? "Password1" 6 16)
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n]
   (valid-password? 8 32))

  ([n min]
   (valid-password? min 32))

  ([n min max]
   (let [pattern (patterns/password-pattern min max)]
        (re-match? (str n) pattern))))

(defn valid-email-address?
  ; @description
  ; Returns true if the given value is a valid email address.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (valid-email-address? "user@email.com")
  ;
  ; @example
  ; (valid-email-address? "user@email.com")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (let [pattern (patterns/email-address-pattern)]
       (re-match? (str n) pattern)))

(defn valid-phone-number?
  ; @description
  ; Returns true if the given value is a valid phone number.
  ;
  ; Phone number qualified as valid if ...
  ; ... its length is in a certain domain.
  ; ... its first letter is a plus sign.
  ; 
  ; @param (*) n
  ; @param (integer)(opt) min
  ; Default: 4
  ; @param (integer)(opt) max
  ; Default: 20
  ;
  ; @usage
  ; (valid-phone-number? "+36420001234")
  ;
  ; @usage
  ; (valid-phone-number? "+36420001234" 6)
  ;
  ; @usage
  ; (valid-phone-number? "+36420001234" 6 24)
  ;
  ; @example
  ; (valid-phone-number? "+36420001234" 6 24)
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n]
   (valid-phone-number? 4 20))

  ([n min]
   (valid-phone-number? min 20))

  ([n min max]
   (let [pattern (patterns/phone-number-pattern min max)]
        (re-match? (str n) pattern))))
