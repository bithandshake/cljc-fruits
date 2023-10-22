
(ns keyword.api
    (:require [keyword.check     :as check]
              [keyword.convert   :as convert]
              [keyword.core      :as core]
              [keyword.name      :as name]
              [keyword.namespace :as namespace]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; keyword.check
(def namespaced? check/namespaced?)

; keyword.convert
(def to-string convert/to-string)

; keyword.core
(def join   core/join)
(def append core/append)

; keyword.name
(def get-name name/get-name)

; keyword.namespace
(def add-namespace    namespace/add-namespace)
(def get-namespace    namespace/get-namespace)
(def remove-namespace namespace/remove-namespace)
