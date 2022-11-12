
(ns regex.api
    (:require [regex.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; regex.core
(def re-match?    core/re-match?)
(def re-mismatch? core/re-mismatch?)
(def first-dex-of core/first-dex-of)
(def last-dex-of  core/last-dex-of)
(def nth-dex-of   core/nth-dex-of)
