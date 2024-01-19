
(ns fruits.regex.match
    (:require [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn re-count
  ; @description
  ; Returns the match count of the given 'x' pattern in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-count "123" #"\d")
  ; =>
  ; 3
  ;
  ; @usage
  ; (re-count "abc" #"\d")
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-seq x) count)))

(defn re-return
  ; @description
  ; Returns the given 'n' string if any match of the given 'x' pattern is found within it.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-return "123" #"^[\d]+$")
  ; =>
  ; "123"
  ;
  ; @usage
  ; (re-return "abc" #"^[\d]+$")
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

(defn re-first
  ; @description
  ; Returns the first match of the given 'x' pattern in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-first "123" #"\d")
  ; =>
  ; "1"
  ;
  ; @usage
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
  ; Returns the last match of the given 'x' pattern in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-last "123" #"\d")
  ; =>
  ; "3"
  ;
  ; @usage
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

(defn re-all
  ; @description
  ; Returns all matches of the given 'x' pattern in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-all "123" #"\d")
  ; =>
  ; ["1" "2" "3"]
  ;
  ; @usage
  ; (re-all "abc" #"\d")
  ; =>
  ; []
  ;
  ; @return (vector)
  [n x]
  ; The 're-seq' function returns a ...
  ; ... sequence of strings if the pattern has no capturing groups.
  ; ... sequence of vectors if the pattern has one or more capturing groups.
  ; ... sequence of maps if the pattern has one ore more named capturing groups.
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-seq x) vec)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn re-match?
  ; @description
  ; Returns TRUE if any match found of the given 'x' pattern in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-match? "123" #"^[\d]+$")
  ; =>
  ; true
  ;
  ; @usage
  ; (re-match? "abc" #"^[\d]+$")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-find x) some?)))

(defn re-mismatch?
  ; @description
  ; Returns TRUE if no match found of the given 'x' pattern in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-mismatch? "abc" #"^[\d]+$")
  ; =>
  ; true
  ;
  ; @usage
  ; (re-mismatch? "123" #"^[\d]+$")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-find x) nil?)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn re-from
  ; @bug (source-code/cljc/fruits/regex/dex.cljc#9081)
  ; Lookaround assertions can cause incorrect result!
  ;
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns the longest match of the given 'x' pattern that starts at the given 'cursor' position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (integer) cursor
  ;
  ; @usage
  ; (re-from "abc123" #"[/d]" 3)
  ; =>
  ; "1"
  ;
  ; @usage
  ; (re-from "abc123" #"[/d]" 4)
  ; =>
  ; "2"
  ;
  ; @usage
  ; (re-from "abc123" #"[/d]" 2)
  ; =>
  ; nil
  ;
  ; @usage
  ; (re-from "abc123" #"(?<=c)[\d]" 3)
  ; =>
  ; "123"
  ;
  ; @return (string)
  [n x cursor]
  (let [n       (str n)
        x       (re-pattern x)
        cursor  (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})
        matches (re-seq x n)]
       (letfn [(f0 [result match]
                   (or (and (seqable/cursor-in-bounds? n (+ cursor (count match)))
                            (=  match (subs n cursor (+ cursor (count match))))
                            (-> match count (> (count result)))
                            (-> match))
                       (-> result)))]
              (reduce f0 nil matches))))

(defn re-to
  ; @bug (source-code/cljc/fruits/regex/dex.cljc#9081)
  ; Lookaround assertions can cause incorrect result!
  ;
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns the longest match of the given 'x' pattern that ends at the given 'cursor' position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (integer) cursor
  ;
  ; @usage
  ; (re-to "abc123" #"[a-z]" 3)
  ; =>
  ; "c"
  ;
  ; @usage
  ; (re-to "abc123" #"[a-z]" 2)
  ; =>
  ; "b"
  ;
  ; @usage
  ; (re-to "abc123" #"[a-z]" 4)
  ; =>
  ; nil
  ;
  ; @usage
  ; (re-to "abc123" #"abc(?=\d)" 3)
  ; =>
  ; "abc"
  ;
  ; @return (string)
  [n x cursor]
  (let [n       (str n)
        x       (re-pattern x)
        cursor  (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})
        matches (re-seq x n)]
       (letfn [(f0 [result match]
                   (or (and (seqable/cursor-in-bounds? n (- cursor (count match)))
                            (=  match (subs n (- cursor (count match)) cursor))
                            (-> match count (> (count result)))
                            (-> match))
                       (-> result)))]
              (reduce f0 nil matches))))
