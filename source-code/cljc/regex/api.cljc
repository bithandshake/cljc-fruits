
(ns regex.api
    (:require [regex.core :as core]
              [regex.cut  :as cut]
              [regex.dex  :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; regex.core
(def re-match         core/re-match)
(def re-match?        core/re-match?)
(def re-mismatch?     core/re-mismatch?)
(def ends-with?       core/ends-with?)
(def not-ends-with?   core/not-ends-with?)
(def not-ends-with!   core/not-ends-with!)
(def starts-with?     core/starts-with?)
(def not-starts-with? core/not-starts-with?)
(def not-starts-with! core/not-starts-with!)

; regex.cut
(def before-first-occurence cut/before-first-occurence)
(def before-last-occurence  cut/before-last-occurence)
(def after-first-occurence  cut/after-first-occurence)
(def after-last-occurence   cut/after-last-occurence)
(def from-first-occurence   cut/from-first-occurence)
(def from-last-occurence    cut/from-last-occurence)
(def to-first-occurence     cut/to-first-occurence)
(def to-last-occurence      cut/to-last-occurence)
(def remove-first-occurence cut/remove-first-occurence)
(def remove-last-occurence  cut/remove-last-occurence)
(def between-occurences     cut/between-occurences)

; regex.dex
(def first-dex-of dex/first-dex-of)
(def last-dex-of  dex/last-dex-of)
(def nth-dex-of   dex/nth-dex-of)
