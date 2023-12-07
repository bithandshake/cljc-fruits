
(ns format.version
    (:require [format.number :as number]
              [mixed.api     :as mixed]
              [regex.api     :refer [re-match?]]
              [string.api    :as string]
              [vector.api    :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn inc-version
  ; @param (string) n
  ;
  ; @usage
  ; (inc-version "0.0.1")
  ;
  ; @example
  ; (inc-version "1.2.19")
  ; =>
  ; "1.2.20"
  ;
  ; @example
  ; (inc-version "0.0.99")
  ; =>
  ; "0.1.00"
  ;
  ; @example
  ; (inc-version "9.9")
  ; =>
  ; "10.0"
  ;
  ; @return (string)
  [n]
  (letfn [; @param (string) n
          ; @param (integers in vector) separators
          ; The positions of delimiters (".") in the 'n' string.
          (implode-f [n separators]
                     (if (vector/nonempty? separators)
                         (implode-f (string/insert-part n "." (last separators))
                                    (vector/remove-last-item separators))
                         (-> n)))

          ; @param (string) n
          ; @param (integers in vector) separators
          ; The positions of delimiters (".") in the 'n' string.
          (explode-f [n separators]
                     (if-let [separator (string/first-dex-of n ".")]
                             (explode-f (string/remove-first-occurence n ".")
                                        (conj separators separator))
                             (implode-f ; BUG#0080
                                        ; The leading zeros have to be removed to prevent the 'update-whole-number'
                                        ; function from parsing the 'n' string (e.g., "008") using a non-decimal system.
                                        (-> n number/remove-leading-zeros (mixed/update-whole-number inc)
                                                                          (number/leading-zeros (count n)))
                                        ; If the 'n' string contains only "9" digits before the increasing,
                                        ; an offset must be applied on the positions of delimiters,
                                        ; otherwise "9.9" might be followed by "1.00" instead of "10.0".
                                        (if (re-match? n #"^[9]{1,}$")
                                            (-> separators (vector/->items inc))
                                            (-> separators)))))]
         ; ...
         (explode-f n [])))
