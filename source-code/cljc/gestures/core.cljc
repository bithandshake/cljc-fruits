
(ns gestures.core
    (:require [candy.api         :refer [return]]
              [gestures.type     :as type]
              [loop.api          :refer [do-while]]
              [mixed.api         :as mixed]
              [mid-fruits.string :as string]
              [mid-fruits.vector :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-label->copy-label
  ; Az original-label paraméterként átadott címkét ellátja a suffix paraméterként
  ; átadott toldalékkal. Névütközés esetén sorszámmal látja el a címkét.
  ;
  ; @param (string) item-label
  ; @param (strings in vector)(opt) concurent-labels
  ;
  ; @example
  ;  (item-label->copy-label "My item" ["Your item" "Their item"])
  ;  =>
  ;  "My item #2"
  ;
  ; @example
  ;  (item-label->copy-label "My item" ["My item" "My item #2"])
  ;  =>
  ;  "My item #3"
  ;
  ; @example
  ;  (item-label->copy-label "My item #2" ["Your item"])
  ;  =>
  ;  "My item #3"
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
                           (str n " #2")))]
          (do-while f item-label test-f))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn resolve-variable
  ; @param (string) text
  ; @param (vectors in vector) variables
  ;  [[(string) variable-value
  ;    (list of strings) variable-names]
  ;   [...]]}
  ;
  ; @example
  ;  (resolve-variable "My favorite color: @color"
  ;                    [["red" "@color"]])
  ;
  ; @example
  ;  (resolve-variable "My favorite color: @color"
  ;                    [["red" "@color" "@szin"]])
  ;
  ; @return (string)
  [text variables]
  (letfn [(f [result [variable-value & variable-names]]
             (letfn [(f [result variable-name]
                        (cond (nil?             variable-value) (return              result)
                              (number?          variable-value) (string/replace-part result variable-name variable-value)
                              (string/nonempty? variable-value) (string/replace-part result variable-name variable-value)
                              :return result))]
                    (reduce f result variable-names)))]
         (reduce f text variables)))
