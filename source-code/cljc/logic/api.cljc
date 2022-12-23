
(ns logic.api
    (:require [logic.core :as core]
              [logic.type :as type]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; logic.core
(def =?     core/=?)
(def not=?  core/not=?)
(def if-or  core/if-or)
(def if-and core/if-and)
(def nor    core/nor)
(def or=    core/or=)
(def swap   core/swap)

; logic.type
(def nonfalse? type/nonfalse?)
(def nontrue?  type/nontrue?)
