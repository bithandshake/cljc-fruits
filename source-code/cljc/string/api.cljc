
(ns string.api
    (:refer-clojure :exclude [empty? repeat])
    (:require [string.apply   :as apply]
              [string.check   :as check]
              [string.config  :as config]
              [string.convert :as convert]
              [string.contain :as contain]
              [string.core    :as core]
              [string.cut     :as cut]
              [string.dex     :as dex]
              [string.filter  :as filter]
              [string.insert  :as insert]
              [string.indent  :as indent]
              [string.length  :as length]
              [string.lines   :as lines]
              [string.nth     :as nth]
              [string.order   :as order]
              [string.remove  :as remove]
              [string.replace :as replace]
              [string.search  :as search]
              [string.set     :as set]
              [string.trim    :as trim]
              [string.walk    :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; string.apply
(def apply-on-range apply/apply-on-range)

; string.check
(def empty?     check/empty?)
(def nonempty?  check/nonempty?)
(def blank?     check/blank?)
(def nonblank?  check/nonblank?)
(def lowercase? check/lowercase?)
(def uppercase? check/uppercase?)

; string.config
(def EMPTY-STRING config/EMPTY-STRING)
(def BREAK        config/BREAK)
(def NEWLINE      config/NEWLINE)
(def WHITE-SPACE  config/WHITE-SPACE)

; string.contain
(def contains-part?             contain/contains-part?)
(def contains-digit?            contain/contains-digit?)
(def contains-lowercase-letter? contain/contains-lowercase-letter?)
(def contains-uppercase-letter? contain/contains-uppercase-letter?)
(def if-contains-part           contain/if-contains-part)

; string.convert
(def to-integer     convert/to-integer)
(def to-capitalized convert/to-capitalized)
(def to-uppercase   convert/to-uppercase)
(def to-lowercase   convert/to-lowercase)

; string.core
(def cover             core/cover)
(def starts-with?      core/starts-with?)
(def ends-with?        core/ends-with?)
(def not-starts-with?  core/not-starts-with?)
(def not-ends-with?    core/not-ends-with?)
(def starts-with!      core/starts-with!)
(def ends-with!        core/ends-with!)
(def not-starts-with!  core/not-starts-with!)
(def not-ends-with!    core/not-ends-with!)
(def matches-with?     core/matches-with?)
(def not-matches-with? core/not-matches-with?)
(def starts-at?        core/starts-at?)
(def ends-at?          core/ends-at?)
(def not-starts-at?    core/not-starts-at?)
(def not-ends-at?      core/not-ends-at?)

; string.cut
(def keep-range             cut/keep-range)
(def cut-range              cut/cut-range)
(def before-first-occurence cut/before-first-occurence)
(def before-last-occurence  cut/before-last-occurence)
(def after-first-occurence  cut/after-first-occurence)
(def after-last-occurence   cut/after-last-occurence)
(def from-first-occurence   cut/from-first-occurence)
(def from-last-occurence    cut/from-last-occurence)
(def to-first-occurence     cut/to-first-occurence)
(def to-last-occurence      cut/to-last-occurence)

; string.dex
(def first-dex-of dex/first-dex-of)
(def last-dex-of  dex/last-dex-of)
(def nth-dex-of   dex/nth-dex-of)

; string.filter
(def filter-characters filter/filter-characters)

; string.indent
(def inline-position        indent/inline-position)
(def position-indent-length indent/position-indent-length)
(def fix-inline-position    indent/fix-inline-position)

; string.insert
(def prefix      insert/prefix)
(def suffix      insert/suffix)
(def prepend     insert/prepend)
(def append      insert/append)
(def insert-part insert/insert-part)

; string.length
(def length          length/length)
(def same-length?    length/same-length?)
(def length-min?     length/length-min?)
(def length-max?     length/length-max?)
(def length-between? length/length-between?)
(def length?         length/length?)
(def max-length      length/max-length)

; string.lines
(def containing-line lines/containing-line)
(def remove-line     lines/remove-line)
(def in-empty-line?  lines/in-empty-line?)
(def in-blank-line?  lines/in-blank-line?)
(def remove-newlines lines/remove-newlines)
(def line-count      lines/line-count)
(def max-lines       lines/max-lines)

; string.nth
(def first-character  nth/first-character)
(def second-character nth/second-character)
(def last-character   nth/last-character)
(def nth-character    nth/nth-character)

; string.order
(def abc? order/abc?)
(def abc  order/abc)

; string.remove
(def remove-part            remove/remove-part)
(def remove-first-occurence remove/remove-first-occurence)
(def remove-last-occurence  remove/remove-last-occurence)

; string.replace
(def replace-part     replace/replace-part)
(def use-replacements replace/use-replacements)
(def use-replacement  replace/use-replacement)
(def use-nil          replace/use-nil)
(def use-placeholder  replace/use-placeholder)

; string.search
(def count-occurences search/count-occurences)
(def min-occurence?   search/min-occurence?)
(def max-occurence?   search/max-occurence?)

; string.set
(def repeat set/repeat)
(def join   set/join)
(def split  set/split)

; string.trim
(def trim          trim/trim)
(def trim-start    trim/trim-start)
(def trim-end      trim/trim-end)
(def trim-newlines trim/trim-newlines)
(def trim-gaps     trim/trim-gaps)

; string.walk
(def walk walk/walk)
