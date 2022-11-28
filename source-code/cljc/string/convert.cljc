
(ns string.convert
    (:require [clojure.string]
              [candy.api :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-integer
  ; @param (integer or string) n
  ;
  ; @usage
  ; (to-integer "420")
  ;
  ; @example
  ; (to-integer "420")
  ; =>
  ; 420
  ;
  ; @example
  ; (to-integer 420)
  ; =>
  ; 420
  ;
  ; @return (integer)
  [n]
  #?(:cljs (cond (string?  n) (js/parseInt n)
                 (integer? n) (return      n))
     :clj  (cond (string?  n) (Integer. (re-find #"\d+" n))
                 (integer? n) (return      n))))

(defn to-capitalized
  ; @param (*) n
  ;
  ; @usage
  ; (to-capitalized "abc")
  ;
  ; @example
  ; (to-capitalized "abc")
  ; =>
  ; "Abc"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/capitalize))

(defn to-uppercase
  ; @param (*) n
  ;
  ; @usage
  ; (to-uppercase "abc")
  ;
  ; @example
  ; (to-uppercase "abc")
  ; =>
  ; "ABC"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/upper-case))

(defn to-lowercase
  ; @param (*) n
  ;
  ; @usage
  ; (to-lowercase "ABC")
  ;
  ; @example
  ; (to-lowercase "ABC")
  ; =>
  ; "abc"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/lower-case))
