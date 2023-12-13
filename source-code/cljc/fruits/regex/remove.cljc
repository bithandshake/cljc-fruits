
(ns fruits.regex.remove
    (:require [clojure.string]
              [fruits.regex.match :as match]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-first-match
  ; @important
  ; Do not use capturing groups in the given pattern, otherwise it generates multiple matches!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (remove-first-match "abc123def" #"\d")
  ;
  ; @example
  ; (remove-first-match "abc123def" #"\d")
  ; =>
  ; "abc23def"
  ;
  ; @example
  ; (remove-first-match "abcdef" #"\d")
  ; =>
  ; "abcdef"
  ;
  ; @return (string)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (if-let [first-match (match/re-first n x)]
               (let [dex (clojure.string/index-of n first-match)]
                    (str (subs n 0 dex)
                         (subs n (+ dex (count first-match)))))
               (-> n))))

(defn remove-last-match
  ; @important
  ; Do not use capturing groups in the given pattern, otherwise it generates multiple matches!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (remove-last-match "abc123def" #"\d")
  ;
  ; @example
  ; (remove-last-match "abc123def" #"\d")
  ; =>
  ; "abc12def"
  ;
  ; @example
  ; (remove-last-match "abcdef" #"\d")
  ; =>
  ; "abcdef"
  ;
  ; @return (string)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (if-let [last-match (match/re-last n x)]
               (let [dex (clojure.string/last-index-of n last-match)]
                    (str (subs n 0 dex)
                         (subs n (+ dex (count last-match)))))
               (-> n))))
