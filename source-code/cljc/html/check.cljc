
(ns html.check
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn blank?
  ; @param (string) n
  ;
  ; @example
  ; (blank? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ; =>
  ; false
  ;
  ; @example
  ; (blank? "<p> </p><p>\n</p>")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (-> n (string/remove-part #"<.*>")
        (string/remove-part #"</.*>")
        (string/remove-part #"<.*/>")
        (string/remove-part #" ")
        (string/remove-part #"\r")
        (string/remove-part #"\t")
        (string/remove-part #"\n")
        (empty?)))

(defn nonblank?
  ; @param (string) n
  ;
  ; @example
  ; (nonblank? "<p> </p><p>\n</p>")
  ; =>
  ; false
  ;
  ; @example
  ; (nonblank? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (-> n blank? not))
