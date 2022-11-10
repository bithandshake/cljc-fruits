
(ns logical.api
    (:require [logical.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; logical.core
(def nonfalse? core/nonfalse?)
(def nontrue?  core/nontrue?)
(def =?        core/=?)
(def not=?     core/not=?)
(def if-or     core/if-or)
(def if-and    core/if-and)
(def nor       core/nor)
(def or=       core/or=)
(def swap      core/swap)
