
(ns fruits.random.api
    (:require [fruits.random.generate :as generate]
              [fruits.random.pick     :as pick]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.random.generate)
(def generate-boolean            generate/generate-boolean)
(def generate-uuid               generate/generate-uuid)
(def generate-string             generate/generate-string)
(def generate-keyword            generate/generate-keyword)
(def generate-namespaced-keyword generate/generate-namespaced-keyword)
(def generate-number             generate/generate-number)

; @redirect (fruits.random.pick)
(def pick-vector-item pick/pick-vector-item)
