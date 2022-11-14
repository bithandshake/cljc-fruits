
(ns mixed.parse
    (:require [candy.api  :refer [return]]
              [mixed.type :as type]
              [reader.api :as reader]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-rational-number
  ; @param (*) n
  ;
  ; @example
  ;  (parse-rational-number 12)
  ;  =>
  ;  12
  ;
  ; @example
  ;  (parse-rational-number 12.1)
  ;  =>
  ;  12.1
  ;
  ; @example
  ;  (parse-rational-number "12.1")
  ;  =>
  ;  12.1
  ;
  ; @example
  ;  (parse-rational-number "abCd12.1")
  ;  =>
  ;  "abCd12.1"
  ;
  ; @return (*)
  [n]
  (cond (number?               n) (return               n)
        (type/rational-number? n) (reader/string->mixed n)
        (some?                 n) (return               n)))

(defn parse-number
  ; @param (*) n
  ;
  ; @example
  ;  (parse-number 12)
  ;  =>
  ;  12
  ;
  ; @example
  ;  (parse-number 12.1)
  ;  =>
  ;  12.1
  ;
  ; @example
  ;  (parse-number "12.1")
  ;  =>
  ;  12.1
  ;
  ; @example
  ;  (parse-number "abCd12.1")
  ;  =>
  ;  "abCd12.1"
  ;
  ; @return (*)
  [n]
  (parse-rational-number n))

(defn parse-whole-number
  ; @param (*) n
  ;
  ; @example
  ;  (parse-whole-number 12)
  ;  =>
  ;  12
  ;
  ; @example
  ;  (parse-whole-number "12")
  ;  =>
  ;  12
  ;
  ; @example
  ;  (parse-whole-number "abCd12")
  ;  =>
  ;  "abCd12"
  ;
  ; @return (*)
  [n]
  (cond (integer?           n) (return               n)
        (type/whole-number? n) (reader/string->mixed n)
        (some?              n) (return               n)))
