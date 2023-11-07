
(ns math.api
    (:require [math.check      :as check]
              [math.collection :as collection]
              [math.config     :as config]
              [math.core       :as core]
              [math.domain     :as domain]
              [math.percent    :as percent]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; math.check
(def negative?    check/negative?)
(def positive?    check/positive?)
(def nonnegative? check/nonnegative?)

; math.collection
(def collection-average collection/collection-average)
(def collection-minimum collection/collection-minimum)
(def collection-maximum collection/collection-maximum)
(def minimum            collection/minimum)
(def maximum            collection/maximum)

; math.config
(def PI config/PI)

; math.core
(def add                 core/add)
(def subtract            core/subtract)
(def multiply            core/multiply)
(def avarage             core/avarage)
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

; math.domain
(def domain-inchoate domain/domain-inchoate)
(def domain-floor    domain/domain-floor)
(def domain-ceil     domain/domain-ceil)
(def choose          domain/choose)
(def calc            domain/calc)

; math.percent
(def percent->angle percent/percent->angle)
(def percent        percent/percent)
(def percent-result percent/percent-result)
(def percent-rest   percent/percent-rest)
(def percent-add    percent/percent-add)
(def percent-diff   percent/percent-diff)
