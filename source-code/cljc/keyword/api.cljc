
(ns keyword.api
    (:require [keyword.check     :as check]
              [keyword.convert   :as convert]
              [keyword.core      :as core]
              [keyword.name      :as name]
              [keyword.namespace :as namespace]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (keyword.check)
(def namespaced?    check/namespaced?)
(def nonnamespaced? check/nonnamespaced?)

; @redirect (keyword.convert)
(def to-string convert/to-string)

; @redirect (keyword.core)
(def join   core/join)
(def append core/append)

; @redirect (keyword.name)
(def get-name name/get-name)

; @redirect (keyword.namespace)
(def add-namespace    namespace/add-namespace)
(def get-namespace    namespace/get-namespace)
(def remove-namespace namespace/remove-namespace)
