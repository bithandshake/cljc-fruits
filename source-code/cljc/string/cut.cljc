
(ns string.cut
    (:require [clojure.string]
              [candy.api :refer [return]]
              [math.api  :as math]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn part
  ; @param (*) n
  ; @param (integer) start
  ; @param (integer)(opt) end
  ;
  ; @usage
  ; (part "abc" 0 2)
  ;
  ; @example
  ; (part "abcdef" 2 4)
  ; =>
  ; "cd"
  ;
  ; @example
  ; (part "abcdef" 4 2)
  ; =>
  ; "cd"
  ;
  ; @example
  ; (part 12345 2 4)
  ; =>
  ; "34"
  ;
  ; @example
  ; (part [:a :b] 0 6)
  ; =>
  ; "[:a, :"
  ;
  ; @return (string)
  ([n start]
   (let [n (str n)]
        (part n start (count n))))

  ([n start end]
   (let [n (str n)]
        (if (and (-> n empty? not)
                 (math/between? end   0 (count n))
                 (math/between? start 0 (count n)))
            (subs   n start end)
            (return n)))))

(defn trim
  ; @param (*) n
  ;
  ; @usage
  ; (trim " abc")
  ;
  ; @example
  ; (trim " abc  ")
  ; =>
  ; "abc"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/trim))

(defn remove-part
  ; @param (*) n
  ; @param (regex or string) x
  ;
  ; @usage
  ; (remove-part "abc" "b")
  ;
  ; @example
  ; (remove-part "abc" "b")
  ; =>
  ; "ac"
  ;
  ; @example
  ; (remove-part "abc abc" "b")
  ; =>
  ; "ac ac"
  ;
  ; @example
  ; (remove-part "abc abc 123" #"\d")
  ; =>
  ; "abc abc"
  ;
  ; @return (string)
  [n x]
  (clojure.string/replace (str n) x ""))

(defn filter-characters
  ; @param (*) n
  ; @param (vector) allowed-characters
  ;
  ; @example
  ; (filter-characters "+3630 / 123 - 4567" ["+" "1" "2" "3" "4" "5" "6" "7" "8" "9" "0"])
  ; =>
  ; "+36301234567"
  ;
  ; @example
  ; (filter-characters [:a :b] [":" "a" "b"])
  ; =>
  ; ":a:b"
  ;
  ; @return (string)
  [n allowed-characters]
  (letfn [(f [o x] (if (some #(= x %) allowed-characters)
                       (str o x) o))]
         (reduce f "" (str n))))

(defn max-length
  ; @param (*) n
  ; @param (integer) limit
  ; @param (*)(opt) suffix
  ;
  ; @usage
  ; (max-length "One Flew Over the Cuckoo's Nest" 5)
  ;
  ; @example
  ; (max-length "One Flew Over the Cuckoo's Nest" 10)
  ; =>
  ; "One Flew O"
  ;
  ; @example
  ; (max-length "One Flew Over the Cuckoo's Nest" 10 " ...")
  ; =>
  ; "One Flew O ..."
  ;
  ; @example
  ; (max-length nil 10)
  ; =>
  ; ""
  ;
  ; @return (string)
  [n limit & [suffix]]
  (let [n (str n)]
       (if (and (-> n empty? not)
                (-> limit integer?)
                (<  limit (count n)))
           (str (subs n 0 limit) suffix)
           (return n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn before-first-occurence
  ; @param (*) n
  ; @param (*) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (before-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                         "never")
  ;
  ; @example
  ; (before-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                         "never")
  ; =>
  ; "With insomnia, you're "
  ;
  ; @example
  ; (before-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                         "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (before-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                         "abc"
  ;                         {:return? true})
  ; =>
  ; "With insomnia, you're never really awake; but you're never really asleep."
  ;
  ; @example
  ; (before-first-occurence nil "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (before-first-occurence nil "abc" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (before-first-occurence n x {:return? false}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/index-of n x)]
                (subs n 0 dex)
                (if return? n)))))

(defn before-last-occurence
  ; @param (*) n
  ; @param (*) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (before-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "never")
  ;
  ; @example
  ; (before-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "never")
  ; =>
  ; "With insomnia, you're never really awake; but you're "
  ;
  ; @example
  ; (before-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (before-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "abc"
  ;                        {:return? true})
  ; =>
  ; "With insomnia, you're never really awake; but you're never really asleep."
  ;
  ; @example
  ; (before-last-occurence nil "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (before-last-occurence nil "abc" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (before-last-occurence n x {:return? false}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/last-index-of n x)]
                (subs n 0 dex)
                (if return? n)))))

(defn after-first-occurence
  ; @param (*) n
  ; @param (*) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (after-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "never")
  ;
  ; @example
  ; (after-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "never")
  ; =>
  ; " really awake; but you're never really asleep."
  ;
  ; @example
  ; (after-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (after-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "abc"
  ;                        {:return? true})
  ; =>
  ; "With insomnia, you're never really awake; but you're never really asleep."
  ;
  ; @example
  ; (after-first-occurence nil "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (after-first-occurence nil "abc" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (after-first-occurence n x {:return? false}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/index-of n x)]
                (subs n (+ dex (count x)))
                (if return? n)))))

(defn after-last-occurence
  ; @param (*) n
  ; @param (*) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (after-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                       "never")
  ;
  ; @example
  ; (after-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                       "never")
  ; =>
  ; "really asleep."
  ;
  ; @example
  ; (after-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                       "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (after-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                       "abc"
  ;                       {:return? true})
  ; =>
  ; "With insomnia, you're never really awake; but you're never really asleep."
  ;
  ; @example
  ; (after-last-occurence nil "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (after-last-occurence nil "abc" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (after-last-occurence n x {:return? false}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/last-index-of n x)]
                (subs n (+ dex (count x)))
                (if return? n)))))

