
(ns format.api
    (:require [format.cover   :as cover]
              [format.number  :as number]
              [format.version :as version]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; format.cover
(def cover-email-address cover/cover-email-address)

; format.number
(def sign-number          number/sign-number)
(def group-number         number/group-number)
(def leading-zeros        number/leading-zeros)
(def remove-leading-zeros number/remove-leading-zeros)
(def trailing-zeros       number/trailing-zeros)
(def decimals             number/decimals)
(def round                number/round)

; format.version
(def inc-version version/inc-version)
