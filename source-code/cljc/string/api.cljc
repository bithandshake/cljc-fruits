
(ns string.api
    (:require [string.check   :as check]
              [string.apply   :as apply]
              [string.config  :as config]
              [string.convert :as convert]
              [string.core    :as core]
              [string.cursor  :as cursor]
              [string.cut     :as cut]
              [string.dex     :as dex]
              [string.length  :as length]
              [string.lines   :as lines]
              [string.replace :as replace]
              [string.trim    :as trim]
              [string.walk    :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; string.apply
(def apply-on-part apply/apply-on-part)

; string.check
(def blank?                     check/blank?)
(def nonblank?                  check/nonblank?)
(def abc?                       check/abc?)
(def contains-part?             check/contains-part?)
(def contains-digit?            check/contains-digit?)
(def lowercase?                 check/lowercase?)
(def uppercase?                 check/uppercase?)
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
(def abc              core/abc)
(def first-character  core/first-character)
(def second-character core/second-character)
(def last-character   core/last-character)
(def nth-character    core/nth-character)
(def multiply         core/multiply)
(def join             core/join)
(def cover            core/cover)
(def split            core/split)
(def prefix           core/prefix)
(def suffix           core/suffix)
(def prepend          core/prepend)
(def append           core/append)
(def insert-part      core/insert-part)
(def count-occurences core/count-occurences)
(def min-occurence?   core/min-occurence?)
(def max-occurence?   core/max-occurence?)
(def ends-with?       core/ends-with?)
(def not-ends-with?   core/not-ends-with?)
(def ends-with!       core/ends-with!)
(def not-ends-with!   core/not-ends-with!)
(def starts-with?     core/starts-with?)
(def not-starts-with? core/not-starts-with?)
(def starts-with!     core/starts-with!)
(def not-starts-with! core/not-starts-with!)
(def pass-with?       core/pass-with?)
(def not-pass-with?   core/not-pass-with?)

; string.cursor
(def cursor-in-bounds?     cursor/cursor-in-bounds?)
(def cursor-out-of-bounds? cursor/cursor-out-of-bounds?)

; string.cut
(def part                   cut/part)
(def cut                    cut/cut)
(def remove-part            cut/remove-part)
(def filter-characters      cut/filter-characters)
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
(def dex-in-bounds?     dex/dex-in-bounds?)
(def dex-out-of-bounds? dex/dex-out-of-bounds?)
(def first-dex-of       dex/first-dex-of)
(def last-dex-of        dex/last-dex-of)
(def nth-dex-of         dex/nth-dex-of)

; string.length
(def length          length/length)
(def same-length?    length/same-length?)
(def length-min?     length/length-min?)
(def length-max?     length/length-max?)
(def length-between? length/length-between?)
(def length?         length/length?)
(def max-length      length/max-length)

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

; string.walk
(def walk walk/walk)
