
(ns regex.api
    (:require [regex.check   :as check]
              [regex.core    :as core]
              [regex.cut     :as cut]
              [regex.dex     :as dex]
              [regex.match   :as match]
              [regex.remove  :as remove]
              [regex.replace :as replace]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; regex.check
(def pattern? check/pattern?)

; regex.core
(def starts-with?     core/starts-with?)
(def ends-with?       core/ends-with?)
(def not-starts-with? core/not-starts-with?)
(def not-ends-with?   core/not-ends-with?)
(def not-starts-with! core/not-starts-with!)
(def not-ends-with!   core/not-ends-with!)
(def starts-at?       core/starts-at?)
(def ends-at?         core/ends-at?)
(def not-starts-at?   core/not-starts-at?)
(def not-ends-at?     core/not-ends-at?)

; regex.cut
(def before-first-match cut/before-first-match)
(def before-last-match  cut/before-last-match)
(def after-first-match  cut/after-first-match)
(def after-last-match   cut/after-last-match)
(def from-first-match   cut/from-first-match)
(def from-last-match    cut/from-last-match)
(def to-first-match     cut/to-first-match)
(def to-last-match      cut/to-last-match)

; regex.dex
(def first-dex-of dex/first-dex-of)
(def last-dex-of  dex/last-dex-of)
(def nth-dex-of   dex/nth-dex-of)

; regex.match
(def re-count     match/re-count)
(def re-first     match/re-first)
(def re-last      match/re-last)
(def re-match     match/re-match)
(def re-match?    match/re-match?)
(def re-mismatch? match/re-mismatch?)
(def re-from      match/re-from)
(def re-to        match/re-to)

; regex.remove
(def remove-first-match remove/remove-first-match)
(def remove-last-match  remove/remove-last-match)

; regex.replace
(def replace-match replace/replace-match)
