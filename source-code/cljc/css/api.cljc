
(ns css.api
    (:require [css.candy :as candy]
              [css.parse :as parse]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; css.candy
(def calc               candy/calc)
(def ms                 candy/ms)
(def s                  candy/s)
(def percent            candy/percent)
(def px                 candy/px)
(def rotate             candy/rotate)
(def rotate-x           candy/rotate-x)
(def rotate-y           candy/rotate-y)
(def rotate-z           candy/rotate-z)
(def scale              candy/scale)
(def translate          candy/translate)
(def translate-x        candy/translate-x)
(def translate-y        candy/translate-y)
(def translate-z        candy/translate-z)
(def url                candy/url)
(def value              candy/value)
(def var                candy/var)
(def var-key            candy/var-key)
(def horizontal-padding candy/horizontal-padding)
(def vertical-padding   candy/vertical-padding)
(def horizontal-margin  candy/horizontal-margin)
(def vertical-margin    candy/vertical-margin)
(def linear-gradient    candy/linear-gradient)

; css.parse
(def unparse parse/unparse)
(def parse   parse/parse)
