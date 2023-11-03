
(ns string.api
    (:require [string.check   :as check]
              [string.config  :as config]
              [string.convert :as convert]
              [string.core    :as core]
              [string.cut     :as cut]
              [string.dex     :as dex]
              [string.lines   :as lines]
              [string.replace :as replace]
              [string.trim    :as trim]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; string.check
(def blank?                     check/blank?)
(def nonblank?                  check/nonblank?)
(def abc?                       check/abc?)
(def length-min?                check/length-min?)
(def length-max?                check/length-max?)
(def length-between?            check/length-between?)
(def length?                    check/length?)
(def contains-part?             check/contains-part?)
(def contains-digit?            check/contains-digit?)
(def contains-lowercase-letter? check/contains-lowercase-letter?)
(def contains-uppercase-letter? check/contains-uppercase-letter?)
(def if-contains-part           check/if-contains-part)

; string.config
(def EMPTY-STRING config/EMPTY-STRING)
(def BREAK        config/BREAK)
(def TAB          config/TAB)
(def WHITE-SPACE  config/WHITE-SPACE)

; string.convert
(def to-integer     convert/to-integer)
(def to-capitalized convert/to-capitalized)
(def to-uppercase   convert/to-uppercase)
(def to-lowercase   convert/to-lowercase)

; string.core
(def abc               core/abc)
(def length            core/length)
(def get-nth-character core/get-nth-character)
(def multiply          core/multiply)
(def join              core/join)
(def cover             core/cover)
(def split             core/split)
(def prefix            core/prefix)
(def suffix            core/suffix)
(def prepend           core/prepend)
(def append            core/append)
(def insert-part       core/insert-part)
(def count-occurences  core/count-occurences)
(def min-occurence?    core/min-occurence?)
(def max-occurence?    core/max-occurence?)
(def ends-with?        core/ends-with?)
(def not-ends-with?    core/not-ends-with?)
(def ends-with!        core/ends-with!)
(def not-ends-with!    core/not-ends-with!)
(def starts-with?      core/starts-with?)
(def not-starts-with?  core/not-starts-with?)
(def starts-with!      core/starts-with!)
(def not-starts-with!  core/not-starts-with!)
(def pass-with?        core/pass-with?)
(def not-pass-with?    core/not-pass-with?)

; string.cut
(def part                   cut/part)
(def cut                    cut/cut)
(def remove-part            cut/remove-part)
(def filter-characters      cut/filter-characters)
(def max-length             cut/max-length)
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

; string.dex
(def first-dex-of dex/first-dex-of)
(def last-dex-of  dex/last-dex-of)
(def nth-dex-of   dex/nth-dex-of)

; string.lines
(def remove-newlines lines/remove-newlines)
(def line-count      lines/line-count)
(def max-lines       lines/max-lines)

; string.replace
(def replace-part     replace/replace-part)
(def use-replacements replace/use-replacements)
(def use-replacement  replace/use-replacement)
(def use-nil          replace/use-nil)
(def use-placeholder  replace/use-placeholder)

; string.trim
(def trim          trim/trim)
(def trim-start    trim/trim-start)
(def trim-end      trim/trim-end)
(def trim-newlines trim/trim-newlines)
(def trim-gaps     trim/trim-gaps)
