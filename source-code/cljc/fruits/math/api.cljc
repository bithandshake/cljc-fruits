
(ns fruits.math.api
    (:require [fruits.math.between    :as between]
              [fruits.math.check      :as check]
              [fruits.math.circle     :as circle]
              [fruits.math.collection :as collection]
              [fruits.math.config     :as config]
              [fruits.math.domain     :as domain]
              [fruits.math.percent    :as percent]
              [fruits.math.polarity   :as polarity]
              [fruits.math.power      :as power]
              [fruits.math.round      :as round]
              [fruits.math.set        :as set]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.math.between/*)
(def between? between/between?)
(def between  between/between)

; @redirect (fruits.math.check/*)
(def negative?     check/negative?)
(def positive?     check/positive?)
(def not-negative? check/not-negative?)
(def not-positive? check/not-positive?)

; @redirect (fruits.math.circle/*)
(def diameter->area   circle/diameter->area)
(def diameter->circum circle/diameter->circum)
(def diameter->radius circle/diameter->radius)
(def radius->area     circle/radius->area)
(def radius->circum   circle/radius->circum)
(def radius->diameter circle/radius->diameter)
(def circum->area     circle/circum->area)
(def circum->diameter circle/circum->diameter)
(def circum->radius   circle/circum->radius)

; @redirect (fruits.math.collection/*)
(def collection-average collection/collection-average)
(def collection-minimum collection/collection-minimum)
(def collection-maximum collection/collection-maximum)
(def average            collection/average)
(def minimum            collection/minimum)
(def maximum            collection/maximum)

; @redirect (fruits.math.config/*)
(def PI config/PI)

; @redirect (fruits.math.domain/*)
(def domain-inchoate domain/domain-inchoate)
(def domain-floor    domain/domain-floor)
(def domain-ceil     domain/domain-ceil)
(def domain-project  domain/domain-project)

; @redirect (fruits.math.percent/*)
(def percent->angle percent/percent->angle)
(def percent        percent/percent)
(def percent-result percent/percent-result)
(def percent-rest   percent/percent-rest)
(def percent-add    percent/percent-add)
(def percent-diff   percent/percent-diff)

; @redirect (fruits.math.polarity/*)
(def absolute            polarity/absolute)
(def negative            polarity/negative)
(def positive            polarity/positive)
(def absolute-difference polarity/absolute-difference)
(def opposite            polarity/opposite)

; @redirect (fruits.math.round/*)
(def floor round/floor)
(def ceil  round/ceil)
(def round round/round)

; @redirect (fruits.math.power/*)
(def power power/power)

; @redirect (fruits.math.set/*)
(def add      set/add)
(def subtract set/subtract)
(def multiply set/multiply)
