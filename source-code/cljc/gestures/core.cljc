
(ns gestures.core
    (:require [gestures.type :as type]
              [loop.api      :refer [do-while]]
              [mixed.api     :as mixed]
              [string.api    :as string]
              [vector.api    :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-label->copy-label
  ; @description
  ; - Appends a copy marker suffix to the given 'item-label' string.
  ; - Optionally avoids name conflicts by checking other labels in the given 'concurent-labels' vector.
  ;
  ; @param (string) item-label
  ; @param (strings in vector)(opt) concurent-labels
  ;
  ; @usage
  ; (item-label->copy-label "My item")
  ;
  ; @example
  ; (item-label->copy-label "My item")
  ; =>
  ; "My item #2"
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
   (letfn [(f0 [n] (-> (vector/contains-item? concurent-labels n) not))
           (f1 [n] (if (type/ordered-label? n)
                       (let [copy-dex      (string/after-last-occurence  n "#" {:return? false})
                             label-base    (string/before-last-occurence n "#" {:return? true})
                             next-copy-dex (mixed/update-whole-number copy-dex inc)]
                            (str label-base "#" next-copy-dex))
                       (if (string/nonempty? n)
                           (str n " #2")
                           (str    "#2"))))]
          (do-while f1 item-label f0))))

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
  (letfn [(f0 [result [variable-value variable-name]]
              (cond (nil?             variable-value) (->                  result)
                    (number?          variable-value) (string/replace-part result variable-name variable-value)
                    (string/nonempty? variable-value) (string/replace-part result variable-name variable-value)
                    :return result))]
         (reduce f0 text variables)))
