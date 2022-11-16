
(ns hash.core
    (:require #?(:clj [buddy.core.mac    :as mac])
              #?(:clj [buddy.core.codecs :as codecs])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn hmac-sha256
  ; @param (string) n
  ; @param (string) secret-key
  ;
  ; @usage
  ; (hmac-sha256 "My text" "my-secret-key")
  ;
  ; @return (hex string)
  [n secret-key]
  #?(:clj (-> n (mac/hash {:key secret-key :alg :hmac+sha256})
                (codecs/bytes->hex))))
