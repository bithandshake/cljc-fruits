
(ns logic.api
    (:require [logic.check :as check]
              [logic.core  :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; logic.check
(def nontrue?  check/nontrue?)
(def nonfalse? check/nonfalse?)

; logic.core
(def =?     core/=?)
(def not=?  core/not=?)
(def if-or  core/if-or)
(def if-and core/if-and)
(def nor    core/nor)
(def nor=   core/nor=)
(def or=    core/or=)
(def swap   core/swap)
