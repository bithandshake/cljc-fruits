
(ns fruits.string.nth
    (:require [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-character
  ; @description
  ; - Returns the first character of the given 'n' value (converted to string.)
  ; - Converts the output character (Java char type) into string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (first-character "abc")
  ;
  ; @example
  ; (first-character "abc")
  ; =>
  ; "a"
  ;
  ; @example
  ; (first-character {:a "A"})
  ; =>
  ; "{"
  ;
  ; @param (string)
  [n]
  (let [n (str n)]
       (-> n first str)))

(defn second-character
  ; @description
  ; - Returns the second character of the given 'n' value (converted to string.)
  ; - Converts the output character (Java char type) into string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (second-character "abc")
  ;
  ; @example
  ; (second-character "abc")
  ; =>
  ; "b"
  ;
  ; @example
  ; (second-character {:a "A"})
  ; =>
  ; ":"
  ;
  ; @param (string)
  [n]
  (let [n (str n)]
       (-> n second str)))

(defn last-character
  ; @description
  ; - Returns the first character of the given 'n' value (converted to string.)
  ; - Converts the output character (Java char type) into string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (last-character "abc")
  ;
  ; @example
  ; (last-character "abc")
  ; =>
  ; "c"
  ;
  ; @example
  ; (last-character {:a "A"})
  ; =>
  ; "}"
  ;
  ; @param (string)
  [n]
  (let [n (str n)]
       (-> n last str)))

(defn nth-character
  ; @description
  ; - Returns the nth character of the given 'n' value (converted to string.)
  ; - Converts the output character (Java char type) into string.
  ;
  ; @param (*) n
  ; @param (integer) th
  ;
  ; @usage
  ; (nth-character "abc" 2)
  ;
  ; @example
  ; (nth-character "abc" 2)
  ; =>
  ; "c"
  ;
  ; @example
  ; (nth-character {:a "A"} 1)
  ; =>
  ; ":"
  ;
  ; @param (string)
  [n th]
  (let [n  (str n)
        th (seqable/normalize-dex n th)]
       (-> n (nth th) str)))
