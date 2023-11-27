
(ns seqable.api
    (:refer-clojure :exclude [empty?])
    (:require [seqable.check  :as check]
              [seqable.count  :as count]
              [seqable.cursor :as cursor]
              [seqable.dex    :as dex]
              [seqable.path   :as path]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; seqable.check
(def nonseqable? check/nonseqable?)
(def empty?      check/empty?)
(def nonempty?   check/nonempty?)
(def cursor?     check/cursor?)
(def dex?        check/dex?)

; seqable.count
(def count-difference count/count-difference)

; seqable.cursor
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

; seqable.dex
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

; seqable.path
(def dynamic-path path/dynamic-path)
