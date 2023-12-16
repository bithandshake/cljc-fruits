
(ns fruits.html.check
    (:refer-clojure :exclude [empty?])
    (:require [fruits.string.api :as string]))

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
  (-> n (string/remove-part #"\<[^\>]*\>")
        (string/remove-part #"\s")
        (clojure.core/empty?)))

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

(defn empty?
  ; @param (string) n
  ;
  ; @usage
  ; (empty? "<p></p><p></p>")
  ;
  ; @example
  ; (empty? "<p></p><p></p>")
  ; =>
  ; true
  ;
  ; @example
  ; (empty? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (-> n (string/remove-part #"\<[^\>]*\>")
        (clojure.core/empty?)))

(defn nonempty?
  ; @param (string) n
  ;
  ; @usage
  ; (nonempty? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ;
  ; @example
  ; (nonempty? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ; =>
  ; true
  ;
  ; @example
  ; (nonempty? "<p></p><p></p>")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (-> n empty? not))
