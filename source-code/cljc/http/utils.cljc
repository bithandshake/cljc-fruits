
(ns http.utils
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn capitalize-header
  ; @param (keyword) header-key
  ;
  ; @usage
  ; (capitalize-header :user-agent)
  ;
  ; @example
  ; (capitalize-header :user-agent)
  ; =>
  ; "User-Agent"
  ;
  ; @return (string)
  [header-key]
  (as-> header-key % (name %)
                     (clojure.string/split % #"\-")
                     (map clojure.string/capitalize %)
                     (clojure.string/join "-" %)))
