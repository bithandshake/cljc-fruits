
(ns string.core
    (:require [clojure.string]
              [math.api     :as math]
              [noop.api     :refer [return]]
              [string.check :as check]
              [string.cut   :as cut]
              [string.dex   :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn abc
  ; @param (*) a
  ; @param (*) b
  ;
  ; @usage
  ; (abc "abc" "def")
  ;
  ; @example
  ; (abc "abc" "def")
  ; =>
  ; "abc"
  ;
  ; @example
  ; (abc "def" "abc")
  ; =>
  ; "abc"
  ;
  ; @example
  ; (abc "abc" "abc")
  ; =>
  ; "abc"
  ;
  ; @example
  ; (abc 10 12)
  ; =>
  ; "10"
  ;
  ; @example
  ; (abc? "" "abc")
  ; =>
  ; ""
  ;
  ; @return (string)
  [a b]
  (if (check/abc? a b)
      (return       a)
      (return       b)))

(defn length
  ; @param (*) n
  ;
  ; @usage
  ; (length "One Flew Over the Cuckoo's Nest")
  ;
  ; @example
  ; (length "One Flew Over the Cuckoo's Nest")
  ; =>
  ; 31
  ;
  ; @example
  ; (length [])
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n]
  (let [n (str n)]
       (if (empty? n)
           (return 0)
           (count  n))))

(defn get-nth-character
  ; @param (*) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (get-nth-character "abc" 2)
  ;
  ; @example
  ; (get-nth-character "abc" 2)
  ; =>
  ; "c"
  ;
  ; @example
  ; (get-nth-character {:a "A"} 1)
  ; =>
  ; ":"
  ;
  ; @param (string)
  [n dex]
  (let [n (str n)]
       (if (math/between? dex 0 (-> n count dec))
           (nth n dex))))

(defn multiply
  ; @param (*) n
  ; @param (integer) x
  ;
  ; @usage
  ; (multiply "a" 3)
  ;
  ; @example
  ; (multiply "a" 3)
  ; =>
  ; "aaa"
  ;
  ; @return (string)
  [n x]
  (when (integer? x)
        (letfn [(f [result _]
                   (str result n))]
               (reduce f "" (range 0 x)))))

(defn join
  ; @param (collection) coll
  ; @param (*) separator
  ; @param (map)(opt) options
  ; {:join-empty? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (join ["filename" "extension"] ".")
  ;
  ; @example
  ; (join ["filename" "extension"] ".")
  ; =>
  ; "filename.extension"
  ;
  ; @example
  ; (join ["a" "b" ""] ".")
  ; =>
  ; "a.b."
  ;
  ; @example
  ; (join ["a" "b" ""] "." {:join-empty? false})
  ; =>
  ; "a.b"
  ;
  ; @return (string)
  ([coll separator]
   (join coll separator {}))

  ([coll separator {:keys [join-empty?] :or {join-empty? true}}]
   (let [last-dex (-> coll count dec)]
        (letfn [(separate?      [dex]  (and (not= dex last-dex)
                                            (-> (nth coll (inc dex)) str empty? not)))
                (join?          [part] (or join-empty? (-> part str empty? not)))
                (f [result dex part] (if (join? part)
                                         (if (separate? dex)
                                             (str result part separator)
                                             (str result part))
                                         (return result)))]
               ; A reduce-kv csak vektor vagy térkép típust fogad, listát nem!
               (reduce-kv f "" (vec coll))))))

(defn split
  ; @param (*) n
  ; @param (clj: regex, cljs: regex or string) delimiter
  ;
  ; @example
  ; (split "a.b.c" ".")
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @example
  ; (split "a.b.c" #".")
  ; =>
  ; []
  ;
  ; @example
  ; (split "a.b.c" #"[.]")
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @example
  ; (split ".b.c" #"[.]")
  ; =>
  ; ["" "b" "c"]
  ;
  ; @example
  ; (split "a.b.c" #"_")
  ; =>
  ; ["a.b.c"]
  ;
  ; @return (strings in vector)
  [n delimiter]
  (let [n (str n)]
       (cond (-> n empty?)        []
             (-> delimiter some?) (clojure.string/split n delimiter)
             :return              [n])))

(defn prefix
  ; @param (*) n
  ; @param (*) x
  ; @param (*)(opt) separator
  ;
  ; @usage
  ; (prefix "420" "$")
  ;
  ; @example
  ; (prefix "420" "$")
  ; =>
  ; "$420"
  ;
  ; @example
  ; (prefix 420 "$")
  ; =>
  ; "$420"
  ;
  ; @example
  ; (prefix "" "$")
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (prefix n x nil))

  ([n x separator]
   (let [n (str n)]
        (if (empty?               n)
            (return               n)
            (str prefix separator n)))))

(defn suffix
  ; @param (*) n
  ; @param (*) x
  ; @param (*)(opt) separator
  ;
  ; @usage
  ; (suffix "420" "px")
  ;
  ; @example
  ; (suffix "420" "px")
  ; =>
  ; "420px"
  ;
  ; @example
  ; (suffix 420 "px")
  ; =>
  ; "420px"
  ;
  ; @example
  ; (suffix "" "px")
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (suffix n x nil))

  ([n x separator]
   (let [n (str n)]
        (if (empty? n)
            (return n)
            (str    n separator x)))))

(defn prepend
  ; @param (*) n
  ; @param (*) x
  ; @param (*)(opt) separator
  ;
  ; @usage
  ; (prepend "my-domain.com" "https://")
  ;
  ; @example
  ; (prepend "my-domain.com" "https://")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @return (string)
  ([n x]
   (prepend n x nil))

  ([n x separator]
   (prefix n x)))

(defn append
  ; @param (*) n
  ; @param (*) x
  ; @param (*)(opt) separator
  ;
  ; @usage
  ; (append "https://" "my-domain.com")
  ;
  ; @example
  ; (append "https://" "my-domain.com")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @return (string)
  ([n x]
   (append n x nil))

  ([n x separator]
   (suffix n x)))

(defn insert-part
  ; @param (*) n
  ; @param (*) x
  ; @param (integer) dex
  ;
  ; @usage
  ; (insert-part "abcd" "xx" 2)
  ;
  ; @example
  ; (insert-part "abcd" "xx" 2)
  ; =>
  ; "abxxcd"
  ;
  ; @return (string)
  [n x dex]
  (let [n     (str   n)
        count (count n)
        dex   (math/between! dex 0 count)]
       (str (subs n 0 dex) x
            (subs n   dex))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn count-occurences
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:separate-matches? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (count-occurences "abc" "a")
  ;
  ; @example
  ; (count-occurences "abca" "a")
  ; =>
  ; 2
  ;
  ; @example
  ; (count-occurences "abca" "ab")
  ; =>
  ; 1
  ;
  ; @example
  ; (count-occurences "aaaa" "aa")
  ; =>
  ; 3
  ;
  ; @example
  ; (count-occurences "aaaa" "aa" {:separate-matches? true})
  ; =>
  ; 2
  ;
  ; @return (integer)
  ([n x]
   (count-occurences n x {}))

  ([n x {:keys [separate-matches?]}]
   (let [n (str n)
         x (str x)]
        (if (check/contains-part? n x)
            (let [step (if separate-matches? (count x) 1)]
                 (letfn [(f [cursor match-count]
                            (if-let [first-dex (dex/first-dex-of (cut/part n cursor) x)]
                                    (let [step (if separate-matches? (count x) 1)]
                                         (f (+   cursor first-dex step)
                                            (inc match-count)))
                                    (return match-count)))]
                        (f 0 0)))
            (return 0)))))

(defn min-occurence?
  ; @param (*) n
  ; @param (*) x
  ; @param (integer) min
  ; @param (map)(opt) options
  ; {:separate-matches? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (min-occurence? "abc" "a" 1)
  ;
  ; @example
  ; (min-occurence? "abc abc" "a" 2)
  ; =>
  ; true
  ;
  ; @example
  ; (min-occurence? "abc abc" "a" 3)
  ; =>
  ; false
  ;
  ; @example
  ; (min-occurence? "aaaa" "aa" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (min-occurence? "aaaa" "aa" 3 {:separate-matches? true})
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x min]
   (min-occurence? n x min {}))

  ([n x min options]
   (let [occurence-count (count-occurences n x options)]
        (<= min occurence-count))))

(defn max-occurence?
  ; @param (*) n
  ; @param (*) x
  ; @param (integer) max
  ; @param (map)(opt) options
  ; {:separate-matches? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (max-occurence? "abc" "a" 1)
  ;
  ; @example
  ; (max-occurence? "abc abc" "a" 2)
  ; =>
  ; true
  ;
  ; @example
  ; (max-occurence? "abc abc abc" "a" 2)
  ; =>
  ; false
  ;
  ; @example
  ; (max-occurence? "aaaa" "aa" 2)
  ; =>
  ; false
  ;
  ; @example
  ; (max-occurence? "aaaa" "aa" 2 {:separate-matches? true})
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n x max]
   (max-occurence? n x max {}))

  ([n x max options]
   (let [occurence-count (count-occurences n x options)]
        (>= max occurence-count))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ends-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (ends-with? "The things you used to own, now they own you." ".")
  ;
  ; @example
  ; (ends-with? "The things you used to own, now they own you." ".")
  ; =>
  ; true
  ;
  ; @example
  ; (ends-with? "The things you used to own, now they own you." "")
  ; =>
  ; true
  ;
  ; @example
  ; (ends-with? "The things you used to own, now they own you." "!")
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x]
   (ends-with? n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (let [n (str n)
         x (str x)]
        (if case-sensitive? (clojure.string/ends-with? n x)
                            (clojure.string/ends-with? (clojure.string/lower-case n)
                                                       (clojure.string/lower-case x))))))

(defn not-ends-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (not-ends-with? "The things you used to own, now they own you." ".")
  ;
  ; @example
  ; (not-ends-with? "The things you used to own, now they own you." "!")
  ; =>
  ; true
  ;
  ; @example
  ; (not-ends-with? "The things you used to own, now they own you." ".")
  ; =>
  ; false
  ;
  ; @example
  ; (not-ends-with? "The things you used to own, now they own you." "")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [ends-with? (ends-with? n x)]
       (not ends-with?)))

(defn ends-with!
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (ends-with! "The things you used to own, now they own you." ".")
  ;
  ; @example
  ; (ends-with! "The things you used to own, now they own you." ".")
  ; =>
  ; "The things you used to own, now they own you."
  ;
  ; @example
  ; (ends-with! "The things you used to own, now they own you" ".")
  ; =>
  ; "The things you used to own, now they own you."
  ;
  ; @return (string)
  ([n x]
   (ends-with! n x {}))

  ([n x options]
   (if (ends-with? n x options)
       (return n)
       (str    n x))))

(defn not-ends-with!
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (not-ends-with! "The things you used to own, now they own you." ".")
  ;
  ; @example
  ; (not-ends-with! "The things you used to own, now they own you" ".")
  ; =>
  ; "The things you used to own, now they own you"
  ;
  ; @example
  ; (not-ends-with! "The things you used to own, now they own you." ".")
  ; =>
  ; "The things you used to own, now they own you"
  ;
  ; @example
  ; (not-ends-with! nil ".")
  ; =>
  ; nil
  ;
  ; @return (string)
  ([n x]
   (not-ends-with! n x {}))

  ([n x options]
   (if (ends-with?                n x options)
       (cut/before-last-occurence n x)
       (return                    n))))

(defn starts-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;               "On a")
  ;
  ; @example
  ; (starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;               "On a")
  ; =>
  ; true
  ;
  ; @example
  ; (starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;               "")
  ; =>
  ; true
  ;
  ; @example
  ; (starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;               "The ")
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x]
   (starts-with? n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (let [n (str n)
         x (str x)]
        (if case-sensitive? (clojure.string/starts-with? n x)
                            (clojure.string/starts-with? (clojure.string/lower-case n)
                                                         (clojure.string/lower-case x))))))

(defn not-starts-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;                   "On a")
  ;
  ; @example
  ; (not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;                   "The ")
  ; =>
  ; true
  ;
  ; @example
  ; (not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;                   "")
  ; =>
  ; false
  ;
  ; @example
  ; (not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
  ;                   "On a")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [starts-with? (starts-with? n x)]
       (not starts-with?)))

