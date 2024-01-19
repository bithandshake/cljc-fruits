
(ns fruits.string.check
    (:refer-clojure :exclude [empty?])
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn empty?
  ; @description
  ; Returns TRUE if the given 'n' value is an empty string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (empty? "")
  ;
  ; @example
  ; (empty? "")
  ; =>
  ; true
  ;
  ; @example
  ; (empty? "abc")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n string?)
       (-> n clojure.core/empty?)))

(defn not-empty?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonempty string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (not-empty? "abc")
  ;
  ; @example
  ; (not-empty? "abc")
  ; =>
  ; true
  ;
  ; @example
  ; (not-empty? "")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n string?)
       (-> n clojure.core/empty? not)))

(defn blank?
  ; @description
  ; Returns TRUE if the given 'n' value is a string that only contains whitespaces, newlines or even empty.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (blank? " ")
  ;
  ; @example
  ; (blank? " ")
  ; =>
  ; true
  ;
  ; @example
  ; (blank? "abc")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n string?)
       (-> n clojure.string/trim empty?)))

(defn not-blank?
  ; @description
  ; Returns TRUE if the given 'n' value is a string that contains more than whitespaces and newlines.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (not-blank? "abc")
  ;
  ; @example
  ; (not-blank? "abc")
  ; =>
  ; true
  ;
  ; @example
  ; (not-blank? " ")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n string?)
       (-> n clojure.string/trim empty? not)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn lowercase?
  ; @description
  ; Returns TRUE if the given 'n' string is in lowercase format.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (lowercase? "abc")
  ;
  ; @example
  ; (lowercase? "abc")
  ; =>
  ; true
  ;
  ; @example
  ; (lowercase? "Abc")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (let [n (str n)]
       (= n (clojure.string/lower-case n))))

(defn uppercase?
  ; @description
  ; Returns TRUE if the given 'n' string is in uppercase format.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (uppercase? "ABC")
  ;
  ; @example
  ; (uppercase? "ABC")
  ; =>
  ; true
  ;
  ; @example
  ; (uppercase? "Abc")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (let [n (str n)]
       (= n (clojure.string/upper-case n))))
