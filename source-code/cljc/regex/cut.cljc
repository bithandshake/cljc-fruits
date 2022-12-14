
(ns regex.cut
    (:require [clojure.string]
              [candy.api :refer [return]]
              [math.api  :as math]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn before-first-occurence
  ; @param (*) n
  ; @param (regex pattern) x
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
   (let [n (str n)]
        (if-let [match (re-find x n)]
                (let [dex (clojure.string/index-of n match)]
                     (subs n 0 dex))
                (if return? n)))))

(defn before-last-occurence
  ; @param (*) n
  ; @param (regex pattern) x
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

  ([n x {:keys [return?]}]))
   ; TODO

(defn after-first-occurence
  ; @param (*) n
  ; @param (regex pattern) x
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
   (let [n (str n)]
        (if-let [match (re-find x n)]
                (let [dex (clojure.string/index-of n match)]
                     (subs n (+ dex (count match))))
                (if return? n)))))

(defn after-last-occurence
  ; @param (*) n
  ; @param (regex pattern) x
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

  ([n x {:keys [return?]}]))
   ; TODO

(defn from-first-occurence
  ; @param (*) n
  ; @param (regex pattern) x
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
   (let [n (str n)]
        (if-let [match (re-find x n)]
                (let [dex (clojure.string/index-of n match)]
                     (subs n dex))
                (if return? n)))))

(defn from-last-occurence
  ; @param (*) n
  ; @param (regex pattern) x
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

  ([n x {:keys [return?]}]))
   ; TODO

(defn to-first-occurence
  ; @param (*) n
  ; @param (regex pattern) x
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
   (let [n (str n)]
        (if-let [match (re-find x n)]
                (let [dex (clojure.string/index-of n match)]
                     (subs n 0 (+ dex (count match))))
                (if return? n)))))

(defn to-last-occurence
  ; @param (*) n
  ; @param (regex pattern) x
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

  ([n x {:keys [return?]}]))
   ; TODO

(defn remove-first-occurence
  ; @param (*) n
  ; @param (regex pattern) x
  ;
  ; @usage
  ; (remove-first-occurence "abc123def" #"\d")
  ;
  ; @example
  ; (remove-first-occurence "abc123def" #"\d")
  ; =>
  ; "abc23def"
  ;
  ; @example
  ; (remove-first-occurence "abcdef" #"\d")
  ; =>
  ; "abcdef"
  ;
  ; @return (string)
  [n x]
  (let [n (str n)]
       (if-let [match (re-find x n)]
               (let [dex (clojure.string/index-of n match)]
                    (str (subs n 0 dex)
                         (subs n (+ dex (count match)))))
               (return n))))

(defn remove-last-occurence
  ; @param (*) n
  ; @param (*) x
  ;
  ; @usage
  ; (remove-last-occurence "abc123def" #"\d")
  ;
  ; @example
  ; (remove-last-occurence "abc123def" #"\d")
  ; =>
  ; "abc12def"
  ;
  ; @example
  ; (remove-last-occurence "abcdef" #"\d")
  ; =>
  ; "abcdef"
  ;
  ; @return (string)
  [n x]
  (let [n (str n)]
       (if-let [match (re-find x n)]
               (let [dex (clojure.string/index-of n match)]
                    (str (subs n 0 dex)
                         (subs n (+ dex (count match)))))
               (return n))))

(defn between-occurences
  ; @param (*) n
  ; @param (regex pattern) x
  ; @param (regex pattern) y
  ;
  ; @usage
  ; (between-occurences "abc123def" #"a" #"f")
  ;
  ; @example
  ; (between-occurences "abc123def" #"a" #"f")
  ; =>
  ; "bc123de"
  ;
  ; @example
  ; (between-occurences "abc123def" #"a" #"x")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n x y]
  (-> n (after-first-occurence x {:return? false})
        (before-last-occurence y {:return? false})))
