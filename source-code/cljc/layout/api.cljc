
(ns layout.api
    (:require [layout.table :as table]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (layout.table)
(def column-count  table/column-count)
(def columns-width table/columns-width)
(def row-count     table/row-count)
