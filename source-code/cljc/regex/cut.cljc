
(ns regex.cut
    (:require [clojure.string]
              [math.api   :as math]
              [regex.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn before-first-occurence
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (before-first-occurence "abc123def" #"\d")
  ;
  ; @example
  ; (before-first-occurence "abc123def" #"\d")
  ; =>
  ; "abc"
  ;
  ; @example
  ; (before-first-occurence "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (before-first-occurence "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (before-first-occurence nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (before-first-occurence nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (before-first-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [first-match (core/re-first n x)]
                (subs n 0 (clojure.string/index-of n first-match))
                (if return? n)))))

(defn before-last-occurence
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (before-last-occurence "abc123def" #"\d")
  ;
  ; @example
  ; (before-last-occurence "abc123def" #"\d")
  ; =>
  ; "abc12"
  ;
  ; @example
  ; (before-last-occurence "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (before-last-occurence "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (before-last-occurence nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (before-last-occurence nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (before-last-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [last-match (core/re-last n x)]
                (subs n 0 (clojure.string/last-index-of n last-match))
                (if return? n)))))

(defn after-first-occurence
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (after-first-occurence "abc123def" #"\d")
  ;
  ; @example
  ; (after-first-occurence "abc123def" #"\d")
  ; =>
  ; "23def"
  ;
  ; @example
  ; (after-first-occurence "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (after-first-occurence "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (after-first-occurence nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (after-first-occurence nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (after-first-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [first-match (core/re-first n x)]
                (subs n (+ (clojure.string/index-of n first-match)
                           (count                     first-match)))
                (if return? n)))))

(defn after-last-occurence
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (after-last-occurence "abc123def" #"\d")
  ;
  ; @example
  ; (after-last-occurence "abc123def" #"\d")
  ; =>
  ; "def"
  ;
  ; @example
  ; (after-last-occurence "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (after-last-occurence "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (after-last-occurence nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (after-last-occurence nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (after-last-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [last-match (core/re-last n x)]
                (subs n (+ (clojure.string/last-index-of n last-match)
                           (count last-match)))
                (if return? n)))))

(defn from-first-occurence
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (from-first-occurence "abc123def" #"\d")
  ;
  ; @example
  ; (from-first-occurence "abc123def" #"\d")
  ; =>
  ; "123def"
  ;
  ; @example
  ; (from-first-occurence "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (from-first-occurence "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (from-first-occurence nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (from-first-occurence nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (from-first-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [first-match (core/re-first n x)]
                (subs n (clojure.string/index-of n first-match))
                (if return? n)))))

(defn from-last-occurence
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (from-last-occurence "abc123def" #"\d")
  ;
  ; @example
  ; (from-last-occurence "abc123def" #"\d")
  ; =>
  ; "def"
  ;
  ; @example
  ; (from-last-occurence "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (from-last-occurence "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (from-last-occurence nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (from-last-occurence nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (from-last-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [last-match (core/re-last n x)]
                (subs n (clojure.string/last-index-of n last-match))
                (if return? n)))))

(defn to-first-occurence
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (to-first-occurence "abc123def" #"\d")
  ;
  ; @example
  ; (to-first-occurence "abc123def" #"\d")
  ; =>
  ; "abc1"
  ;
  ; @example
  ; (to-first-occurence "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-first-occurence "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (to-first-occurence nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-first-occurence nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (to-first-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [first-match (core/re-first n x)]
                (subs n 0 (+ (clojure.string/index-of n first-match)
                             (count                     first-match)))
                (if return? n)))))

(defn to-last-occurence
  ; @warning
  ; Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (to-last-occurence "abc123def" #"\d")
  ;
  ; @example
  ; (to-last-occurence "abc123def" #"\d")
  ; =>
  ; "abc123"
  ;
  ; @example
  ; (to-last-occurence "abcdef" #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-last-occurence "abcdef" #"\d" {:return? true})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (to-last-occurence nil #"\d")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-last-occurence nil "\d" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (to-last-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (re-pattern x)]
        (if-let [last-match (core/re-last n x)]
                (subs n 0 (+ (clojure.string/last-index-of n last-match)
                             (count last-match)))
                (if return? n)))))
