
(ns string.check
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn blank?
  ; @description
  ; Returns TRUE if the given 'n' value is an empty string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (blank? "")
  ;
  ; @example
  ; (blank? "abc")
  ; =>
  ; false
  ;
  ; @example
  ; (blank? "")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n string?)
       (-> n empty?)))

(defn nonblank?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonempty string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (nonblank? "abc")
  ;
  ; @example
  ; (nonblank? "")
  ; =>
  ; false
  ;
  ; @example
  ; (nonblank? "abc")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n string?)
       (-> n empty? not)))

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
