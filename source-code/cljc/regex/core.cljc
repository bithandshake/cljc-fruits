
(ns regex.core
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn re-match?
  ; @param (*) n
  ; @param (regex pattern) pattern
  ;
  ; @example
  ; (re-match? "123" #"^[\d]{1,}$")
  ; =>
  ; true
  ;
  ; @example
  ; (re-match? "abc" #"^[\d]{1,}$")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n pattern]
  ; Returns the match, if any, of string to pattern ...
  (let [n (str n)]
       (some? (re-matches pattern n))))

(defn re-mismatch?
  ; @param (*) n
  ; @param (regex pattern) pattern
  ;
  ; @example
  ; (re-mismatch? "123" #"^[\d]{1,}$")
  ; =>
  ; false
  ;
  ; @example
  ; (re-mismatch? "abc" #"^[\d]{1,}$")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n pattern]
  ; Returns the match, if any, of string to pattern ...
  (let [n (str n)]
       (nil? (re-matches pattern n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ends-with?
  ; @param (*) n
  ; @param (regex) x
  ;
  ; @usage
  ; (ends-with? "The things you used to own, now they own you." #"\.")
  ;
  ; @example
  ; (ends-with? "The things you used to own, now they own you." #"\.")
  ; =>
  ; true
  ;
  ; @example
  ; (ends-with? "The things you used to own, now they own you." #"!")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x])
  ; TODO

(defn not-ends-with?
  ; @param (*) n
  ; @param (regex) x
  ;
  ; @usage
  ; (not-ends-with? "The things you used to own, now they own you." #"\.")
  ;
  ; @example
  ; (not-ends-with? "The things you used to own, now they own you." #"!")
  ; =>
  ; true
  ;
  ; @example
  ; (not-ends-with? "The things you used to own, now they own you." #"\.")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [ends-with? (ends-with? n x)]
       (not ends-with?)))

(defn not-ends-with!
  ; @param (*) n
  ; @param (regex) x
  ;
  ; @usage
  ; (not-ends-with! "The things you used to own, now they own you." #"\.")
  ;
  ; @example
  ; (not-ends-with! "The things you used to own, now they own you" #"\.")
  ; =>
  ; "The things you used to own, now they own you"
  ;
  ; @example
  ; (not-ends-with! "The things you used to own, now they own you." #"\.")
  ; =>
  ; "The things you used to own, now they own you"
  ;
  ; @return (string)
  [n x])
  ; TODO

(defn starts-with?
  ; @param (*) n
  ; @param (regex) x
  ;
  ; @usage
  ; (starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;              #"[a-z]")
  ;
  ; @example
  ; (starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;              #"[a-z]")
  ; =>
  ; true
  ;
  ; @example
  ; (starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;              #"[\d]")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (str n)]
       (if-let [match (re-find x n)]
               (clojure.string/starts-with? n match))))

(defn not-starts-with?
  ; @param (*) n
  ; @param (regex) x
  ;
  ; @usage
  ; (not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;                  #"[\d]")
  ;
  ; @example
  ; (not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;                   "[\d]")
  ; =>
  ; true
  ;
  ; @example
  ; (not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;                   "[a-z]")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [starts-with? (starts-with? n x)]
       (not starts-with?)))

(defn not-starts-with!
  ; @param (*) n
  ; @param (regex) x
  ;
  ; @usage
  ; (not-starts-with! "On a long enough time line, the survival rate for everyone drops to zero."
  ;                  #"[a-z]")
  ;
  ; @example
  ; (not-starts-with! " long enough time line, the survival rate for everyone drops to zero."
  ;                  #"[a-z]")
  ; =>
  ; "n a long enough time line, the survival rate for everyone drops to zero."
  ;
  ; @example
  ; (not-starts-with! " long enough time line, the survival rate for everyone drops to zero."
  ;                  #"[/d]")
  ; =>
  ; "On a long enough time line, the survival rate for everyone drops to zero."
  ;
  ; @return (string)
  [n x])
  ; TODO
