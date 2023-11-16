
(ns mixed.api
    (:refer-clojure :exclude [empty?])
    (:require [mixed.check   :as check]
              [mixed.convert :as convert]
              [mixed.core    :as core]
              [mixed.parse   :as parse]
              [mixed.type    :as type]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; mixed.check
(def empty?    check/empty?)
(def nonempty? check/nonempty?)

; mixed.convert
(def to-string   convert/to-string)
(def to-data-url convert/to-data-url)
(def to-vector   convert/to-vector)
(def to-map      convert/to-map)
(def to-number   convert/to-number)

; mixed.core
(def add-numbers         core/add-numbers)
(def subtract-numbers    core/subtract-numbers)
(def multiply-numbers    core/multiply-numbers)
(def update-whole-number core/update-whole-number)

; mixed.parse
(def parse-rational-number parse/parse-rational-number)
(def parse-number          parse/parse-number)
(def parse-whole-number    parse/parse-whole-number)

; mixed.type
(def rational-number?       type/rational-number?)
(def whole-number?          type/whole-number?)
(def natural-whole-number?  type/natural-whole-number?)
(def positive-whole-number? type/positive-whole-number?)
(def negative-whole-number? type/negative-whole-number?)