(defn starts-with!
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (starts-with! "On a long enough time line, the survival rate for everyone drops to zero."
  ;               "On a")
  ;
  ; @example
  ; (starts-with! "On a long enough time line, the survival rate for everyone drops to zero."
  ;               "On a")
  ; =>
  ; "On a long enough time line, the survival rate for everyone drops to zero."
  ;
  ; @example
  ; (starts-with! " long enough time line, the survival rate for everyone drops to zero."
  ;               "On a")
  ; =>
  ; "On a long enough time line, the survival rate for everyone drops to zero."
  ;
  ; @return (string)
  ([n x]
   (starts-with! n x {}))

  ([n x options]
   (if (starts-with? n x options)
       (return       n)
       (str          x n))))

(defn not-starts-with!
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (not-starts-with! "On a long enough time line, the survival rate for everyone drops to zero."
  ;                   "On a")
  ;
  ; @example
  ; (not-starts-with! " long enough time line, the survival rate for everyone drops to zero."
  ;                   "On a")
  ; =>
  ; " long enough time line, the survival rate for everyone drops to zero."
  ;
  ; @example
  ; (not-starts-with! " long enough time line, the survival rate for everyone drops to zero."
  ;                   "On a")
  ; =>
  ; " long enough time line, the survival rate for everyone drops to zero."
  ;
  ; @example
  ; (not-starts-with! nil ".")
  ; =>
  ; nil
  ;
  ; @return (string)
  ([n x]
   (not-starts-with! n x {}))

  ([n x options]
   (if (starts-with?              n x options)
       (cut/after-first-occurence n x)
       (return                    n))))

(defn pass-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @example
  ; (pass-with? "abc" "ab")
  ; =>
  ; false
  ;
  ; @example
  ; (pass-with? "abc" "abc")
  ; =>
  ; true
  ;
  ; @example
  ; (pass-with? "abc" "Abc")
  ; =>
  ; false
  ;
  ; @example
  ; (pass-with? "abc" "Abc" {:case-sensitive? false})
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n x]
   (pass-with? n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (let [n (str n)
         x (str x)]
        (or (= n x)
            (and (not case-sensitive?)
                 (= (clojure.string/lower-case n)
                    (clojure.string/lower-case x)))))))

(defn not-pass-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @example
  ; (not-pass-with? "abc" "ab")
  ; =>
  ; true
  ;
  ; @example
  ; (not-pass-with? "abc" "abc")
  ; =>
  ; false
  ;
  ; @example
  ; (not-pass-with? "abc" "Abc")
  ; =>
  ; true
  ;
  ; @example
  ; (not-pass-with? "abc" "Abc" {:case-sensitive? false})
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x]
   (not-pass-with? n x {}))

  ([n x options]
   (let [pass-with? (pass-with? n x options)]
        (not pass-with?))))
