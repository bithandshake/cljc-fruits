
(ns fruits.mixed.core
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
  ; (add-numbers 1 "3")
  ; =>
  ; 4
  ;
  ; @example
  ; (add-numbers 1 "3" "a")
  ; =>
  ; 4
  ;
  ; @return (integer)
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
  ; (subtract-numbers 1 "3")
  ; =>
  ; -2
  ;
  ; @example
  ; (subtract-numbers 1 "3" "a")
  ; =>
  ; -2
  ;
  ; @return (integer)
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
  ; (multiply-numbers 1 "3")
  ; =>
  ; 3
  ;
  ; @example
  ; (multiply-numbers 1 "3" "a")
  ; =>
  ; 0
  ;
  ; @return (integer)
  [& abc]
  (letfn [(f0 [result x]
              (* result (convert/to-number x)))]
         (reduce f0 1 abc)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn update-whole-number
  ; @param (integer or string) n
  ; @param (function) f
  ; @param (*)(opt) x
  ;
  ; @example
  ; (update-whole-number "12" inc)
  ; =>
  ; "13"
  ;
  ; @example
  ; (update-whole-number "12" + 3)
  ; =>
  ; "15"
  ;
  ; @example
  ; (update-whole-number 12 + 3)
  ; =>
  ; 15
  ;
  ; @example
  ; (update-whole-number "abCd12" + 3)
  ; =>
  ; "abCd12"
  ;
  ; @return (integer or string)
  ([n f]
   (update-whole-number n f nil))

  ([n f x]
   (letfn [(f0 [n] (if x (f n x) (f n)))]
          (cond (-> n           integer?) (-> n f0)
                (-> n type/whole-number?) (-> n reader/read-edn f0)
                (-> n              some?) (-> n)))))
