
(ns fruits.regex.remove
    (:require [clojure.string]
              [fruits.regex.match :as match]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-first-match
  ; @bug (fruits.regex.dex#9081)
  ; Lookaround assertions can cause incorrect result!
  ;
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Removes the first match of the given 'x' pattern from the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (remove-first-match "abc123def" #"\d")
  ; =>
  ; "abc23def"
  ;
  ; @usage
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
  ; @bug (fruits.regex.dex#9081)
  ; Lookaround assertions can cause incorrect result!
  ;
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Removes the last match of the given 'x' pattern from the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (remove-last-match "abc123def" #"\d")
  ; =>
  ; "abc12def"
  ;
  ; @usage
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
