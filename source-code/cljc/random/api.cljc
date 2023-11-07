
(ns random.api
    (:require [random.generate :as generate]
              [random.pick     :as pick]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; random.generate
(def generate-boolean            generate/generate-boolean)
(def generate-uuid               generate/generate-uuid)
(def generate-string             generate/generate-string)
(def generate-keyword            generate/generate-keyword)
(def generate-namespaced-keyword generate/generate-namespaced-keyword)
(def generate-number             generate/generate-number)

; random.pick
(def pick-vector-item pick/pick-vector-item)
