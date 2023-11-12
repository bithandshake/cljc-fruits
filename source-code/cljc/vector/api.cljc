
(ns vector.api
    (:refer-clojure :exclude [empty?])
    (:require [vector.check   :as check]
              [vector.compare :as compare]
              [vector.convert :as convert]
              [vector.core    :as core]
              [vector.count   :as count]
              [vector.dex     :as dex]
              [vector.filter  :as filter]
              [vector.item    :as item]
              [vector.match   :as match]
              [vector.move    :as move]
              [vector.nth     :as nth]
              [vector.range   :as range]
              [vector.remove  :as remove]
              [vector.sort    :as sort]
              [vector.step    :as step]
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

; vector.convert
(def to-map convert/to-map)

; vector.core
(def sum-items-by      core/sum-items-by)
(def gap-items         core/gap-items)
(def repeat-item       core/repeat-item)
(def cons-item         core/cons-item)
(def cons-item-once    core/cons-item-once)
(def conj-item         core/conj-item)
(def conj-item-once    core/conj-item-once)
(def conj-some         core/conj-some)
(def concat-items      core/concat-items)
(def concat-items-once core/concat-items-once)
(def replace-item      core/replace-item)
(def insert-item       core/insert-item)
(def toggle-item       core/toggle-item)

; vector.count
(def min?    count/min?)
(def max?    count/max?)
(def longer? count/longer?)
(def count?  count/count?)

; vector.dex
(def item-last-dex  dex/item-last-dex)
(def item-first-dex dex/item-first-dex)

; vector.filter
(def filter-items      filter/filter-items)
(def filter-items-by   filter/filter-items-by)
(def first-filtered    filter/first-filtered)
(def first-filtered-by filter/first-filtered-by)
(def last-filtered     filter/last-filtered)
(def last-filtered-by  filter/last-filtered-by)
(def nth-filtered      filter/nth-filtered)
(def nth-filtered-by   filter/nth-filtered-by)
(def filtered-count    filter/filtered-count)
(def filtered-count?   filter/filtered-count?)

; vector.item
(def last-item          item/last-item)
(def first-item         item/first-item)
(def contains-item?     item/contains-item?)
(def not-contains-item? item/not-contains-item?)
(def only-item?         item/only-item?)
(def item-last?         item/item-last?)
(def item-first?        item/item-first?)

; vector.match
(def any-item-match?     match/any-item-match?)
(def all-items-match?    match/all-items-match?)
(def get-first-match     match/get-first-match)
(def get-last-match      match/get-last-match)
(def get-first-match-dex match/get-first-match-dex)
(def get-last-match-dex  match/get-last-match-dex)

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

; vector.range
(def ranged-items                 range/ranged-items)
(def last-items                   range/last-items)
(def first-items                  range/first-items)
(def trim                         range/trim)
(def items-before-first-occurence range/items-before-first-occurence)
(def items-after-first-occurence  range/items-after-first-occurence)

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

; vector.walk
(def ->items         walk/->items)
(def ->items-indexed walk/->items-indexed)
(def ->>items        walk/->>items)
