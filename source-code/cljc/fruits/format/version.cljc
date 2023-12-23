
(ns fruits.format.version
    (:require [fruits.format.number :as number]
              [fruits.mixed.api     :as mixed]
              [fruits.regex.api     :as regex]
              [fruits.string.api    :as string]
              [fruits.vector.api    :as vector]))

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
  (letfn [(f0 [n delimiter-positions]
              (if (vector/nonempty? delimiter-positions)
                  (f0 (string/insert-part n "." (last delimiter-positions))
                      (vector/remove-last-item delimiter-positions))
                  (-> n)))]

         ; ...
         (if-let [version (-> n (regex/re-first #"\d(\.\d)*") first)]
                 (loop [version version delimiter-positions []]
                       (if-let [delimiter-position (string/first-dex-of version ".")]
                               (recur (string/remove-first-occurence version ".")
                                      (conj delimiter-positions delimiter-position))
                               (f0 ; BUG#0080
                                   ; The leading zeros have to be removed to prevent the 'update-number'
                                   ; function from parsing the 'n' string using a non-decimal system (e.g., "008").
                                   (-> version (number/remove-leading-zeros)
                                               (mixed/update-number inc)
                                               (number/leading-zeros (count version)))
                                   ; If the 'version' string contained only "9" digits before the increasing,
                                   ; an offset must be applied on the delimiter positions,
                                   ; otherwise "9.9" might be updated into "1.00" instead of "10.0".
                                   (if (-> version             (regex/re-match? #"^[9]{1,}$"))
                                       (-> delimiter-positions (vector/->items inc))
                                       (-> delimiter-positions))))))))
