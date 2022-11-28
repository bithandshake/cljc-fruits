
(ns syntax.convert
    (:require [candy.api  :refer [return]]
              [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-snake-case
  ; @param (string) n
  ;
  ; @example
  ; (to-snake-case "SnakeCase")
  ; =>
  ; "snake-case"
  ;
  ; @return (string)
  [n]
  (let [count (count n)]
       (letfn [(f [result cursor]
                  (if (= count cursor)
                      (return result)
                      (let [char (subs n cursor (inc cursor))]
                           (if (= char (string/to-uppercase char))
                               (f (str (subs n 0 cursor)
                                       (if (not= cursor 0) "-")
                                       (string/to-lowercase char)
                                       (subs n (inc cursor)))
                                  (inc cursor))
                               (f result (inc cursor))))))]
              (f n 0))))

(defn ToCamelCase
  ; WARNING! INCOMPLETE! DO NOT USE!
  ;
  ; @param (string) n
  ;
  ; @example
  ; (ToCamelCase "camel-case")
  ; =>
  ; "CamelCase"
  ;
  ; @return (string)
  [n])
  ; TODO
