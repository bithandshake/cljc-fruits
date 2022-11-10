
(ns logical.api
    (:require [logical.core :as core]
              [logical.type :as type]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; logical.core
(def =?     core/=?)
(def not=?  core/not=?)
(def if-or  core/if-or)
(def if-and core/if-and)
(def nor    core/nor)
(def or=    core/or=)
(def swap   core/swap)

; logical.type
(def nonfalse? type/nonfalse?)
(def nontrue?  type/nontrue?)
