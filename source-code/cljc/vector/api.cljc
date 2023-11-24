
(ns vector.api
    (:refer-clojure :exclude [empty?])
    (:require [vector.check   :as check]
              [vector.compare :as compare]
              [vector.contain :as contain]
              [vector.convert :as convert]
              [vector.core    :as core]
              [vector.count   :as count]
              [vector.cut     :as cut]
              [vector.dex     :as dex]
              [vector.filter  :as filter]
              [vector.insert  :as insert]
              [vector.item    :as item]
              [vector.match   :as match]
              [vector.move    :as move]
              [vector.nth     :as nth]
              [vector.remove  :as remove]
              [vector.replace :as replace]
              [vector.set     :as set]
              [vector.sort    :as sort]
              [vector.step    :as step]
              [vector.update  :as update]
              [vector.walk    :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; vector.check
(def empty?    check/empty?)
(def nonempty? check/nonempty?)

; vector.compare
(def similars           compare/similars)
(def contains-similars? compare/contains-similars?)
(def difference         compare/difference)

; vector.contain
(def contains-item?     contain/contains-item?)
(def not-contains-item? contain/not-contains-item?)

; vector.convert
(def to-map convert/to-map)

; vector.core
(def cons-item         core/cons-item)
(def cons-item-once    core/cons-item-once)
(def conj-item         core/conj-item)
(def conj-item-once    core/conj-item-once)
(def conj-some         core/conj-some)
(def concat-items      core/concat-items)
(def concat-items-once core/concat-items-once)
(def toggle-item       core/toggle-item)

; vector.count
(def count-min? count/count-min?)
(def count-max? count/count-max?)
(def longer?    count/longer?)
(def count?     count/count?)

; vector.cut
(def keep-range             cut/keep-range)
(def cut-range              cut/cut-range)
(def last-items             cut/last-items)
(def first-items            cut/first-items)
(def before-first-occurence cut/before-first-occurence)
(def before-first-match     cut/before-first-match)
(def before-last-occurence  cut/before-last-occurence)
(def before-last-match      cut/before-last-match)
(def after-first-occurence  cut/after-first-occurence)
(def after-first-match      cut/after-first-match)
(def after-last-occurence   cut/after-last-occurence)
(def after-last-match       cut/after-last-match)
(def from-first-occurence   cut/from-first-occurence)
(def from-first-match       cut/from-first-match)
(def from-last-occurence    cut/from-last-occurence)
(def from-last-match        cut/from-last-match)
(def to-first-occurence     cut/to-first-occurence)
(def to-first-match         cut/to-first-match)
(def to-last-occurence      cut/to-last-occurence)
(def to-last-match          cut/to-last-match)

; vector.dex
(def first-dex-of dex/first-dex-of)
(def last-dex-of  dex/last-dex-of)
(def nth-dex-of   dex/nth-dex-of)
(def first-dex-by dex/first-dex-by)
(def last-dex-by  dex/last-dex-by)
(def nth-dex-by   dex/nth-dex-by)

; vector.filter
(def filter-items filter/filter-items)

; vector.insert
(def insert-item insert/insert-item)

; vector.item
(def last-item   item/last-item)
(def first-item  item/first-item)
(def only-item?  item/only-item?)
(def item-last?  item/item-last?)
(def item-first? item/item-first?)

; vector.match
(def any-item-matches? match/any-item-matches?)
(def all-items-match?  match/all-items-match?)
(def first-match       match/first-match)
(def last-match        match/last-match)
(def nth-match         match/nth-match)
(def match-count       match/match-count)

; vector.move
(def move-nth-item        move/move-nth-item)
(def move-nth-item-bwd    move/move-nth-item-bwd)
(def move-nth-item-fwd    move/move-nth-item-fwd)
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

; vector.remove
(def remove-first-item      remove/remove-first-item)
(def remove-first-items     remove/remove-first-items)
(def remove-last-item       remove/remove-last-item)
(def remove-last-items      remove/remove-last-items)
(def remove-item            remove/remove-item)
(def remove-item-once       remove/remove-item-once)
(def remove-items           remove/remove-items)
(def remove-items-by        remove/remove-items-by)
(def remove-items-kv        remove/remove-items-kv)
(def remove-duplicates      remove/remove-duplicates)
(def remove-first-occurence remove/remove-first-occurence)
(def keep-items             remove/keep-items)
(def keep-items-by          remove/keep-items-by)

; vector.replace
(def replace-item       replace/replace-item)
(def replace-first-item replace/replace-first-item)
(def replace-last-item  replace/replace-last-item)

; vector.set
(def sum-items-by set/sum-items-by)
(def gap-items    set/gap-items)
(def repeat-item  set/repeat-item)

; vector.sort
(def reverse-items          sort/reverse-items)
(def abc-items              sort/abc-items)
(def sort-items             sort/sort-items)
(def items-sorted?          sort/items-sorted?)
(def sort-items-by          sort/sort-items-by)
(def sort-items-by-dexes    sort/sort-items-by-dexes)
(def sorted-dexes           sort/sorted-dexes)
(def compared-items-sorted? sort/compared-items-sorted?)

; vector.step
(def prev-item step/prev-item)
(def next-item step/next-item)

; vector.update
(def update-first-item    update/update-first-item)
(def update-last-item     update/update-last-item)
(def update-nth-item      update/update-nth-item)
(def update-first-item-by update/update-first-item-by)
(def update-last-item-by  update/update-last-item-by)
(def update-nth-item-by   update/update-nth-item-by)

; vector.walk
(def ->items         walk/->items)
(def ->items-indexed walk/->items-indexed)
(def ->>items        walk/->>items)
