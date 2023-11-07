
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

(defn abc?
  ; @description
  ; Returns TRUE if the given 'a' and 'b' values (converted to string) are in alphabetical order.
  ;
  ; @param (*) a
  ; @param (*) b
  ;
  ; @usage
  ; (abc? "abc" "def")
  ;
  ; @example
  ; (abc? "abc" "def")
  ; =>
  ; true
  ;
  ; @example
  ; (abc? "abc" "abc")
  ; =>
  ; true
  ;
  ; @example
  ; (abc? 10 12)
  ; =>
  ; true
  ;
  ; @example
  ; (abc? "" "abc")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [a b]
  (let [a (str a)
        b (str b)]
       (>= 0 (compare a b))))

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

(defn lowercase?
  ; @description
  ; Returns TRUE if the given 'n' value (converted to string) is lowercase.
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
  ; Returns TRUE if the given 'n' value (converted to string) is uppercase.
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
  ; Returns the given 'n' value (converted to string) if it contains the given 'x' part.
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
