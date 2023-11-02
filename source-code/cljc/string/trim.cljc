
(ns string.trim
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn trim
  ; @param (*) n
  ;
  ; @usage
  ; (trim " abc")
  ;
  ; @example
  ; (trim " abc  ")
  ; =>
  ; "abc"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/trim))

(defn trim-whitespaces
  ; @param (*) n
  ;
  ; @usage
  ; (trim-whitespaces "a  b  c")
  ;
  ; @example
  ; (trim-whitespaces "a  b  c")
  ; =>
  ; "a b c"
  ;
  ; @return (string)
  [n]
  ; "\h" matches horizontal whitespace, which includes spaces and tabs but excludes newline.
  (-> n str (clojure.string/replace #"[\h]{1,}" " ")))
