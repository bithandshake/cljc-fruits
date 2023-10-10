
(ns map.api
    (:require [map.check     :as check]
              [map.collapse  :as collapse]
              [map.convert   :as convert]
              [map.core      :as core]
              [map.filter    :as filter]
              [map.key       :as key]
              [map.inherit   :as inherit]
              [map.match     :as match]
              [map.merge     :as merge]
              [map.namespace :as namespace]
              [map.remove    :as remove]
              [map.value     :as value]
              [map.walk      :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; map.check
(def nonempty? check/nonempty?)

; map.collapse
(def collapse collapse/collapse)

; map.convert
(def to-vector convert/to-vector)

; map.core
(def difference     core/difference)
(def swap           core/swap)
(def dissoc-in      core/dissoc-in)
(def dissoc-items   core/dissoc-items)
(def inject-in      core/inject-in)
(def toggle         core/toggle)
(def toggle-in      core/toggle-in)
(def update-some    core/update-some)
(def update-in-some core/update-in-some)
(def assoc-some     core/assoc-some)
(def assoc-in-some  core/assoc-in-some)
(def assoc-or       core/assoc-or)
(def assoc-in-or    core/assoc-in-or)

; map.filter
(def filter-values    filter/filter-values)
(def filter-values-by filter/filter-values-by)

; map.key
(def get-keys          key/get-keys)
(def get-first-key     key/get-first-key)
(def contains-key?     key/contains-key?)
(def contains-of-keys? key/contains-of-keys?)
(def rekey-item        key/rekey-item)
(def rekey-items       key/rekey-items)
(def get-keys-by       key/get-keys-by)

; map.inherit
(def inherit    inherit/inherit)
(def inherit-in inherit/inherit-in)

; map.match
(def any-key-match?        match/any-key-match?)
(def any-value-match?      match/any-value-match?)
(def all-values-match?     match/all-values-match?)
(def get-first-match-key   match/get-first-match-key)
(def get-first-match-value match/get-first-match-value)
(def match-pattern?        match/match-pattern?)

; map.merge
(def deep-merge     merge/deep-merge)
(def reversed-merge merge/reversed-merge)
(def merge-some     merge/merge-some)

; map.namespace
(def get-namespace    namespace/get-namespace)
(def namespaced?      namespace/namespaced?)
(def add-namespace    namespace/add-namespace)
(def remove-namespace namespace/remove-namespace)
(def assoc-ns         namespace/assoc-ns)
(def get-ns           namespace/get-ns)

; map.remove
(def remove-keys      remove/remove-keys)
(def remove-keys-by   remove/remove-keys-by)
(def remove-values    remove/remove-values)
(def remove-values-by remove/remove-values-by)

; map.value
(def get-values      value/get-values)
(def get-first-value value/get-first-value)
(def contains-value? value/contains-value?)
(def values-equal?   value/values-equal?)

; map.walk
(def ->keys              walk/->keys)
(def ->>keys             walk/->>keys)
(def ->values            walk/->values)
(def ->>values           walk/->>values)
(def ->kv                walk/->kv)
(def ->>kv               walk/->>kv)
(def ->remove-keys-by    walk/->remove-keys-by)
(def ->>remove-keys-by   walk/->>remove-keys-by)
(def ->remove-values-by  walk/->remove-values-by)
(def ->>remove-values-by walk/->>remove-values-by)
