
(ns reader.api
    (:require [reader.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; reader.core
(def read-str      core/read-str)
(def mixed->string core/mixed->string)
(def string->mixed core/string->mixed)
(def string->map   core/string->map)