(defn from-first-occurence
  ; @param (*) n
  ; @param (*) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (from-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                       "never")
  ;
  ; @example
  ; (from-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                       "never")
  ; =>
  ; "never really awake; but you're never really asleep."
  ;
  ; @example
  ; (from-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                       "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (from-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                       "abc"
  ;                       {:return? true})
  ; =>
  ; "With insomnia, you're never really awake; but you're never really asleep."
  ;
  ; @example
  ; (from-first-occurence nil "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (from-first-occurence nil "abc" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (from-first-occurence n x {:return? false}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/index-of n x)]
                (subs n dex)
                (if return? n)))))

(defn from-last-occurence
  ; @param (*) n
  ; @param (*) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (from-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                      "never")
  ;
  ; @example
  ; (from-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                      "never")
  ; =>
  ; "never really asleep."
  ;
  ; @example
  ; (from-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                      "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (from-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                      "abc"
  ;                      {:return? true})
  ; =>
  ; "With insomnia, you're never really awake; but you're never really asleep."
  ;
  ; @example
  ; (from-last-occurence nil "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (from-last-occurence nil "abc" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (from-last-occurence n x {:return? false}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/last-index-of n x)]
                (subs n dex)
                (if return? n)))))

(defn to-first-occurence
  ; @param (*) n
  ; @param (*) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (to-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                     "never")
  ;
  ; @example
  ; (to-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                     "never")
  ; =>
  ; "With insomnia, you're never"
  ;
  ; @example
  ; (to-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                     "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                     "abc"
  ;                     {:return? true})
  ; =>
  ; "With insomnia, you're never really awake; but you're never really asleep."
  ;
  ; @example
  ; (to-first-occurence nil "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-first-occurence nil "abc" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (to-first-occurence n x {:return? false}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/index-of n x)]
                (subs n 0 (+ dex (count x)))
                (if return? n)))))

(defn to-last-occurence
  ; @param (*) n
  ; @param (*) x
  ; @param (map) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (to-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                    "never")
  ;
  ; @example
  ; (to-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                    "never")
  ; =>
  ; "With insomnia, you're never really awake; but you're never"
  ;
  ; @example
  ; (to-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                    "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                    "abc"
  ;                    {:return? true})
  ; =>
  ; "With insomnia, you're never really awake; but you're never really asleep."
  ;
  ; @example
  ; (to-last-occurence nil "abc")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-last-occurence nil "abc" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (to-last-occurence n x {:return? false}))

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/last-index-of n x)]
                (subs n 0 (+ dex (count x)))
                (if return? n)))))

(defn remove-first-occurence
  ; @param (*) n
  ; @param (*) x
  ;
  ; @usage
  ; (remove-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                         "never")
  ;
  ; @example
  ; (remove-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                         "never")
  ; =>
  ; "With insomnia, you're really awake; but you're never really asleep."
  ;
  ; @example
  ; (remove-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                         "abc")
  ; =>
  ; "With insomnia, you're never really awake; but you're never really asleep."
  ;
  ; @return (string)
  [n x]
  (let [n (str n)
        x (str x)]
       (if-let [dex (clojure.string/index-of n x)]
               (str (subs n 0 dex)
                    (subs n (+ dex (count x))))
               (return n))))

(defn remove-last-occurence
  ; @param (*) n
  ; @param (*) x
  ;
  ; @usage
  ; (remove-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "never")
  ;
  ; @example
  ; (remove-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "never")
  ; =>
  ; "With insomnia, you're never really awake; but you're really asleep."
  ;
  ; @example
  ; (remove-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "abc")
  ; =>
  ; "With insomnia, you're never really awake; but you're never really asleep."
  ;
  ; @return (string)
  [n x]
  (let [n (str n)
        x (str x)]
       (if-let [dex (clojure.string/last-index-of n x)]
               (str (subs n 0 dex)
                    (subs n (+ dex (count x))))
               (return n))))

(defn between-occurences
  ; @param (*) n
  ; @param (*) x
  ; @param (*) y
  ;
  ; @usage
  ; (between-occurences "With insomnia, you're never really awake; but you're never really asleep."
  ;                     "never" "never")
  ;
  ; @example
  ; (between-occurences "With insomnia, you're never really awake; but you're never really asleep."
  ;                     "never" "asleep.")
  ; =>
  ; " really awake; but you're never really "
  ;
  ; @example
  ; (between-occurences "With insomnia, you're never really awake; but you're never really asleep."
  ;                     "never" "never")
  ; =>
  ; " really awake; but you're "
  ;
  ; @example
  ; (between-occurences "With insomnia, you're never really awake; but you're never really asleep."
  ;                     "abc" "never")
  ; =>
  ; nil
  ;
  ; @example
  ; (between-occurences "With insomnia, you're never really awake; but you're never really asleep."
  ;                     "abc" "def")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n x y]
  (-> n (after-first-occurence x {:return? false})
        (before-last-occurence y {:return? false})))
