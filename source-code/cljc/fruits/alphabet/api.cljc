
(ns fruits.alphabet.api
    (:require [fruits.alphabet.abc     :as abc]
              [fruits.alphabet.convert :as convert]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.alphabet.abc/*)
(def GREEK abc/GREEK)
(def NATO  abc/NATO)
(def EN    abc/EN)

; @redirect (fruits.alphabet.convert/*)
(def integer->lowercase convert/integer->lowercase)
(def integer->uppercase convert/integer->uppercase)

 
