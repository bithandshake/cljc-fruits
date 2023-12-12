
(ns fruits.string.convert
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-integer
  ; @description
  ; Converts the given 'n' string into an integer.
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

(defn to-nil
  ; @param (*) n
  ; @param (map)(opt) options
  ; {:if-empty? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (to-nil "")
  ;
  ; @example
  ; (to-nil "")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-nil "abc")
  ; =>
  ; "abc"
  ;
  ; @return (nil or string)
  ([n]
   (to-nil n {}))

  ([n {:keys [if-empty?] :or {if-empty? true}}]
   (let [n (str n)]
        (cond (-> n empty?)      (-> nil)
              (-> if-empty? not) (-> nil)
              :return n))))
