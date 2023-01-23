
(ns gestures.core
    (:require [gestures.type :as type]
              [loop.api      :refer [do-while]]
              [mixed.api     :as mixed]
              [noop.api      :refer [return]]
              [string.api    :as string]
              [vector.api    :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-label->copy-label
  ; @description
  ; Appends a copy marking suffix to the given 'item-label'.
  ; Avoids name conflicts by checking the concurent labels.
  ;
  ; @param (string) item-label
  ; @param (strings in vector)(opt) concurent-labels
  ;
  ; @example
  ; (item-label->copy-label "My item" ["Your item" "Their item"])
  ; =>
  ; "My item #2"
  ;
  ; @example
  ; (item-label->copy-label "My item" ["My item" "My item #2"])
  ; =>
  ; "My item #3"
  ;
  ; @example
  ; (item-label->copy-label "My item #2" ["Your item"])
  ; =>
  ; "My item #3"
  ;
  ; @example
  ; (item-label->copy-label "" [])
  ; =>
  ; "#2"
  ;
  ; @return (string)
  ([item-label]
   (item-label->copy-label item-label []))

  ([item-label concurent-labels]
   (letfn [(test-f [n] (not (vector/contains-item? concurent-labels n)))
           (f      [n] (if (type/ordered-label? n)
                           (let [copy-dex      (string/after-last-occurence  n "#" {:return? false})
                                 label-base    (string/before-last-occurence n "#" {:return? true})
                                 next-copy-dex (mixed/update-whole-number copy-dex inc)]
                                (str label-base "#" next-copy-dex))
                           (if (string/nonblank? n)
                               (str n " #2")
                               (str    "#2"))))]
          (do-while f item-label test-f))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn resolve-variable
  ; @description
  ; Replaces variables in strings with its values.
  ;
  ; @param (string) text
  ; @param (list of vectors) variables
  ; [(string) variable-value
  ;  (string) variable-name]
  ;
  ; @example
  ; (resolve-variable "My favorite color is @color."
  ;                   ["red" "@color"])
  ; =>
  ; "My favorite color is red."
  ;
  ; @example
  ; (resolve-variable "My favorite color is @color."
  ;                   ["red" "@color"]
  ;                   ["red" "@szin"])
  ; =>
  ; "My favorite color is red."
  ;
  ; @return (string)
  [text & [variables]]
  (letfn [(f [result [variable-value variable-name]]
             (cond (nil?             variable-value) (return              result)
                   (number?          variable-value) (string/replace-part result variable-name variable-value)
                   (string/nonblank? variable-value) (string/replace-part result variable-name variable-value)
                   :return result))]
         (reduce f text variables)))
