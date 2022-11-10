
(ns math.api
    (:require [math.collection :as collection]
              [math.config     :as config]
              [math.core       :as core]
              [math.domain     :as domain]
              [math.percent    :as percent]
              [math.type       :as type]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; math.collection
(def collection-minimum collection/collection-minimum)
(def collection-maximum collection/collection-maximum)
(def minimum            collection/minimum)
(def maximum            collection/maximum)

; math.config
(def PI config/PI)

; math.core
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

; math.type
(def negative?    type/negative?)
(def positive?    type/positive?)
(def nonnegative? type/nonnegative?)
