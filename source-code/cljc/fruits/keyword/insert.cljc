
(ns fruits.keyword.insert
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn prepend
  ; @description
  ; Prepends the given 'x' value to the given 'n' value.
  ;
  ; @param (keyword) n
  ; @param (keyword) x
  ; @param (string)(opt) delimiter
  ;
  ; @usage
  ; (prepend :a :b)
  ; =>
  ; :ba
  ;
  ; @usage
  ; (prepend :a/b :c)
  ; =>
  ; :a/cb
  ;
  ; @usage
  ; (prepend :a/b :c "--")
  ; =>
  ; :a/c--b
  ;
  ; @return (keyword)
  ([n x]
   (let [n (mixed/to-keyword n)
         x (mixed/to-keyword x)]
        (if-let [namespace (namespace n)]
                (keyword namespace (str (name x) (name x)))
                (keyword           (str (name x) (name x))))))

  ([n x delimiter]
   (let [n (mixed/to-keyword n)
         x (mixed/to-keyword x)
         delimiter (str delimiter)]
        (if-let [namespace (namespace n)]
                (keyword namespace (str (name x) delimiter (name x)))
                (keyword           (str (name x) delimiter (name x)))))))

(defn append
  ; @description
  ; Appends the given 'x' value to the given 'n' value.
  ;
  ; @param (keyword) n
  ; @param (keyword) x
  ; @param (string)(opt) delimiter
  ;
  ; @usage
  ; (append :a :b)
  ; =>
  ; :ab
  ;
  ; @usage
  ; (append :a/b :c)
  ; =>
  ; :a/bc
  ;
  ; @usage
  ; (append :a/b :c "--")
  ; =>
  ; :a/b--c
  ;
  ; @return (keyword)
  ([n x]
   (let [n (mixed/to-keyword n)
         x (mixed/to-keyword x)]
        (if-let [namespace (namespace n)]
                (keyword namespace (str (name n) (name x)))
                (keyword           (str (name n) (name x))))))

  ([n x delimiter]
   (let [n (mixed/to-keyword n)
         x (mixed/to-keyword x)
         delimiter (str delimiter)]
        (if-let [namespace (namespace n)]
                (keyword namespace (str (name n) delimiter (name x)))
                (keyword           (str (name n) delimiter (name x)))))))
