
(ns fruits.html.check
    (:refer-clojure :exclude [empty?])
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn html?
  ; @important
  ; This function is incomplete and may not behave as expected.
  ;
  ; @description
  ; Returns TRUE if the given 'n' value is a HTML string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (html? "Paragraph #1")
  ; =>
  ; false
  ;
  ; @usage
  ; (html? "<p>Paragraph #1</p>")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n])
  ; TODO

(defn blank?
  ; @description
  ; Returns TRUE if the given 'n' value is a blank HTML string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (blank? "<p> </p><p>\n</p>")
  ; =>
  ; true
  ;
  ; @usage
  ; (blank? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (-> n (string/remove-part #"\<[^\>]*\>")
        (string/remove-part #"\s")
        (clojure.core/empty?)))

(defn not-blank?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonblank HTML string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (not-blank? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ; =>
  ; true
  ;
  ; @usage
  ; (not-blank? "<p> </p><p>\n</p>")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (-> n blank? not))

(defn empty?
  ; @description
  ; Returns TRUE if the given 'n' value is an empty HTML string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (empty? "<p></p><p></p>")
  ; =>
  ; true
  ;
  ; @usage
  ; (empty? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (-> n (string/remove-part #"\<[^\>]*\>")
        (clojure.core/empty?)))

(defn not-empty?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonempty HTML string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (not-empty? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ; =>
  ; true
  ;
  ; @usage
  ; (not-empty? "<p></p><p></p>")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (-> n empty? not))
