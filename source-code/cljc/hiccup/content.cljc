
(ns hiccup.content
    (:require [hiccup.convert :as convert]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn content-length
  ; @param (hiccup) n
  ;
  ; @example
  ;  (content-length [:div "Hello World!"])
  ;  =>
  ;  12
  ;
  ; @return (integer)
  [n]
  (-> n convert/to-string count))
