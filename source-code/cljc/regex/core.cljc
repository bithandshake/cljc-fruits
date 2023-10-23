
(ns regex.core
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn re-first
  ; @description
  ; Returns the first match.
  ;
  ; @param (*) n
  ; @param (regex pattern) pattern
  ;
  ; @usage
  ; (re-first "123" #"\d")
  ;
  ; @example
  ; (re-first "123" #"\d")
  ; =>
  ; "1"
  ;
  ; @example
  ; (re-first "abc" #"\d")
  ; =>
  ; nil
  ;
  ; @return (map, string or vector)
  [n pattern]
  ; The re-seq function returns a ...
  ; ... sequence of strings if the pattern has no capturing groups.
  ; ... sequence of vectors if the pattern has one or more capturing groups.
  ; ... sequence of maps if the pattern has one ore more named capturing groups.
  (first (re-seq pattern (str n))))

(defn re-last
  ; @description
  ; Returns the last match.
  ;
  ; @param (*) n
  ; @param (regex pattern) pattern
  ;
  ; @usage
  ; (re-last "123" #"\d")
  ;
  ; @example
  ; (re-last "123" #"\d")
  ; =>
  ; "3"
  ;
  ; @example
  ; (re-last "abc" #"\d")
  ; =>
  ; nil
  ;
  ; @return (map, string or vector)
  [n pattern]
  ; The re-seq function returns a ...
  ; ... sequence of strings if the pattern has no capturing groups.
  ; ... sequence of vectors if the pattern has one or more capturing groups.
  ; ... sequence of maps if the pattern has one ore more named capturing groups.
  (last (re-seq pattern (str n))))

(defn re-match
  ; @description
  ; Returns the given 'n' string if any matches found.
  ;
  ; @param (*) n
  ; @param (regex pattern) pattern
  ;
  ; @usage
  ; (re-match "123" #"\d{1,}")
  ;
  ; @example
  ; (re-match "123" #"^[\d]{1,}$")
  ; =>
  ; "123"
  ;
  ; @example
  ; (re-match "abc" #"^[\d]{1,}$")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n pattern]
  (let [n (str n)]
       (if (re-find pattern n)
           (-> n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ends-with?
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple
  ; matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern) x
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
  [n x]
  (let [n (str n)]
       (if-let [last-match (re-last n x)]
               (clojure.string/ends-with? n last-match))))

(defn not-ends-with?
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple
  ; matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern) x
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
  (not (ends-with? n x)))

(defn not-ends-with!
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple
  ; matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern) x
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
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple
  ; matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern) x
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
       (if-let [first-match (re-first n x)]
               (clojure.string/starts-with? n first-match))))

(defn not-starts-with?
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple
  ; matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern) x
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
  (not (starts-with? n x)))

(defn not-starts-with!
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple
  ; matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern) x
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
