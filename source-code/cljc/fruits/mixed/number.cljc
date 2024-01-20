
(ns fruits.mixed.number
    (:require [fruits.mixed.convert :as convert]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn add-numbers
  ; @description
  ; Adds the values of the given parameters that can be converted to a number.
  ;
  ; @param (list of *) abc
  ;
  ; @usage
  ; (add-numbers 1 2 "3")
  ; =>
  ; 6
  ;
  ; @usage
  ; (add-numbers 1 2 "3" "a")
  ; =>
  ; 6
  ;
  ; @return (number)
  [& abc]
  (letfn [(f0 [result x] (+ result (convert/to-number x)))]
         (reduce f0 0 abc)))

(defn subtract-numbers
  ; @description
  ; Subtracts the values of the given parameters that can be converted to a number.
  ;
  ; @param (list of *) abc
  ;
  ; @usage
  ; (subtract-numbers 1 2 "3")
  ; =>
  ; -4
  ;
  ; @usage
  ; (subtract-numbers 1 2 "3" "a")
  ; =>
  ; -4
  ;
  ; @return (number)
  [& abc]
  (letfn [(f0 [result x]
              (- result (convert/to-number x)))]
         (reduce f0 0 abc)))

(defn multiply-numbers
  ; @description
  ; Multiplies the values of the given parameters that can be converted to a number.
  ;
  ; @param (list of *) abc
  ;
  ; @usage
  ; (multiply-numbers 1 2 "3")
  ; =>
  ; 6
  ;
  ; @usage
  ; (multiply-numbers 1 2 "3" "a")
  ; =>
  ; 0
  ;
  ; @return (number)
  [& abc]
  (letfn [(f0 [result x]
              (* result (convert/to-number x)))]
         (reduce f0 1 abc)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn update-number
  ; @description
  ; Converts the given 'n' value into number and applies the given 'f' function on it.
  ;
  ; @param (integer or string) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-number "123" inc)
  ; =>
  ; 124
  ;
  ; @usage
  ; (update-number "123" + 1)
  ; =>
  ; 124
  ;
  ; @usage
  ; (update-number 123 + 1)
  ; =>
  ; 124
  ;
  ; @usage
  ; (update-number "abc-123.456def789" - 10)
  ; =>
  ; -133.456
  ;
  ; @return (number)
  [n f & params]
  (letfn [(f0 [%] (apply f % params))]
         (-> n convert/to-number f0)))

(defn update-number-part
  ; @description
  ; - Converts the given 'n' value into number and applies the given 'f' function on it.
  ; - Keeps the parts of the given 'n' value that cannot be converted into number and returns the output as a string.
  ;
  ; @param (integer or string) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-number-part "123" inc)
  ; =>
  ; "124"
  ;
  ; @usage
  ; (update-number-part "123" + 1)
  ; =>
  ; "124"
  ;
  ; @usage
  ; (update-number-part 123 + 1)
  ; =>
  ; "124"
  ;
  ; @usage
  ; (update-number-part "abc-123.456def789" - 10)
  ; =>
  ; "abc-133.456def789"
  ;
  ; @usage
  ; (update-number-part "Value: +10" - 12)
  ; =>
  ; "Value: -2"
  ;
  ; @return (string)
  [n f & params]
  ; - This function, unlike other number related functions in the 'fruits.mixed.api' library,
  ;   uses a regex pattern that matches the leading plus sign, and the leading zeros as well!
  ;   E.g., (update-number-part "Value: +10" - 12) => "value: -2"   <- Good :)
  ;         (update-number-part "Value: +10" - 12) => "value: +-2"  <- Bad  :(
  ;   E.g., (update-number-part "Value: 05"  + 20) => "value: 25"   <- Good :)
  ;         (update-number-part "Value: 05"  + 20) => "value: 025"  <- Bad  :(
  ; - Another solution could be if this function would provide the number part as a string to the 'f' function:
  ;   E.g., (update-number-part "Value: +10" println) => (println "+10") <- Maybe better for this function?
  ;         (update-number-part "Value: +10" println) => (println 10)    <- This is how it works now.
  (let [n (str n)]
       (if-let [number (re-find #"[\-\+]?[\d]+[\.]*[\d]*" n)]
               (let [number-starts-at (clojure.string/index-of n number)
                     number-ends-at   (+ number-starts-at (count number))]
                    (str (subs n 0 number-starts-at)
                         (apply update-number number f params)
                         (subs n number-ends-at))))))
