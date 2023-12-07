
(ns fruits.math.api
    (:require [fruits.math.check      :as check]
              [fruits.math.collection :as collection]
              [fruits.math.config     :as config]
              [fruits.math.core       :as core]
              [fruits.math.domain     :as domain]
              [fruits.math.percent    :as percent]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.math.check)
(def negative?    check/negative?)
(def positive?    check/positive?)
(def nonnegative? check/nonnegative?)

; @redirect (fruits.math.collection)
(def collection-average collection/collection-average)
(def collection-minimum collection/collection-minimum)
(def collection-maximum collection/collection-maximum)
(def minimum            collection/minimum)
(def maximum            collection/maximum)

; @redirect (fruits.math.config)
(def PI config/PI)

; @redirect (fruits.math.core)
(def add                 core/add)
(def subtract            core/subtract)
(def multiply            core/multiply)
(def average             core/average)
(def circum              core/circum)
(def power               core/power)
(def floor               core/floor)
(def ceil                core/ceil)
(def round               core/round)
(def absolute            core/absolute)
(def negative            core/negative)
(def positive            core/positive)
(def absolute-difference core/absolute-difference)
(def opposite            core/opposite)
(def between?            core/between?)
(def between!            core/between!)

; @redirect (fruits.math.domain)
(def domain-inchoate domain/domain-inchoate)
(def domain-floor    domain/domain-floor)
(def domain-ceil     domain/domain-ceil)
(def choose          domain/choose)
(def calc            domain/calc)

; @redirect (fruits.math.percent)
(def percent->angle percent/percent->angle)
(def percent        percent/percent)
(def percent-result percent/percent-result)
(def percent-rest   percent/percent-rest)
(def percent-add    percent/percent-add)
(def percent-diff   percent/percent-diff)
