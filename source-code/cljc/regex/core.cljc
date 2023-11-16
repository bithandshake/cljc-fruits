
(ns regex.core
    (:require [clojure.string]
              [seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn re-count
  ; @description
  ; Returns the match count.
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-count "123" #"\d")
  ;
  ; @example
  ; (re-count "123" #"\d")
  ; =>
  ; 3
  ;
  ; @example
  ; (re-count "abc" #"\d")
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-seq x) count)))

(defn re-first
  ; @description
  ; Returns the first match.
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
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
  [n x]
  ; The 're-seq' function returns a ...
  ; ... sequence of strings if the pattern has no capturing groups.
  ; ... sequence of vectors if the pattern has one or more capturing groups.
  ; ... sequence of maps if the pattern has one ore more named capturing groups.
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-seq x) first)))

(defn re-last
  ; @description
  ; Returns the last match.
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
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
  [n x]
  ; The 're-seq' function returns a ...
  ; ... sequence of strings if the pattern has no capturing groups.
  ; ... sequence of vectors if the pattern has one or more capturing groups.
  ; ... sequence of maps if the pattern has one ore more named capturing groups.
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-seq x) last)))

(defn re-match
  ; @description
  ; Returns the given 'n' string if any match is found.
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
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
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (if (->> n (re-find x))
           (->  n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn starts-with?
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (starts-with? "abcdef" #"[a-z]")
  ;
  ; @example
  ; (starts-with? "abcdef" #"[a-z]")
  ; =>
  ; true
  ;
  ; @example
  ; (starts-with? "abcdef" #"[\d]")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  ; '^' asserts that the pattern following it must appear at the start of the string
  ;     (if placed at the beginning of a regular expression, outside of a character class).
  (let [n (str n)
        x (str "^" x)
        x (re-pattern x)]
       (->> n (re-find x) some?)))

(defn ends-with?
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (ends-with? "abcdef" #"[a-z]")
  ;
  ; @example
  ; (ends-with? "abcdef" #"[a-z]")
  ; =>
  ; true
  ;
  ; @example
  ; (ends-with? "abcdef" #"[\d]")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  ; '(?![\n\r])'  asserts that something is not followed by a newline or return character
  ; '$'           represents the end of a LINE or STRING
  ; '(?![\n\r])$' represents the end of the STRING (A)
  ; '$(?![\n\r])' represents the end of the STRING (B)
  (let [n (str n)
        x (str x "(?![\n\r])$")
        x (re-pattern x)]
       (->> n (re-find x) some?)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not-starts-with?
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (not-starts-with? "abcdef" #"[\d]")
  ;
  ; @example
  ; (not-starts-with? "abcdef" "[\d]")
  ; =>
  ; true
  ;
  ; @example
  ; (not-starts-with? "abcdef" "[a-z]")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [starts-with? (starts-with? n x)]
       (not starts-with?)))

(defn not-ends-with?
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (not-ends-with? "abcdef" #"[\d]")
  ;
  ; @example
  ; (not-ends-with? "abcdef" #"[\d]")
  ; =>
  ; true
  ;
  ; @example
  ; (not-ends-with? "abcdef" #"[a-z]")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [ends-with? (ends-with? n x)]
       (not ends-with?)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not-starts-with!
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (not-starts-with! "abcdef" #"[a-z]")
  ;
  ; @example
  ; (not-starts-with! "abcdef" #"[a-z]")
  ; =>
  ; "bcdef"
  ;
  ; @example
  ; (not-starts-with! "abcdef" #"[/d]")
  ; =>
  ; "abcdef"
  ;
  ; @return (string)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (or (if-let [first-match (re-first n x)]
                   (if (clojure.string/starts-with? n first-match)
                       (subs n (-> first-match count))))
           (-> n))))

(defn not-ends-with!
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (not-ends-with! "abcdef" #"[a-z]")
  ;
  ; @example
  ; (not-ends-with! "abcdef" #"[a-z]")
  ; =>
  ; "abcde"
  ;
  ; @example
  ; (not-ends-with! "abcdef" #"[\d]")
  ; =>
  ; "abcdef"
  ;
  ; @return (string)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (or (if-let [last-match (re-last n x)]
                   (if (clojure.string/ends-with? n last-match)
                       (subs n 0 (- (-> n          count)
                                    (-> last-match count)))))
           (-> n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn starts-at?
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (integer) cursor
  ;
  ; @usage
  ; (starts-at? "abc123" #"[/d]" 3)
  ;
  ; @example
  ; (starts-at? "abc123" #"[/d]" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (starts-at? "abc123" #"[/d]" 4)
  ; =>
  ; true
  ;
  ; @example
  ; (starts-at? "abc123" #"[/d]" 2)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x cursor]
  (let [n      (str n)
        x      (re-pattern x)
        cursor (seqable/normalize-cursor n cursor)]
       (starts-with? (subs n cursor) x)))

(defn ends-at?
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (integer) cursor
  ;
  ; @usage
  ; (ends-at? "abc123" #"[/d]" 6)
  ;
  ; @example
  ; (ends-at? "abc123" #"[/d]" 6)
  ; =>
  ; true
  ;
  ; @example
  ; (ends-at? "abc123" #"[/d]" 5)
  ; =>
  ; true
  ;
  ; @example
  ; (ends-at? "abc123" #"[/d]" 2)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x cursor]
  (let [n      (str n)
        x      (re-pattern x)
        cursor (seqable/normalize-cursor n cursor)]
       (ends-with? (subs n 0 cursor) x)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not-starts-at?
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (integer) cursor
  ;
  ; @usage
  ; (not-starts-at? "abc123" #"[/d]" 2)
  ;
  ; @example
  ; (not-starts-at? "abc123" #"[/d]" 2)
  ; =>
  ; true
  ;
  ; @example
  ; (not-starts-at? "abc123" #"[/d]" 1)
  ; =>
  ; true
  ;
  ; @example
  ; (not-starts-at? "abc123" #"[/d]" 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x cursor]
  (let [starts-at? (starts-at? n x cursor)]
       (not starts-at?)))

(defn not-ends-at?
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (integer) cursor
  ;
  ; @usage
  ; (not-ends-at? "abc123" #"[/d]" 2)
  ;
  ; @example
  ; (not-ends-at? "abc123" #"[/d]" 2)
  ; =>
  ; true
  ;
  ; @example
  ; (not-ends-at? "abc123" #"[/d]" 1)
  ; =>
  ; true
  ;
  ; @example
  ; (not-ends-at? "abc123" #"[/d]" 4)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x cursor]
  (let [ends-at? (ends-at? n x cursor)]
       (not ends-at?)))
