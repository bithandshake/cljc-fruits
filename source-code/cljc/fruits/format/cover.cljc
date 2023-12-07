
(ns fruits.format.cover
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn cover-email-address
  ; @param (string) n
  ;
  ; @usage
  ; (cover-email-address "user@email.com")
  ;
  ; @example
  ; (cover-email-address "user@email.com")
  ; =>
  ; "u**r@email.com"
  ;
  ; @example
  ; (cover-email-address "username@email.com")
  ; =>
  ; "u******e@email.com"
  ;
  ; @return (string)
  [n]
  (if-let [user (string/before-first-occurence n "@" {:return? false})]
          (case (count user) 0 (str n)
                             1 (string/cover n "*")
                             2 (string/cover n "*" 1)
                               (string/cover n (string/repeat "*" (-> user count dec dec)) 1))))
