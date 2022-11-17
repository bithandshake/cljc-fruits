
(ns string.transform
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn capitalize
  ; @param (*) n
  ;
  ; @usage
  ; (capitalize "abc")
  ;
  ; @example
  ; (capitalize "abc")
  ; =>
  ; "Abc"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/capitalize))

(defn uppercase
  ; @param (*) n
  ;
  ; @usage
  ; (uppercase "abc")
  ;
  ; @example
  ; (uppercase "abc")
  ; =>
  ; "ABC"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/upper-case))

(defn lowercase
  ; @param (*) n
  ;
  ; @usage
  ; (lowercase "ABC")
  ;
  ; @example
  ; (lowercase "ABC")
  ; =>
  ; "abc"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/lower-case))
