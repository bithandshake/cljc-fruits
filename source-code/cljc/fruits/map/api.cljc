
(ns fruits.map.api
    (:refer-clojure :exclude [empty? keys namespace])
    (:require [fruits.map.assoc     :as assoc]
              [fruits.map.check     :as check]
              [fruits.map.collapse  :as collapse]
              [fruits.map.compare   :as compare]
              [fruits.map.convert   :as convert]
              [fruits.map.copy      :as copy]
              [fruits.map.dissoc    :as dissoc]
              [fruits.map.filter    :as filter]
              [fruits.map.get       :as get]
              [fruits.map.key       :as key]
              [fruits.map.match     :as match]
              [fruits.map.merge     :as merge]
              [fruits.map.move      :as move]
              [fruits.map.namespace :as namespace]
              [fruits.map.remove    :as remove]
              [fruits.map.swap      :as swap]
              [fruits.map.toggle    :as toggle]
              [fruits.map.update    :as update]
              [fruits.map.value     :as value]
              [fruits.map.walk      :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.map.assoc/*)
(def assoc-by  assoc/assoc-by)

; @redirect (map.check/*)
(def empty?      check/empty?)
(def not-empty?  check/not-empty?)
(def namespaced? check/namespaced?)

; @redirect (fruits.map.collapse/*)
(def collapse collapse/collapse)

; @redirect (fruits.map.compare/*)
(def difference compare/difference)

; @redirect (fruits.map.convert/*)
(def to-vector      convert/to-vector)
(def to-nil         convert/to-nil)
(def to-associative convert/to-associative)
(def to-seqable     convert/to-seqable)

; @redirect (fruits.map.copy/*)
(def copy    copy/copy)
(def copy-in copy/copy-in)

; @redirect (fruits.map.dissoc/*)
(def dissoc-in dissoc/dissoc-in)
(def dissoc-by dissoc/dissoc-by)

; @redirect (fruits.map.filter/*)
(def filter-values filter/filter-values)

; @redirect (fruits.map.key/*)
(def keys              key/keys)
(def keys-by           key/keys-by)
(def first-key         key/first-key)
(def contains-key?     key/contains-key?)
(def contains-any-key? key/contains-any-key?)
(def contains-all-key? key/contains-all-key?)

; @redirect (fruits.map.get/*)
(def get-by get/get-by)

; @redirect (fruits.map.match/*)
(def any-key-matches?      match/any-key-matches?)
(def any-value-matches?    match/any-value-matches?)
(def all-keys-match?       match/all-keys-match?)
(def all-values-match?     match/all-values-match?)
(def not-all-keys-match?   match/not-all-keys-match?)
(def not-all-values-match? match/not-all-values-match?)
(def first-match-key       match/first-match-key)
(def first-matching-key    match/first-matching-key)
(def first-matching-value  match/first-matching-value)
(def matches-pattern?      match/matches-pattern?)

; @redirect (fruits.map.merge/*)
(def deep-merge     merge/deep-merge)
(def reversed-merge merge/reversed-merge)
(def merge-some     merge/merge-some)

; @redirect (fruits.map.move/*)
(def move    move/move)
(def move-in move/move-in)

; @redirect (fruits.map.namespace/*)
(def namespace        namespace/namespace)
(def add-namespace    namespace/add-namespace)
(def remove-namespace namespace/remove-namespace)
(def assoc-ns         namespace/assoc-ns)
(def get-ns           namespace/get-ns)

; @redirect (fruits.map.remove/*)
(def remove-key       remove/remove-key)
(def remove-keys      remove/remove-keys)
(def remove-keys-by   remove/remove-keys-by)
(def remove-value     remove/remove-value)
(def remove-values    remove/remove-values)
(def remove-values-by remove/remove-values-by)
(def keep-key         remove/keep-key)
(def keep-keys        remove/keep-keys)
(def keep-keys-by     remove/keep-keys-by)
(def keep-value       remove/keep-value)
(def keep-values      remove/keep-values)
(def keep-values-by   remove/keep-values-by)

; @redirect (fruits.map.swap/*)
(def swap swap/swap)

; @redirect (fruits.map.toggle/*)
(def toggle    toggle/toggle)
(def toggle-in toggle/toggle-in)

; @redirect (fruits.map.update/*)
(def update-by        update/update-by)
(def update-all-key   update/update-all-key)
(def update-all-value update/update-all-value)
(def update-keys-by   update/update-keys-by)
(def update-values-by update/update-values-by)

; @redirect (fruits.map.value/*)
(def values          value/values)
(def first-value     value/first-value)
(def contains-value? value/contains-value?)
(def values-equal?   value/values-equal?)

; @redirect (fruits.map.walk/*)
(def ->keys       walk/->keys)
(def ->keys-by    walk/->keys-by)
(def ->>keys      walk/->>keys)
(def ->>keys-by   walk/->>keys-by)
(def ->values     walk/->values)
(def ->values-by  walk/->values-by)
(def ->>values    walk/->>values)
(def ->>values-by walk/->>values-by)
(def ->kv         walk/->kv)
(def ->>kv        walk/->>kv)
