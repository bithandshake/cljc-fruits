
(ns fruits.seqable.api
    (:refer-clojure :exclude [empty?])
    (:require [fruits.seqable.check  :as check]
              [fruits.seqable.count  :as count]
              [fruits.seqable.cursor :as cursor]
              [fruits.seqable.dex    :as dex]
              [fruits.seqable.path   :as path]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.seqable.check/*)
(def not-seqable? check/not-seqable?)
(def empty?       check/empty?)
(def not-empty?   check/not-empty?)
(def cursor?      check/cursor?)
(def dex?         check/dex?)

; @redirect (fruits.seqable.count/*)
(def count-difference count/count-difference)

; @redirect (fruits.seqable.cursor/*)
(def normalize-cursor      cursor/normalize-cursor)
(def cursor-in-bounds?     cursor/cursor-in-bounds?)
(def cursor-out-of-bounds? cursor/cursor-out-of-bounds?)
(def next-cursor           cursor/next-cursor)
(def prev-cursor           cursor/prev-cursor)
(def cursor-range          cursor/cursor-range)
(def cursor-first?         cursor/cursor-first?)
(def cursor-last?          cursor/cursor-last?)
(def first-cursor          cursor/first-cursor)
(def last-cursor           cursor/last-cursor)
(def inc-cursor            cursor/inc-cursor)
(def dec-cursor            cursor/dec-cursor)

; @redirect (fruits.seqable.dex/*)
(def normalize-dex      dex/normalize-dex)
(def dex-in-bounds?     dex/dex-in-bounds?)
(def dex-out-of-bounds? dex/dex-out-of-bounds?)
(def next-dex           dex/next-dex)
(def prev-dex           dex/prev-dex)
(def dex-range          dex/dex-range)
(def dex-first?         dex/dex-first?)
(def dex-last?          dex/dex-last?)
(def first-dex          dex/first-dex)
(def last-dex           dex/last-dex)
(def new-dex            dex/new-dex)
(def inc-dex            dex/inc-dex)
(def dec-dex            dex/dec-dex)

; @redirect (fruits.seqable.path/*)
(def dynamic-path path/dynamic-path)
