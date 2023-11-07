
(ns string.convert
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-integer
  ; @description
  ; Converts the given 'n' string to an integer.
  ;
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
  #?(:cljs (cond (string?  n) (-> n js/parseInt)
                 (integer? n) (-> n))
     :clj  (cond (string?  n) (Integer. (re-find #"\d+" n))
                 (integer? n) (-> n))))

(defn to-capitalized
  ; @description
  ; Makes the given 'n' value (converted to string) capitalized.
  ;
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
  ; @description
  ; Makes the given 'n' value (converted to string) uppercase.
  ;
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
  ; @description
  ; Makes the given 'n' value (converted to string) lowercase.
  ;
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
