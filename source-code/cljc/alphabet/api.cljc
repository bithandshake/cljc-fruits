
(ns alphabet.api
    (:require [alphabet.abc     :as abc]
              [alphabet.convert :as convert]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (alphabet.abc)
(def GREEK abc/GREEK)
(def NATO  abc/NATO)
(def EN    abc/EN)

; @redirect (alphabet.convert)
(def integer->lowercase convert/integer->lowercase)
(def integer->uppercase convert/integer->uppercase)
