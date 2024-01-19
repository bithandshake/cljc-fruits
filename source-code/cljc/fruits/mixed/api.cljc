
(ns fruits.mixed.api
    (:refer-clojure :exclude [empty? number?])
    (:require [fruits.mixed.check   :as check]
              [fruits.mixed.convert :as convert]
              [fruits.mixed.number  :as number]
              [fruits.mixed.parse   :as parse]
              [fruits.mixed.type    :as type]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.mixed.check/*)
(def empty?     check/empty?)
(def not-empty? check/not-empty?)
(def kv?        check/kv?)
(def kvs?       check/kvs?)

; @redirect (fruits.mixed.convert/*)
(def to-kv          convert/to-kv)
(def to-kvs         convert/to-kvs)
(def to-data-url    convert/to-data-url)
(def to-string      convert/to-string)
(def to-vector      convert/to-vector)
(def to-map         convert/to-map)
(def to-integer     convert/to-integer)
(def to-number      convert/to-number)
(def to-keyword     convert/to-keyword)
(def to-fn          convert/to-fn)
(def to-ifn         convert/to-ifn)
(def to-nil         convert/to-nil)
(def to-seqable     convert/to-seqable)
(def to-associative convert/to-associative)

; @redirect (fruits.mixed.number/*)
(def add-numbers        number/add-numbers)
(def subtract-numbers   number/subtract-numbers)
(def multiply-numbers   number/multiply-numbers)
(def update-number      number/update-number)
(def update-number-part number/update-number-part)

; @redirect (fruits.mixed.parse/*)
(def parse-number parse/parse-number)

; @redirect (fruits.mixed.type/*)
(def number?                type/number?)
(def rational-number?       type/rational-number?)
(def whole-number?          type/whole-number?)
(def natural-whole-number?  type/natural-whole-number?)
(def positive-whole-number? type/positive-whole-number?)
(def negative-whole-number? type/negative-whole-number?)
