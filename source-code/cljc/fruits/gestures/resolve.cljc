
(ns fruits.gestures.resolve
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn resolve-variable
  ; @description
  ; Replaces variables in the given 'text' string with their corresponding values.
  ;
  ; @param (string) text
  ; @param (list of vectors) variables
  ; [(string) variable-value
  ;  (string) variable-name]
  ;
  ; @usage
  ; (resolve-variable "My favorite color is @color."
  ;                   ["red" "@color"])
  ; =>
  ; "My favorite color is red."
  ;
  ; @usage
  ; (resolve-variable "My favorite color is @color."
  ;                   ["red" "@color"]
  ;                   ["red" "@szin"])
  ; =>
  ; "My favorite color is red."
  ;
  ; @return (string)
  [text & [variables]]
  (letfn [(f0 [result [variable-value variable-name]]
              (cond (nil?              variable-value) (->                  result)
                    (number?           variable-value) (string/replace-part result variable-name variable-value)
                    (string/not-empty? variable-value) (string/replace-part result variable-name variable-value)
                    :return result))]
         (reduce f0 text variables)))
