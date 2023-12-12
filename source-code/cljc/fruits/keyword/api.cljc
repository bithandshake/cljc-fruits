
(ns fruits.keyword.api
    (:require [fruits.keyword.check     :as check]
              [fruits.keyword.convert   :as convert]
              [fruits.keyword.insert :as insert]
              [fruits.keyword.name      :as name]
              [fruits.keyword.namespace :as namespace]
              [fruits.keyword.set :as set]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.keyword.check)
(def namespaced?    check/namespaced?)
(def nonnamespaced? check/nonnamespaced?)

; @redirect (fruits.keyword.convert)
(def to-string convert/to-string)

; @redirect (fruits.keyword.insert)
(def prepend insert/prepend)
(def append  insert/append)

; @redirect (fruits.keyword.name)
(def get-name name/get-name)

; @redirect (fruits.keyword.namespace)
(def add-namespace    namespace/add-namespace)
(def get-namespace    namespace/get-namespace)
(def remove-namespace namespace/remove-namespace)

; @redirect (fruits.keyword.set)
(def join set/join)
