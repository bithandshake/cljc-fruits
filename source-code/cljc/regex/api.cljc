
(ns regex.api
    (:require [regex.core    :as core]
              [regex.cut     :as cut]
              [regex.dex     :as dex]
              [regex.match   :as match]
              [regex.remove  :as remove]
              [regex.replace :as replace]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; regex.core
(def re-count         core/re-count)
(def re-first         core/re-first)
(def re-last          core/re-last)
(def re-match         core/re-match)
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

; regex.dex
(def first-dex-of dex/first-dex-of)
(def last-dex-of  dex/last-dex-of)
(def nth-dex-of   dex/nth-dex-of)

; regex.match
(def re-match?    match/re-match?)
(def re-mismatch? match/re-mismatch?)

; regex.remove
(def remove-first-match remove/remove-first-match)
(def remove-last-match  remove/remove-last-match)

; regex.replace
(def replace-match replace/replace-match)
