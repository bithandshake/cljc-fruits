
(ns fruits.mixed.number
    (:require [fruits.mixed.convert :as convert]
              [fruits.mixed.type    :as type]
              [fruits.reader.api    :as reader]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn add-numbers
  ; @description
  ; Adds the values of the given parameters that are convertable to a number.
  ;
  ; @param (list of *) abc
  ;
  ; @example
  ; (add-numbers 1 2 "3")
  ; =>
  ; 6
  ;
  ; @example
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
  ; Subtracts the values of the given parameters that are convertable to a number.
  ;
  ; @param (list of *) abc
  ;
  ; @example
  ; (subtract-numbers 1 2 "3")
  ; =>
  ; -4
  ;
  ; @example
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
  ; Multiplies the values of the given parameters that are convertable to a number.
  ;
  ; @param (list of *) abc
  ;
  ; @example
  ; (multiply-numbers 1 2 "3")
  ; =>
  ; 6
  ;
  ; @example
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
  ; @param (integer or string) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @example
  ; (update-number "123" inc)
  ; =>
  ; 124
  ;
  ; @example
  ; (update-number "123" + 1)
  ; =>
  ; 124
  ;
  ; @example
  ; (update-number 123 + 1)
  ; =>
  ; 124
  ;
  ; @example
  ; (update-number "abc-123.456def789" - 10)
  ; =>
  ; -133.456
  ;
  ; @return (number)
  [n f & params]
  (letfn [(f0 [%] (apply f % params))]
         (-> n convert/to-number f0)))

(defn update-number-part
  ; @param (integer or string) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @example
  ; (update-number-part "123" inc)
  ; =>
  ; "124"
  ;
  ; @example
  ; (update-number-part "123" + 1)
  ; =>
  ; "124"
  ;
  ; @example
  ; (update-number-part 123 + 1)
  ; =>
  ; "124"
  ;
  ; @example
  ; (update-number-part "abc-123.456def789" - 10)
  ; =>
  ; "abc-133.456def789"
  ;
  ; @return (string)
  [n f & params]
  (let [n (str n)]
       (if-let [number (re-find #"[\-]?[\d]+[\.]*[\d]*" n)]
               (let [number-starts-at (clojure.string/index-of n number)
                     number-ends-at   (+ number-starts-at (count number))]
                    (str (subs n 0 number-starts-at)
                         (apply update-number number f params)
                         (subs n number-ends-at))))))
