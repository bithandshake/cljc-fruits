
(ns fruits.regex.bounds
    (:require [clojure.string]
              [fruits.regex.match :as match]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn starts-with?
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns TRUE if the given 'n' string starts with any match of the given 'x' pattern.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (starts-with? "abcdef" #"[a-z]")
  ; =>
  ; true
  ;
  ; @usage
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
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns TRUE if the given 'n' string ends with any match of the given 'x' pattern.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (ends-with? "abcdef" #"[a-z]")
  ; =>
  ; true
  ;
  ; @usage
  ; (ends-with? "abcdef" #"[\d]")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  ; '(?![\n\r])'  asserts that something is not followed by a newline or a return character
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
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns TRUE if the given 'n' string not starts with any match of the given 'x' pattern.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (not-starts-with? "abcdef" "[\d]")
  ; =>
  ; true
  ;
  ; @usage
  ; (not-starts-with? "abcdef" "[a-z]")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [starts-with? (starts-with? n x)]
       (not starts-with?)))

(defn not-ends-with?
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns TRUE if the given 'n' string not ends with any match of the given 'x' pattern.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (not-ends-with? "abcdef" #"[\d]")
  ; =>
  ; true
  ;
  ; @usage
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
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Ensures that the given 'n' string not starts with any match of the given 'x' pattern.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (not-starts-with! "abcdef" #"[a-z]")
  ; =>
  ; "bcdef"
  ;
  ; @usage
  ; (not-starts-with! "abcdef" #"[/d]")
  ; =>
  ; "abcdef"
  ;
  ; @return (string)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (or (if-let [first-match (match/re-first n x)]
                   (if (clojure.string/starts-with? n first-match)
                       (subs n (-> first-match count))))
           (-> n))))

(defn not-ends-with!
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Ensures that the given 'n' string not ends with any match of the given 'x' pattern.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (not-ends-with! "abcdef" #"[a-z]")
  ; =>
  ; "abcde"
  ;
  ; @usage
  ; (not-ends-with! "abcdef" #"[\d]")
  ; =>
  ; "abcdef"
  ;
  ; @return (string)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (or (if-let [last-match (match/re-last n x)]
                   (if (clojure.string/ends-with? n last-match)
                       (subs n 0 (- (-> n          count)
                                    (-> last-match count)))))
           (-> n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn starts-at?
  ; @bug (fruits.regex.dex#9081)
  ; Lookaround assertions can cause incorrect result!
  ;
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns TRUE if a match of the given 'x' pattern starts at the given 'cursor' position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (integer) cursor
  ;
  ; @usage
  ; (starts-at? "abc123" #"[/d]" 3)
  ; =>
  ; true
  ;
  ; @usage
  ; (starts-at? "abc123" #"[/d]" 4)
  ; =>
  ; true
  ;
  ; @usage
  ; (starts-at? "abc123" #"[/d]" 2)
  ; =>
  ; false
  ;
  ; @usage
  ; (starts-at? "abc123" #"(?<=c)[\d]" 3)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x cursor]
  (-> (match/re-from n x cursor) boolean))

(defn ends-at?
  ; @bug (fruits.regex.dex#9081)
  ; Lookaround assertions can cause incorrect result!
  ;
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns TRUE if a match of the given 'x' pattern ends at the given 'cursor' position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (integer) cursor
  ;
  ; @usage
  ; (ends-at? "abc123" #"[a-z]" 3)
  ; =>
  ; true
  ;
  ; @usage
  ; (ends-at? "abc123" #"[a-z]" 2)
  ; =>
  ; true
  ;
  ; @usage
  ; (ends-at? "abc123" #"[a-z]" 4)
  ; =>
  ; false
  ;
  ; @usage
  ; (ends-at? "abc123" #"abc(?=\d)" 3)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x cursor]
  (-> (match/re-to n x cursor) boolean))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not-starts-at?
  ; @bug (fruits.regex.dex#9081)
  ; Lookaround assertions can cause incorrect result!
  ;
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns TRUE if NO match of the given 'x' pattern starts at the given 'cursor' position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (integer) cursor
  ;
  ; @usage
  ; (not-starts-at? "abc123" #"[/d]" 2)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-starts-at? "abc123" #"[/d]" 1)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-starts-at? "abc123" #"[/d]" 3)
  ; =>
  ; false
  ;
  ; @usage
  ; (not-starts-at? "abc123" #"(?<=c)[\d]" 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x cursor]
  (let [starts-at? (starts-at? n x cursor)]
       (not starts-at?)))

(defn not-ends-at?
  ; @bug (fruits.regex.dex#9081)
  ; Lookaround assertions can cause incorrect result!
  ;
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns TRUE if NO match of the given 'x' pattern ends at the given 'cursor' position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (integer) cursor
  ;
  ; @usage
  ; (not-ends-at? "abc123" #"[a-z]" 5)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-ends-at? "abc123" #"[a-z]" 4)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-ends-at? "abc123" #"[a-z]" 3)
  ; =>
  ; false
  ;
  ; @usage
  ; (not-ends-at? "abc123" #"abc(?=\d)" 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x cursor]
  (let [ends-at? (ends-at? n x cursor)]
       (not ends-at?)))
