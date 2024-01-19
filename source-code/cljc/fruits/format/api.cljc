
(ns fruits.format.api
    (:require [fruits.format.cover   :as cover]
              [fruits.format.number  :as number]
              [fruits.format.version :as version]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.format.cover/*)
(def cover-email-address cover/cover-email-address)

; @redirect (fruits.format.number/*)
(def sign-number          number/sign-number)
(def group-number         number/group-number)
(def leading-zeros        number/leading-zeros)
(def remove-leading-zeros number/remove-leading-zeros)
(def trailing-zeros       number/trailing-zeros)
(def decimals             number/decimals)
(def round                number/round)

; @redirect (fruits.format.version/*)
(def inc-version version/inc-version)
