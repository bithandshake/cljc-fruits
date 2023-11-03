
(ns string.trim
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn trim
  ; @description
  ; Trims both ends of the given 'n' string by removing the leading and trailing whitespaces.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (trim "  abc  ")
  ;
  ; @example
  ; (trim "  abc  ")
  ; =>
  ; "abc"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/trim))

(defn trim-start
  ; @description
  ; Trims the beginning of the given 'n' string by removing the leading whitespaces.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (trim-start "  abc  ")
  ;
  ; @example
  ; (trim-start "  abc  ")
  ; =>
  ; "abc  "
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/triml))

(defn trim-end
  ; @description
  ; Trims the end of the given 'n' string by removing the trailing whitespaces.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (trim-end "  abc  ")
  ;
  ; @example
  ; (trim-end "  abc  ")
  ; =>
  ; "abc  "
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/trimr))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn trim-newlines
  ; @description
  ; Trims the trailing newlines of the given 'n' string by removing the all newlines from the end.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (trim-newlines "abc \r\n")
  ;
  ; @example
  ; (trim-newlines "abc \r\n")
  ; =>
  ; "abc "
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/trim-newline))

(defn trim-gaps
  ; @description
  ; Trims the whitespace gaps in the given 'n' string by removing the duplicated whitespaces.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (trim-gaps "  a  b  c  ")
  ;
  ; @example
  ; (trim-gaps "  a  b  c  ")
  ; =>
  ; " a b c "
  ;
  ; @return (string)
  [n]
  ; "\h" matches horizontal whitespace, which includes spaces and tabs but excludes newline.
  (-> n str (clojure.string/replace #"[\h]{1,}" " ")))
