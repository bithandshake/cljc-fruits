
(ns alphabet.api
    (:require [alphabet.abc     :as abc]
              [alphabet.convert :as convert]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; alphabet.abc
(def NATO abc/NATO)
(def EN   abc/EN)

; alphabet.convert
(def integer->lowercase convert/integer->lowercase)
(def integer->uppercase convert/integer->uppercase)
