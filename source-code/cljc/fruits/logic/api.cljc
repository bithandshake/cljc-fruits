
(ns fruits.logic.api
    (:require [fruits.logic.check :as check]
              [fruits.logic.swap :as swap]
              [fruits.logic.condition :as condition]
              [fruits.logic.equal :as equal]
              [fruits.logic.gates :as gates]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.logic.check/*)
(def not-true?  check/not-true?)
(def not-false? check/not-false?)

; @redirect (fruits.logic.condition/*)
(def if-or  condition/if-or)
(def if-and condition/if-and)

; @redirect (fruits.logic.equal/*)
(def =?    equal/=?)
(def not=? equal/not=?)
(def nor=  equal/nor=)
(def or=   equal/or=)

; @redirect (fruits.logic.gates/*)
(def nor gates/nor)

; @redirect (fruits.logic.swap/*)
(def swap swap/swap)
