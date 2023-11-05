
(ns seqable.api
    (:require [seqable.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; seqable.check
(def nonseqable? check/nonseqable?)
(def nonempty?   check/nonempty?)
