
(ns fruits.regex.cut
    (:require [clojure.string]
              [fruits.math.api    :as math]
              [fruits.regex.match :as match]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn before-first-match
  ; @important
  ; Do not use capturing groups in the given pattern. Otherwise, it generates multiple matches!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (before-first-match "abc123def" #"\d")
  ;
  ; @example
  ; (before-first-match "abc123def" #"\d")
  ; =>
  ; "abc"
  ;
  ; @example
  ; (before-first-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (before-first-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (before-first-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (before-last-match "abc123def" #"\d")
  ;
  ; @example
  ; (before-last-match "abc123def" #"\d")
  ; =>
  ; "abc12"
  ;
  ; @example
  ; (before-last-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (before-last-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (before-last-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (after-first-match "abc123def" #"\d")
  ;
  ; @example
  ; (after-first-match "abc123def" #"\d")
  ; =>
  ; "23def"
  ;
  ; @example
  ; (after-first-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (after-first-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (after-first-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (after-last-match "abc123def" #"\d")
  ;
  ; @example
  ; (after-last-match "abc123def" #"\d")
  ; =>
  ; "def"
  ;
  ; @example
  ; (after-last-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (after-last-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (after-last-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (from-first-match "abc123def" #"\d")
  ;
  ; @example
  ; (from-first-match "abc123def" #"\d")
  ; =>
  ; "123def"
  ;
  ; @example
  ; (from-first-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (from-first-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (from-first-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (from-last-match "abc123def" #"\d")
  ;
  ; @example
  ; (from-last-match "abc123def" #"\d")
  ; =>
  ; "def"
  ;
  ; @example
  ; (from-last-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (from-last-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (from-last-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (to-first-match "abc123def" #"\d")
  ;
  ; @example
  ; (to-first-match "abc123def" #"\d")
  ; =>
  ; "abc1"
  ;
  ; @example
  ; (to-first-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-first-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (to-first-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (to-last-match "abc123def" #"\d")
  ;
  ; @example
  ; (to-last-match "abc123def" #"\d")
  ; =>
  ; "abc123"
  ;
  ; @example
  ; (to-last-match "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-last-match "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (to-last-match nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
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
