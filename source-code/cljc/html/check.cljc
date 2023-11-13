
(ns html.check
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn html?
  ; @param (string) n
  ;
  ; @usage
  ; (html? "<p>Paragraph #1</p>")
  ;
  ; @example
  ; (html? "Paragraph #1")
  ; =>
  ; false
  ;
  ; @example
  ; (html? "<p>Paragraph #1</p>")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n])
  ; TODO

(defn blank?
  ; @param (string) n
  ;
  ; @usage
  ; (blank? "<p> </p><p>\n</p>")
  ;
  ; @example
  ; (blank? "<p> </p><p>\n</p>")
  ; =>
  ; true
  ;
  ; @example
  ; (blank? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (-> n (string/remove-part #"<.*>")
        (string/remove-part #"</.*>")
        (string/remove-part #"<.*/>")
        (string/remove-part #"\s")
        (string/remove-part #"\r")
        (string/remove-part #"\t")
        (string/remove-part #"\n")
        (empty?)))

(defn nonblank?
  ; @param (string) n
  ;
  ; @usage
  ; (nonblank? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ;
  ; @example
  ; (nonblank? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ; =>
  ; true
  ;
  ; @example
  ; (nonblank? "<p> </p><p>\n</p>")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (-> n blank? not))
