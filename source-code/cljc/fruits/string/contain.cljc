
(ns fruits.string.contain
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn contains-part?
  ; @description
  ; Returns TRUE if the given 'n' string contains the given 'x' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ;
  ; @usage
  ; (contains-part? "abc" "ab")
  ; =>
  ; true
  ;
  ; @usage
  ; (contains-part? "abc" "cd")
  ; =>
  ; false
  ;
  ; @usage
  ; (contains-part? "abc" "")
  ; =>
  ; true
  ;
  ; @usage
  ; (contains-part? "abc" nil)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (let [n (str n)
        x (str x)]
       (clojure.string/includes? n x)))

(defn if-contains-part
  ; @description
  ; Returns the given 'n' string if it contains the given 'x' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ;
  ; @usage
  ; (if-contains-part "abc" "ab")
  ; =>
  ; "abc"
  ;
  ; @usage
  ; (if-contains-part "abc" "cd")
  ; =>
  ; nil
  ;
  ; @usage
  ; (if-contains-part "abc" "")
  ; =>
  ; "abc"
  ;
  ; @usage
  ; (if-contains-part "abc" nil)
  ; =>
  ; "abc"
  ;
  ; @usage
  ; (if-contains-part [:a] "[:")
  ; =>
  ; "[:a]"
  ;
  ; @return (string)
  [n x]
  (let [n (str n)
        x (str x)]
       (if (-> n (clojure.string/includes? x))
           (-> n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn contains-digit?
  ; @description
  ; Returns TRUE if the given 'n' string contains at least one digit.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (contains-digit? "abc1")
  ; =>
  ; true
  ;
  ; @usage
  ; (contains-digit? "abc")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (let [n (str n)]
       (some? (re-find #"\d" n))))

(defn contains-lowercase-letter?
  ; @description
  ; Returns TRUE if the given 'n' string contains at least one lowercase letter.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (contains-lowercase-letter? "abc")
  ; =>
  ; true
  ;
  ; @usage
  ; (contains-lowercase-letter? "ABC")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (let [n (str n)]
       (not= n (clojure.string/upper-case n))))

(defn contains-uppercase-letter?
  ; @description
  ; Returns TRUE if the given 'n' string contains at least one uppercase letter.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (contains-uppercase-letter? "ABC")
  ; =>
  ; true
  ;
  ; @usage
  ; (contains-uppercase-letter? "abc")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (let [n (str n)]
       (not= n (clojure.string/lower-case n))))

(defn contains-special-character?
  ; @important
  ; This function is incomplete and may not behave as expected.
  ;
  ; @description
  ; Returns TRUE if the given 'n' string contains at least one special character.
  ;
  ; @param (string) n
  ; @param (?) special-characters
  ; Default: ?
  ;
  ; @usage
  ; (contains-special-character? "?")
  ; =>
  ; true
  ;
  ; @usage
  ; (contains-special-character? "?")
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n]
   (contains-special-character? n "..."))

  ([n special-characters]
   (let [n (str n)])))
