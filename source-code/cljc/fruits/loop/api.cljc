
(ns fruits.loop.api
    (:require [fruits.loop.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.loop.core)
(def <-walk            core/<-walk)
(def reduce-kv-indexed core/reduce-kv-indexed)
(def reduce-range      core/reduce-range)
(def reduce-indexed    core/reduce-indexed)
(def some-indexed      core/some-indexed)
(def do-indexed        core/do-indexed)
(def do-while          core/do-while)
(def reduce-pairs      core/reduce-pairs)
(def apply-pairs       core/apply-pairs)
