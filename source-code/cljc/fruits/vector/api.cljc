
(ns fruits.vector.api
    (:refer-clojure :exclude [empty?])
    (:require [fruits.vector.add       :as add]
              [fruits.vector.check     :as check]
              [fruits.vector.compare   :as compare]
              [fruits.vector.contain   :as contain]
              [fruits.vector.convert   :as convert]
              [fruits.vector.count     :as count]
              [fruits.vector.cut       :as cut]
              [fruits.vector.dex       :as dex]
              [fruits.vector.duplicate :as duplicate]
              [fruits.vector.get       :as get]
              [fruits.vector.insert    :as insert]
              [fruits.vector.item      :as item]
              [fruits.vector.match     :as match]
              [fruits.vector.move      :as move]
              [fruits.vector.remove    :as remove]
              [fruits.vector.replace   :as replace]
              [fruits.vector.result    :as result]
              [fruits.vector.set       :as set]
              [fruits.vector.sort      :as sort]
              [fruits.vector.step      :as step]
              [fruits.vector.toggle    :as toggle]
              [fruits.vector.update    :as update]
              [fruits.vector.upsert    :as upsert]
              [fruits.vector.walk      :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.vector.add/*)
(def conj-item         add/conj-item)
(def conj-item-once    add/conj-item-once)
(def conj-some         add/conj-some)
(def cons-item         add/cons-item)
(def cons-item-once    add/cons-item-once)
(def concat-items      add/concat-items)
(def concat-items-once add/concat-items-once)

; @redirect (fruits.vector.check/*)
(def empty?     check/empty?)
(def not-empty? check/not-empty?)

; @redirect (fruits.vector.compare/*)
(def similars           compare/similars)
(def contains-similars? compare/contains-similars?)
(def difference         compare/difference)

; @redirect (fruits.vector.contain/*)
(def contains-item?     contain/contains-item?)
(def not-contains-item? contain/not-contains-item?)

; @redirect (fruits.vector.convert/*)
(def from-subvec    convert/from-subvec)
(def to-map         convert/to-map)
(def to-nil         convert/to-nil)
(def to-associative convert/to-associative)
(def to-seqable     convert/to-seqable)

; @redirect (fruits.vector.count/*)
(def count?      count/count?)
(def count-min?  count/count-min?)
(def count-max?  count/count-max?)
(def count-same? count/count-same?)
(def count-inc?  count/count-inc?)
(def count-dec?  count/count-dec?)

; @redirect (fruits.vector.cut/*)
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

; @redirect (fruits.vector.dex/*)
(def first-dex-of  dex/first-dex-of)
(def last-dex-of   dex/last-dex-of)
(def nth-dex-of    dex/nth-dex-of)
(def first-dex-by  dex/first-dex-by)
(def second-dex-by dex/second-dex-by)
(def last-dex-by   dex/last-dex-by)
(def nth-dex-by    dex/nth-dex-by)

; @redirect (fruits.vector.duplicate/*)
(def duplicate-first-item  duplicate/duplicate-first-item)
(def duplicate-second-item duplicate/duplicate-second-item)
(def duplicate-last-item   duplicate/duplicate-last-item)
(def duplicate-nth-item    duplicate/duplicate-nth-item)
(def duplicate-nth-items   duplicate/duplicate-nth-items)

; @redirect (fruits.vector.get/*)
(def first-item  get/first-item)
(def second-item get/second-item)
(def last-item   get/last-item)
(def nth-item    get/nth-item)

; @redirect (fruits.vector.insert/*)
(def insert-item insert/insert-item)

; @redirect (fruits.vector.item/*)
(def item-only?   item/item-only?)
(def item-first?  item/item-first?)
(def item-second? item/item-second?)
(def item-last?   item/item-last?)
(def item-nth?    item/item-nth?)

; @redirect (fruits.vector.match/*)
(def any-item-matches?    match/any-item-matches?)
(def no-item-matches?     match/no-item-matches?)
(def all-items-match?     match/all-items-match?)
(def not-all-items-match? match/not-all-items-match?)
(def first-match          match/first-match)
(def second-match         match/second-match)
(def last-match           match/last-match)
(def nth-match            match/nth-match)
(def all-matches          match/all-matches)
(def match-count          match/match-count)

; @redirect (fruits.vector.move/*)
(def move-first-item     move/move-first-item)
(def move-second-item    move/move-second-item)
(def move-last-item      move/move-last-item)
(def move-nth-item       move/move-nth-item)
(def move-nth-item-bwd   move/move-nth-item-bwd)
(def move-nth-item-fwd   move/move-nth-item-fwd)
(def move-item-to-first  move/move-item-to-first)
(def move-item-to-second move/move-item-to-second)
(def move-item-to-last   move/move-item-to-last)
(def move-item-to-nth    move/move-item-to-nth)

; @redirect (fruits.vector.remove/*)
(def remove-item            remove/remove-item)
(def remove-item-once       remove/remove-item-once)
(def remove-first-item      remove/remove-first-item)
(def remove-first-items     remove/remove-first-items)
(def remove-last-item       remove/remove-last-item)
(def remove-last-items      remove/remove-last-items)
(def remove-nth-item        remove/remove-nth-item)
(def remove-nth-items       remove/remove-nth-items)
(def remove-items           remove/remove-items)
(def remove-items-by        remove/remove-items-by)
(def remove-duplicates      remove/remove-duplicates)
(def remove-first-occurence remove/remove-first-occurence)
(def keep-items             remove/keep-items)
(def keep-items-by          remove/keep-items-by)

; @redirect (fruits.vector.replace/*)
(def replace-item        replace/replace-item)
(def replace-first-item  replace/replace-first-item)
(def replace-second-item replace/replace-second-item)
(def replace-last-item   replace/replace-last-item)
(def replace-nth-item    replace/replace-nth-item)

; @redirect (fruits.vector.result/*)
(def first-result  result/first-result)
(def second-result result/second-result)
(def last-result   result/last-result)
(def nth-result    result/nth-result)
(def all-results   result/all-results)
(def result-count  result/result-count)

; @redirect (fruits.vector.set/*)
(def flat-items   set/flat-items)
(def sum-items    set/sum-items)
(def sum-items-by set/sum-items-by)
(def gap-items    set/gap-items)
(def prefix-items set/prefix-items)
(def suffix-items set/suffix-items)
(def repeat-item  set/repeat-item)

; @redirect (fruits.vector.sort/*)
(def order-comparator       sort/order-comparator)
(def reverse-items          sort/reverse-items)
(def abc-items              sort/abc-items)
(def sort-items             sort/sort-items)
(def items-sorted?          sort/items-sorted?)
(def sort-items-by          sort/sort-items-by)
(def sort-items-by-dexes    sort/sort-items-by-dexes)
(def sorted-dexes           sort/sorted-dexes)
(def compared-items-sorted? sort/compared-items-sorted?)

; @redirect (fruits.vector.step/*)
(def prev-item step/prev-item)
(def next-item step/next-item)

; @redirect (fruits.vector.toggle/*)
(def toggle-item toggle/toggle-item)

; @redirect (fruits.vector.update/*)
(def update-first-item     update/update-first-item)
(def update-second-item    update/update-second-item)
(def update-last-item      update/update-last-item)
(def update-nth-item       update/update-nth-item)
(def update-all-item       update/update-all-item)
(def update-first-item-by  update/update-first-item-by)
(def update-second-item-by update/update-second-item-by)
(def update-last-item-by   update/update-last-item-by)
(def update-nth-item-by    update/update-nth-item-by)
(def update-items-by       update/update-items-by)

; @redirect (fruits.vector.upsert/*)
(def upsert-first-item  upsert/upsert-first-item)
(def upsert-second-item upsert/upsert-second-item)
(def upsert-last-item   upsert/upsert-last-item)
(def upsert-nth-item    upsert/upsert-nth-item)

; @redirect (fruits.vector.walk/*)
(def ->items     walk/->items)
(def ->items-by  walk/->items-by)
(def ->>items    walk/->>items)
(def ->>items-by walk/->>items-by)
