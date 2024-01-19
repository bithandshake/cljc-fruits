
(ns fruits.href.link
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn email-address
  ; @description
  ; Converts the given email address into 'mailto' link.
  ;
  ; @param (string) email-address
  ; @param (string)(opt) subject
  ; @param (string)(opt) body
  ;
  ; @usage
  ; (email-address "Hello@my-site.com")
  ; =>
  ; "mailto:hello@my-site.com"
  ;
  ; @usage
  ; (email-address "Hello@my-site.com" "My subject")
  ; =>
  ; "mailto:hello@my-site.com?subject=My%20subject"
  ;
  ; @usage
  ; (email-address "Hello@my-site.com" "My subject" "My body")
  ; =>
  ; "mailto:hello@my-site.com?subject=My%20subject&My%20body"
  ;
  ; @return (string)
  ([email-address]
   (str "mailto:" (string/to-lowercase email-address)))

  ([email-address subject]
   (str "mailto:"   (string/to-lowercase email-address)
        "?subject=" (string/replace-part subject " " "%20")))

  ([email-address subject body]
   (str "mailto:"   (string/to-lowercase email-address)
        "?subject=" (string/replace-part subject " " "%20")
        "&body="    (string/replace-part body    " " "%20"))))

(defn phone-number
  ; @description
  ; Converts the given phone number into 'tel' link.
  ;
  ; @param (integer or string) phone-number
  ;
  ; @usage
  ; (phone-number "+3630 / 123 - 4567")
  ; =>
  ; "tel:+36301234567"
  ;
  ; @return (string)
  [phone-number]
  ; The 'phone-number' must be converted to a string (it might be an integer)!
  (if (-> phone-number str string/not-empty?)
      (str "tel:" (string/filter-characters phone-number ["+" "1" "2" "3" "4" "5" "6" "7" "8" "9" "0"]))))

(defn google-maps-address
  ; @description
  ; Converts the given address into Google Maps link.
  ;
  ; @param (string) address
  ;
  ; @usage
  ; (google-maps-address "My City, My Address street 42.")
  ; =>
  ; "https://www.google.com/maps/search/?api=1&query=My%20City,%20My%20Address%20street%2042."
  ;
  ; @return (string)
  [address]
  (str "https://www.google.com/maps/search/?api=1&query=" (string/replace-part address " " "%20")))

(defn ftp-uri
  ; @description
  ; Converts the given URI into FTP address.
  ;
  ; @param (string) uri
  ;
  ; @usage
  ; (ftp-uri "my-website.com")
  ; =>
  ; "ftp://my-website.com"
  ;
  ; @usage
  ; (ftp-uri "https://my-website.com")
  ; =>
  ; "ftp://my-website.com"
  ;
  ; @return (string)
  [uri]
  (as-> uri % (string/after-first-occurence % "://" {:return? true})
              (str "ftp://"%)))

(defn http-uri
  ; @description
  ; Converts the given URI into HTTP address.
  ;
  ; @param (string) uri
  ;
  ; @usage
  ; (http-uri "my-website.com")
  ; =>
  ; "http://my-website.com"
  ;
  ; @usage
  ; (http-uri "ftp://my-website.com")
  ; =>
  ; "http://my-website.com"
  ;
  ; @return (string)
  [uri]
  (as-> uri % (string/after-first-occurence % "://" {:return? true})
              (str "http://"%)))

(defn https-uri
  ; @description
  ; Converts the given URI into HTTPS address.
  ;
  ; @param (string) uri
  ;
  ; @usage
  ; (https-uri "my-website.com")
  ; =>
  ; "https://my-website.com"
  ;
  ; @usage
  ; (https-uri "ftp://my-website.com")
  ; =>
  ; "https://my-website.com"
  ;
  ; @return (string)
  [uri]
  (as-> uri % (string/after-first-occurence % "://" {:return? true})
              (str "https://"%)))
