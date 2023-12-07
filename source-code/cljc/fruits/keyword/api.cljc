
(ns fruits.keyword.api
    (:require [fruits.keyword.check     :as check]
              [fruits.keyword.convert   :as convert]
              [fruits.keyword.core      :as core]
              [fruits.keyword.name      :as name]
              [fruits.keyword.namespace :as namespace]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.keyword.check)
(def namespaced?    check/namespaced?)
(def nonnamespaced? check/nonnamespaced?)

; @redirect (fruits.keyword.convert)
(def to-string convert/to-string)

; @redirect (fruits.keyword.core)
(def join   core/join)
(def append core/append)

; @redirect (fruits.keyword.name)
(def get-name name/get-name)

; @redirect (fruits.keyword.namespace)
(def add-namespace    namespace/add-namespace)
(def get-namespace    namespace/get-namespace)
(def remove-namespace namespace/remove-namespace)
