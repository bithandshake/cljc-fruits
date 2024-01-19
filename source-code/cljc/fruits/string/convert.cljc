
(ns fruits.string.convert
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-integer
  ; @description
  ; Converts the given 'n' string into integer.
  ;
  ; @param (integer or string) n
  ;
  ; @usage
  ; (to-integer "420")
  ; =>
  ; 420
  ;
  ; @usage
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
  ; Converts the given 'n' value into capitalized string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-capitalized "abc")
  ; =>
  ; "Abc"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/capitalize))

(defn to-uppercase
  ; @description
  ; Converts the given 'n' value into uppercase string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-uppercase "abc")
  ; =>
  ; "ABC"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/upper-case))

(defn to-lowercase
  ; @description
  ; Converts the given 'n' value into lowercase string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-lowercase "ABC")
  ; =>
  ; "abc"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/lower-case))

(defn to-nil
  ; @description
  ; Converts the given 'n' string into NIL.

  ; @param (string) n
  ; @param (map)(opt) options
  ; {:if-empty? (boolean)(opt)
  ;   Converts only if the given 'n' string is empty.
  ;   Default: true}
  ;
  ; @usage
  ; (to-nil "")
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-nil "abc")
  ; =>
  ; "abc"
  ;
  ; @return (nil or string)
  ([n]
   (to-nil n {}))

  ([n {:keys [if-empty?] :or {if-empty? true}}]
   ; Alternative: 'not-empty'
   ; https://clojuredocs.org/clojure.core/not-empty
   (let [n (str n)]
        (cond (-> n empty?)      (-> nil)
              (-> if-empty? not) (-> nil)
              :return n))))

(defn to-seqable
  ; @description
  ; Converts the given 'n' value into string, in case it does not implement the ISeqable protocol.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-seqable {:a "A"})
  ; =>
  ; {:a "A"}
  ;
  ; @usage
  ; (to-seqable [:a :b :c])
  ; =>
  ; [:a :b :c]
  ;
  ; @usage
  ; (to-seqable nil)
  ; =>
  ; ""
  ;
  ; @usage
  ; (to-seqable "abc")
  ; =>
  ; "abc"
  ;
  ; @usage
  ; (to-seqable 123)
  ; =>
  ; "123"
  ;
  ; @return (string or seqable *)
  [n]
  (if (-> n seqable?)
      (-> n)
      (-> n str)))
