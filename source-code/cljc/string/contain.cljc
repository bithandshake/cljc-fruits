
(ns string.contain
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn contains-part?
  ; @description
  ; Returns TRUE if the given 'n' value (converted to string) contains the given 'x' value (converted to string).
  ;
  ; @param (*) n
  ; @param (*) x
  ;
  ; @usage
  ; (contains-part? "abc" "ab")
  ;
  ; @example
  ; (contains-part? "abc" "ab")
  ; =>
  ; true
  ;
  ; @example
  ; (contains-part? "abc" "cd")
  ; =>
  ; false
  ;
  ; @example
  ; (contains-part? "abc" "")
  ; =>
  ; true
  ;
  ; @example
  ; (contains-part? "abc" nil)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (let [n (str n)
        x (str x)]
       (clojure.string/includes? n x)))

(defn contains-digit?
  ; @description
  ; Returns TRUE if the given 'n' value (converted to string) contains at least one digit.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (contains-digit? "abc1")
  ;
  ; @example
  ; (contains-digit? "abc1")
  ; =>
  ; true
  ;
  ; @example
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
  ; Returns TRUE if the given 'n' value (converted to string) contains at least one lowercase letter.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (contains-lowercase-letter? "abc")
  ;
  ; @example
  ; (contains-lowercase-letter? "abc")
  ; =>
  ; true
  ;
  ; @example
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
  ; Returns TRUE if the given 'n' value (converted to string) contains at least one uppercase letter.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (contains-uppercase-letter? "ABC")
  ;
  ; @example
  ; (contains-uppercase-letter? "ABC")
  ; =>
  ; true
  ;
  ; @example
  ; (contains-uppercase-letter? "abc")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (let [n (str n)]
       (not= n (clojure.string/lower-case n))))

(defn if-contains-part
  ; @description
  ; Returns the given 'n' value (converted to string) if it contains the given 'x' value (converted to string).
  ;
  ; @param (*) n
  ; @param (*) x
  ;
  ; @usage
  ; (if-contains-part "abc" "ab")
  ;
  ; @example
  ; (if-contains-part "abc" "ab")
  ; =>
  ; "abc"
  ;
  ; @example
  ; (if-contains-part "abc" "cd")
  ; =>
  ; nil
  ;
  ; @example
  ; (if-contains-part "abc" "")
  ; =>
  ; "abc"
  ;
  ; @example
  ; (if-contains-part "abc" nil)
  ; =>
  ; "abc"
  ;
  ; @example
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
