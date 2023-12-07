
(ns fruits.mixed.api
    (:refer-clojure :exclude [empty?])
    (:require [fruits.mixed.check   :as check]
              [fruits.mixed.convert :as convert]
              [fruits.mixed.core    :as core]
              [fruits.mixed.parse   :as parse]
              [fruits.mixed.type    :as type]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.mixed.check)
(def empty?    check/empty?)
(def nonempty? check/nonempty?)

; @redirect (fruits.mixed.convert)
(def to-string   convert/to-string)
(def to-data-url convert/to-data-url)
(def to-vector   convert/to-vector)
(def to-map      convert/to-map)
(def to-number   convert/to-number)

; @redirect (fruits.mixed.core)
(def add-numbers         core/add-numbers)
(def subtract-numbers    core/subtract-numbers)
(def multiply-numbers    core/multiply-numbers)
(def update-whole-number core/update-whole-number)

; @redirect (fruits.mixed.parse)
(def parse-rational-number parse/parse-rational-number)
(def parse-number          parse/parse-number)
(def parse-whole-number    parse/parse-whole-number)

; @redirect (fruits.mixed.type)
(def rational-number?       type/rational-number?)
(def whole-number?          type/whole-number?)
(def natural-whole-number?  type/natural-whole-number?)
(def positive-whole-number? type/positive-whole-number?)
(def negative-whole-number? type/negative-whole-number?)
