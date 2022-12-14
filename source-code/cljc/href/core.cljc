
(ns href.core
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn email-address
  ; @param (string) email-address
  ;
  ; @example
  ; (email-address "Hello@my-site.com")
  ; =>
  ; "mailto:hello@my-site.com"
  ;
  ; @return (string)
  ([email-address]
   (str "mailto:" (string/to-lowercase email-address)))

  ([email-address subject]
   (str "mailto:"   (string/to-lowercase email-address)
        "?subject=" subject))

  ([email-address subject body]
   (str "mailto:"   (string/to-lowercase email-address)
        "?subject=" subject
        "&body="    body)))

(defn phone-number
  ; @param (integer or string) phone-number
  ;
  ; @example
  ; (phone-number "+3630 / 123 - 4567")
  ; =>
  ; "tel:+36301234567"
  ;
  ; @return (string)
  [phone-number]
  ; The 'phone-number' has to be converted to a string, because it's might be an integer!
  (if (-> phone-number str string/nonblank?)
      (str "tel:" (string/filter-characters phone-number ["+" "1" "2" "3" "4" "5" "6" "7" "8" "9" "0"]))))

(defn address
  ; @param (string) address
  ;
  ; @example
  ; (address "My City, My Address street 42.")
  ; =>
  ; "https://www.google.com/maps/search/?api=1&query=My%20City,%20My%20Address%20street%2042."
  ;
  ; @return (string)
  [address]
  (str "https://www.google.com/maps/search/?api=1&query=" (string/replace-part address " " "%20")))
