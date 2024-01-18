
(ns fruits.uri.pretty
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn pretty-url
  ; @param (string) n
  ;
  ; @usage
  ; (pretty-url "https://my-domain.com")
  ; =>
  ; "my-domain.com"
  ;
  ; @usage
  ; (pretty-url "https://my-domain.com/")
  ; =>
  ; "my-domain.com"
  ;
  ; @return (string)
  [n]
  (-> n (string/not-ends-with!        "/")
        (string/after-first-occurence "://" {:return? true})))
