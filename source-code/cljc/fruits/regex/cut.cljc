
(ns fruits.regex.cut
    (:require [clojure.string]
              [fruits.regex.match :as match]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn before-first-match
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns the part of the given 'n' string that ends where the first match of the given 'x' pattern starts (if any).
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' string in case of no match of the given 'x' pattern is found.
  ;   Default: false}
  ;
  ; @usage
  ; (before-first-match "abc123def" #"\d")
  ; =>
  ; "abc"
  ;
  ; @usage
  ; (before-first-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (before-first-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @usage
  ; (before-first-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (before-first-match nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (before-first-match n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [first-match (match/re-first n x)]
                (subs n 0 (clojure.string/index-of n first-match))
                (if return? n)))))

(defn before-last-match
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns the part of the given 'n' string that ends where the last match of the given 'x' pattern starts (if any).
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' string in case of no match of the given 'x' pattern is found.
  ;   Default: false}
  ;
  ; @usage
  ; (before-last-match "abc123def" #"\d")
  ; =>
  ; "abc12"
  ;
  ; @usage
  ; (before-last-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (before-last-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @usage
  ; (before-last-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (before-last-match nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (before-last-match n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [last-match (match/re-last n x)]
                (subs n 0 (clojure.string/last-index-of n last-match))
                (if return? n)))))

(defn after-first-match
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns the part of the given 'n' string that starts where the first match of the given 'x' pattern ends (if any).
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' string in case of no match of the given 'x' pattern is found.
  ;   Default: false}
  ;
  ; @usage
  ; (after-first-match "abc123def" #"\d")
  ; =>
  ; "23def"
  ;
  ; @usage
  ; (after-first-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (after-first-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @usage
  ; (after-first-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (after-first-match nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (after-first-match n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [first-match (match/re-first n x)]
                (subs n (+ (clojure.string/index-of n first-match)
                           (count                     first-match)))
                (if return? n)))))

(defn after-last-match
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns the part of the given 'n' string that starts where the last match of the given 'x' pattern ends (if any).
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' string in case of no match of the given 'x' pattern is found.
  ;   Default: false}
  ;
  ; @usage
  ; (after-last-match "abc123def" #"\d")
  ; =>
  ; "def"
  ;
  ; @usage
  ; (after-last-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (after-last-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @usage
  ; (after-last-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (after-last-match nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (after-last-match n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [last-match (match/re-last n x)]
                (subs n (+ (clojure.string/last-index-of n last-match)
                           (count last-match)))
                (if return? n)))))

(defn from-first-match
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns the part of the given 'n' string that starts where the first match of the given 'x' pattern starts (if any).
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' string in case of no match of the given 'x' pattern is found.
  ;   Default: false}
  ;
  ; @usage
  ; (from-first-match "abc123def" #"\d")
  ; =>
  ; "123def"
  ;
  ; @usage
  ; (from-first-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (from-first-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @usage
  ; (from-first-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (from-first-match nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (from-first-match n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [first-match (match/re-first n x)]
                (subs n (clojure.string/index-of n first-match))
                (if return? n)))))

(defn from-last-match
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns the part of the given 'n' string that starts where the last match of the given 'x' pattern starts (if any).
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' string in case of no match of the given 'x' pattern is found.
  ;   Default: false}
  ;
  ; @usage
  ; (from-last-match "abc123def" #"\d")
  ; =>
  ; "def"
  ;
  ; @usage
  ; (from-last-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (from-last-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @usage
  ; (from-last-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (from-last-match nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (from-last-match n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [last-match (match/re-last n x)]
                (subs n (clojure.string/last-index-of n last-match))
                (if return? n)))))

(defn to-first-match
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns the part of the given 'n' string that ends where the first match of the given 'x' pattern ends (if any).
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' string in case of no match of the given 'x' pattern is found.
  ;   Default: false}
  ;
  ; @usage
  ; (to-first-match "abc123def" #"\d")
  ; =>
  ; "abc1"
  ;
  ; @usage
  ; (to-first-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-first-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @usage
  ; (to-first-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-first-match nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (to-first-match n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [first-match (match/re-first n x)]
                (subs n 0 (+ (clojure.string/index-of n first-match)
                             (count                     first-match)))
                (if return? n)))))

(defn to-last-match
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @description
  ; Returns the part of the given 'n' string that ends where the last match of the given 'x' pattern ends (if any).
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' string in case of no match of the given 'x' pattern is found.
  ;   Default: false}
  ;
  ; @usage
  ; (to-last-match "abc123def" #"\d")
  ; =>
  ; "abc123"
  ;
  ; @usage
  ; (to-last-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-last-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @usage
  ; (to-last-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-last-match nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (to-last-match n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [last-match (match/re-last n x)]
                (subs n 0 (+ (clojure.string/last-index-of n last-match)
                             (count last-match)))
                (if return? n)))))
