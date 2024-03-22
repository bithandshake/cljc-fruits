
(ns fruits.print.api
    (:require [fruits.print.table :as table]
              [fruits.print.data :as data]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.print.data/*)
(def map-kv data/map-kv)

; @redirect (fruits.print.table/*)
(def table-line table/table-line)
(def table-row  table/table-row)
