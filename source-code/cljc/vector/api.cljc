
(ns vector.api
    (:require [vector.check   :as check]
              [vector.compare :as compare]
              [vector.convert :as convert]
              [vector.core    :as core]
              [vector.dex     :as dex]
              [vector.filter  :as filter]
              [vector.match   :as match]
              [vector.move    :as move]
              [vector.nth     :as nth]
              [vector.range   :as range]
              [vector.remove  :as remove]
              [vector.sort    :as sort]
              [vector.type    :as type]
              [vector.walk    :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; vector.check
(def contains-item?     check/contains-item?)
(def not-contains-item? check/not-contains-item?)
(def min?               check/min?)
(def max?               check/max?)
(def longer?            check/longer?)
(def count?             check/count?)
(def item-last?         check/item-last?)
(def item-first?        check/item-first?)

; vector.compare
(def similars           compare/similars)
(def contains-similars? compare/contains-similars?)
(def difference         compare/difference)
(def keep-items         compare/keep-items)

; vector.convert
(def to-map convert/to-map)

; vector.core
(def repeat-item       core/repeat-item)
(def cons-item         core/cons-item)
(def cons-item-once    core/cons-item-once)
(def conj-item         core/conj-item)
(def conj-item-once    core/conj-item-once)
(def conj-some         core/conj-some)
(def concat-items      core/concat-items)
(def concat-items-once core/concat-items-once)
(def change-item       core/change-item)
(def inject-item       core/inject-item)
(def toggle-item       core/toggle-item)
(def prev-item         core/prev-item)
(def next-item         core/next-item)
(def last-item         core/last-item)
(def first-item        core/first-item)

; vector.dex
(def item-dex?          dex/item-dex?)
(def dex-out-of-bounds? dex/dex-out-of-bounds?)
(def dex-in-bounds?     dex/dex-in-bounds?)
(def dex-range          dex/dex-range)
(def dex-first?         dex/dex-first?)
(def dex-last?          dex/dex-last?)
(def last-dex           dex/last-dex)
(def next-dex           dex/next-dex)
(def inc-dex            dex/inc-dex)
(def prev-dex           dex/prev-dex)
(def dec-dex            dex/dec-dex)
(def match-dex          dex/match-dex)
(def item-last-dex      dex/item-last-dex)
(def item-first-dex     dex/item-first-dex)

; vector.filter
(def filter-items-by filter/filter-items-by)
(def first-filtered  filter/first-filtered)
(def last-filtered   filter/last-filtered)
(def nth-filtered    filter/nth-filtered)
(def filtered-count  filter/filtered-count)
(def filtered-count? filter/filtered-count?)

; vector.match
(def any-item-match?          match/any-item-match?)
(def all-items-match?         match/all-items-match?)
(def get-first-match-item     match/get-first-match-item)
(def get-first-match-item-dex match/get-first-match-item-dex)

; vector.move
(def move-item            move/move-item)
(def move-item-to-last    move/move-item-to-last)
(def move-item-to-first   move/move-item-to-first)
(def move-first-occurence move/move-first-occurence)

; vector.nth
(def nth-item            nth/nth-item)
(def remove-nth-item     nth/remove-nth-item)
(def remove-nth-items    nth/remove-nth-items)
(def duplicate-nth-item  nth/duplicate-nth-item)
(def duplicate-nth-items nth/duplicate-nth-items)
(def replace-nth-item    nth/replace-nth-item)

; vector.range
(def ranged-items                 range/ranged-items)
(def last-items                   range/last-items)
(def first-items                  range/first-items)
(def trim                         range/trim)
(def count!                       range/count!)
(def items-before-first-occurence range/items-before-first-occurence)
(def items-after-first-occurence  range/items-after-first-occurence)

; vector.remove
(def remove-first-item      remove/remove-first-item)
(def remove-last-item       remove/remove-last-item)
(def remove-item            remove/remove-item)
(def remove-item-once       remove/remove-item-once)
(def remove-items           remove/remove-items)
(def remove-items-by        remove/remove-items-by)
(def remove-items-kv        remove/remove-items-kv)
(def remove-duplicates      remove/remove-duplicates)
(def remove-first-occurence remove/remove-first-occurence)

; vector.sort
(def reverse-items          sort/reverse-items)
(def abc-items              sort/abc-items)
(def sort-items             sort/sort-items)
(def items-sorted?          sort/items-sorted?)
(def sort-items-by          sort/sort-items-by)
(def compared-items-sorted? sort/compared-items-sorted?)

; vector.type
(def nonempty? type/nonempty?)

; vector.walk
(def ->items  walk/->items)
(def ->>items walk/->>items)
