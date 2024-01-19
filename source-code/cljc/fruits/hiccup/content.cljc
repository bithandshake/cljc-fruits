
(ns fruits.hiccup.content
    (:require [fruits.hiccup.convert :as convert]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn content-length
  ; @description
  ; Returns the length of content within the given 'n' HICCUP value.
  ;
  ; @param (hiccup) n
  ;
  ; @usage
  ; (content-length [:div "Hello World!"])
  ; =>
  ; 12
  ;
  ; @return (integer)
  [n]
  (if (-> n vector?)
      (-> n convert/to-string count)))
