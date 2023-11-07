
(ns mixed.core
    (:require [mixed.convert :as convert]
              [mixed.type    :as type]
              [reader.api    :as reader]))

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
  (letfn [(f [result x]
             (+ result (convert/to-number x)))]
         (reduce f 0 abc)))

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
  (letfn [(f [result x]
             (- result (convert/to-number x)))]
         (reduce f 0 abc)))

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
  (letfn [(f [result x]
             (* result (convert/to-number x)))]
         (reduce f 1 abc)))

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
   (letfn [(update-f [n] (if x (f n x)
                               (f n)))]
          (cond (-> n           integer?)      (-> n update-f)
                (-> n type/whole-number?) (let [integer (reader/read-edn n)]
                                               (-> integer update-f))
                (-> n              some?)      (-> n)))))
