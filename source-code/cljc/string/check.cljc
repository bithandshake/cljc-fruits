
(ns string.check
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

(defn nonempty?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonempty string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (nonempty? "abc")
  ;
  ; @example
  ; (nonempty? "abc")
  ; =>
  ; true
  ;
  ; @example
  ; (nonempty? "")
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

(defn nonblank?
  ; @description
  ; Returns TRUE if the given 'n' value is a string that contains more than whitespaces and newlines.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (nonblank? "abc")
  ;
  ; @example
  ; (nonblank? "abc")
  ; =>
  ; true
  ;
  ; @example
  ; (nonblank? " ")
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
  ; Returns TRUE if the given 'n' value (converted to string) is in lowercase format.
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
  ; Returns TRUE if the given 'n' value (converted to string) is in uppercase format.
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
