
(ns fruits.string.get
    (:require [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn first-character
  ; @description
  ; - Returns the first character of the given 'n' string.
  ; - Converts the output character (Java char type) to a string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (first-character "abc")
  ; =>
  ; "a"
  ;
  ; @usage
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
  ; - Returns the second character of the given 'n' string.
  ; - Converts the output character (Java char type) to a string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (second-character "abc")
  ; =>
  ; "b"
  ;
  ; @usage
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
  ; - Returns the first character of the given 'n' string.
  ; - Converts the output character (Java char type) to a string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (last-character "abc")
  ; =>
  ; "c"
  ;
  ; @usage
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
  ; - Returns the nth character of the given 'n' string.
  ; - Converts the output character (Java char type) to a string.
  ;
  ; @param (string) n
  ; @param (integer) th
  ;
  ; @usage
  ; (nth-character "abc" 2)
  ; =>
  ; "c"
  ;
  ; @usage
  ; (nth-character {:a "A"} 1)
  ; =>
  ; ":"
  ;
  ; @param (string)
  [n th]
  (let [n (str n)]
       (if-let [th (seqable/normalize-dex n th {:adjust? false :mirror? true})]
               (-> n (nth th) str))))
