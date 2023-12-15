
(ns fruits.vector.api
    (:refer-clojure :exclude [empty?])
    (:require [fruits.vector.check   :as check]
              [fruits.vector.compare :as compare]
              [fruits.vector.contain :as contain]
              [fruits.vector.convert :as convert]
              [fruits.vector.core    :as core]
              [fruits.vector.count   :as count]
              [fruits.vector.cut     :as cut]
              [fruits.vector.dex     :as dex]
              [fruits.vector.insert  :as insert]
              [fruits.vector.item    :as item]
              [fruits.vector.match   :as match]
              [fruits.vector.move    :as move]
              [fruits.vector.nth     :as nth]
              [fruits.vector.remove  :as remove]
              [fruits.vector.replace :as replace]
              [fruits.vector.set     :as set]
              [fruits.vector.sort    :as sort]
              [fruits.vector.step    :as step]
              [fruits.vector.update  :as update]
              [fruits.vector.walk    :as walk]
              [fruits.vector.result :as result]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.vector.check)
(def empty?    check/empty?)
(def nonempty? check/nonempty?)

; @redirect (fruits.vector.compare)
(def similars           compare/similars)
(def contains-similars? compare/contains-similars?)
(def difference         compare/difference)

; @redirect (fruits.vector.contain)
(def contains-item?     contain/contains-item?)
(def not-contains-item? contain/not-contains-item?)

; @redirect (fruits.vector.convert)
(def from-subvec convert/from-subvec)
(def to-map      convert/to-map)
(def to-nil      convert/to-nil)

; @redirect (fruits.vector.core)
(def cons-item         core/cons-item)
(def cons-item-once    core/cons-item-once)
(def conj-item         core/conj-item)
(def conj-item-once    core/conj-item-once)
(def conj-some         core/conj-some)
(def concat-items      core/concat-items)
(def concat-items-once core/concat-items-once)
(def toggle-item       core/toggle-item)

; @redirect (fruits.vector.count)
(def count-min? count/count-min?)
(def count-max? count/count-max?)
(def longer?    count/longer?)
(def count?     count/count?)

; @redirect (fruits.vector.cut)
(def keep-range             cut/keep-range)
(def cut-range              cut/cut-range)
(def keep-last-items        cut/keep-last-items)
(def keep-last-item         cut/keep-last-item)
(def keep-first-items       cut/keep-first-items)
(def keep-first-item        cut/keep-first-item)
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

; @redirect (fruits.vector.dex)
(def first-dex-of dex/first-dex-of)
(def last-dex-of  dex/last-dex-of)
(def nth-dex-of   dex/nth-dex-of)
(def first-dex-by dex/first-dex-by)
(def last-dex-by  dex/last-dex-by)
(def nth-dex-by   dex/nth-dex-by)

; @redirect (fruits.vector.insert)
(def insert-item insert/insert-item)

; @redirect (fruits.vector.item)
(def last-item   item/last-item)
(def first-item  item/first-item)
(def only-item?  item/only-item?)
(def item-last?  item/item-last?)
(def item-first? item/item-first?)

; @redirect (fruits.vector.match)
(def any-item-matches? match/any-item-matches?)
(def no-item-matches?  match/no-item-matches?)
(def all-items-match?  match/all-items-match?)
(def first-match       match/first-match)
(def last-match        match/last-match)
(def nth-match         match/nth-match)
(def match-count       match/match-count)

; @redirect (fruits.vector.move)
(def move-nth-item        move/move-nth-item)
(def move-nth-item-bwd    move/move-nth-item-bwd)
(def move-nth-item-fwd    move/move-nth-item-fwd)
(def move-item-to-last    move/move-item-to-last)
(def move-item-to-first   move/move-item-to-first)
(def move-first-occurence move/move-first-occurence)

; @redirect (fruits.vector.nth)
(def nth-item            nth/nth-item)
(def remove-nth-item     nth/remove-nth-item)
(def remove-nth-items    nth/remove-nth-items)
(def duplicate-nth-item  nth/duplicate-nth-item)
(def duplicate-nth-items nth/duplicate-nth-items)
(def replace-nth-item    nth/replace-nth-item)

; @redirect (fruits.vector.remove)
(def remove-first-item      remove/remove-first-item)
(def remove-first-items     remove/remove-first-items)
(def remove-last-item       remove/remove-last-item)
(def remove-last-items      remove/remove-last-items)
(def remove-item            remove/remove-item)
(def remove-item-once       remove/remove-item-once)
(def remove-items           remove/remove-items)
(def remove-items-by        remove/remove-items-by)
(def remove-duplicates      remove/remove-duplicates)
(def remove-first-occurence remove/remove-first-occurence)
(def keep-items             remove/keep-items)
(def keep-items-by          remove/keep-items-by)

; @redirect (fruits.vector.replace)
(def replace-item       replace/replace-item)
(def replace-first-item replace/replace-first-item)
(def replace-last-item  replace/replace-last-item)

; @redirect (fruits.vector.result)
(def first-result result/first-result)
(def last-result  result/last-result)
(def nth-result   result/nth-result)

; @redirect (fruits.vector.set)
(def flat-items   set/flat-items)
(def sum-items    set/sum-items)
(def sum-items-by set/sum-items-by)
(def gap-items    set/gap-items)
(def prefix-items set/prefix-items)
(def suffix-items set/suffix-items)
(def repeat-item  set/repeat-item)

; @redirect (fruits.vector.sort)
(def reverse-items          sort/reverse-items)
(def abc-items              sort/abc-items)
(def sort-items             sort/sort-items)
(def items-sorted?          sort/items-sorted?)
(def sort-items-by          sort/sort-items-by)
(def sort-items-by-dexes    sort/sort-items-by-dexes)
(def sorted-dexes           sort/sorted-dexes)
(def compared-items-sorted? sort/compared-items-sorted?)

; @redirect (fruits.vector.step)
(def prev-item step/prev-item)
(def next-item step/next-item)

; @redirect (fruits.vector.update)
(def update-first-item    update/update-first-item)
(def update-last-item     update/update-last-item)
(def update-nth-item      update/update-nth-item)
(def update-all-item      update/update-all-item)
(def update-first-item-by update/update-first-item-by)
(def update-last-item-by  update/update-last-item-by)
(def update-nth-item-by   update/update-nth-item-by)
(def update-items-by      update/update-items-by)

; @redirect (fruits.vector.walk)
(def ->items     walk/->items)
(def ->items-by  walk/->items-by)
(def ->>items    walk/->>items)
(def ->>items-by walk/->>items-by)
