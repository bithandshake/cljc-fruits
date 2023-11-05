
(ns syntax.check
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn position-escaped?
  [n position])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn position-commented?
  ; @description
  ; - Returns TRUE if the given position in the 'n' string is in a commented section.
  ; - Quoted comment tags might cause false output.
  ;
  ; @param (string) n
  ; @param (dex) position
  ; @param (string) comment-open-tag
  ; Default: ";"
  ; @param (string)(opt) comment-close-tag
  ; Default: "\n"
  ;
  ; @usage
  ; (position-commented? "(defn my-function [])\n ; My comment\n" 5)
  ;
  ; @example
  ; (position-commented? "(defn my-function [])\n ; My comment\n" 5)
  ; =>
  ; false
  ;
  ; @example
  ; (position-commented? "(defn my-function [])\n ; My comment\n" 25)
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n position]
   (position-commented? n position ";" "\n"))

  ([n position comment-open-tag]
   (position-commented? n position comment-open-tag "\n"))

  ([n position comment-open-tag comment-close-tag]
   (boolean (let [observed-part (string/part n 0 position)]
                 (if-let [last-open-pos (string/last-dex-of observed-part comment-open-tag)]
                         (-> n (string/part last-open-pos position)
                               (string/contains-part? comment-close-tag)
                               (not)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn position-quoted?
  ; @description
  ; Returns TRUE if the given position in the 'n' string is in a quoted section.
  ;
  ; @param (string) n
  ; @param (dex) position
  ; @param (map)(opt) options
  ; {:comment-close-tag (string)(opt)
  ;   Default: "\n"
  ;  :comment-open-tag (string)(opt)
  ;   Default ";"
  ;  :ignore-comments? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (position-quoted? "\"My quote\" My string" 3)
  ;
  ; @example
  ; (position-quoted? "\"My quote\" My string" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (position-quoted? "\"My quote\" My string" 13)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n position])
