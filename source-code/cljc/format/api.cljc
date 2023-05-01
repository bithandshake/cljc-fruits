
(ns format.api
    (:require [format.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; format.core
(def sign-number          core/sign-number)
(def group-number         core/group-number)
(def leading-zeros        core/leading-zeros)
(def remove-leading-zeros core/remove-leading-zeros)
(def trailing-zeros       core/trailing-zeros)
(def decimals             core/decimals)
(def round                core/round)
(def inc-version          core/inc-version)
