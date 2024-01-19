
(ns fruits.loop.api
    (:require [fruits.loop.do     :as do]
              [fruits.loop.pairs  :as pairs]
              [fruits.loop.reduce :as reduce]
              [fruits.loop.some   :as some]
              [fruits.loop.walk   :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.loop.do/*)
(def do-indexed do/do-indexed)
(def do-while   do/do-while)

; @redirect (fruits.loop.pairs/*)
(def reduce-pairs pairs/reduce-pairs)
(def apply-pairs  pairs/apply-pairs)

; @redirect (fruits.loop.reduce/*)
(def reduce-kv-indexed reduce/reduce-kv-indexed)
(def reduce-range      reduce/reduce-range)
(def reduce-indexed    reduce/reduce-indexed)

; @redirect (fruits.loop.some/*)
(def some-indexed some/some-indexed)

; @redirect (fruits.loop.walk/*)
(def <-walk walk/<-walk)
