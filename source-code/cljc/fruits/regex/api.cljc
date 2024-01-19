
(ns fruits.regex.api
    (:require [fruits.regex.bounds  :as bounds]
              [fruits.regex.check   :as check]
              [fruits.regex.cut     :as cut]
              [fruits.regex.dex     :as dex]
              [fruits.regex.match   :as match]
              [fruits.regex.pattern :as pattern]
              [fruits.regex.remove  :as remove]
              [fruits.regex.replace :as replace]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.regex.bounds/*)
(def starts-with?     bounds/starts-with?)
(def ends-with?       bounds/ends-with?)
(def not-starts-with? bounds/not-starts-with?)
(def not-ends-with?   bounds/not-ends-with?)
(def not-starts-with! bounds/not-starts-with!)
(def not-ends-with!   bounds/not-ends-with!)
(def starts-at?       bounds/starts-at?)
(def ends-at?         bounds/ends-at?)
(def not-starts-at?   bounds/not-starts-at?)
(def not-ends-at?     bounds/not-ends-at?)

; @redirect (fruits.regex.check/*)
(def pattern? check/pattern?)

; @redirect (fruits.regex.cut/*)
(def before-first-match cut/before-first-match)
(def before-last-match  cut/before-last-match)
(def after-first-match  cut/after-first-match)
(def after-last-match   cut/after-last-match)
(def from-first-match   cut/from-first-match)
(def from-last-match    cut/from-last-match)
(def to-first-match     cut/to-first-match)
(def to-last-match      cut/to-last-match)

; @redirect (fruits.regex.dex/*)
(def first-dex-of dex/first-dex-of)
(def last-dex-of  dex/last-dex-of)
(def nth-dex-of   dex/nth-dex-of)

; @redirect (fruits.regex.match/*)
(def re-count     match/re-count)
(def re-return    match/re-return)
(def re-first     match/re-first)
(def re-last      match/re-last)
(def re-all       match/re-all)
(def re-match?    match/re-match?)
(def re-mismatch? match/re-mismatch?)
(def re-from      match/re-from)
(def re-to        match/re-to)

; @redirect (fruits.regex.pattern/*)
(def join-pattern pattern/join-pattern)

; @redirect (fruits.regex.remove/*)
(def remove-first-match remove/remove-first-match)
(def remove-last-match  remove/remove-last-match)

; @redirect (fruits.regex.replace/*)
(def replace-match replace/replace-match)
