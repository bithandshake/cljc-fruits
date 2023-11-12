
(ns string.core
    (:refer-clojure :exclude [repeat])
    (:require [clojure.string]
              [math.api       :as math]
              [seqable.api    :as seqable]
              [string.contain :as contain]
              [string.cut     :as cut]
              [string.dex     :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn repeat
  ; @param (*) n
  ; @param (integer) x
  ;
  ; @usage
  ; (repeat "a" 3)
  ;
  ; @example
  ; (repeat "a" 3)
  ; =>
  ; "aaa"
  ;
  ; @return (string)
  [n x]
  (if (nat-int? x)
      (letfn [(f [result]
                 (if (-> result count (= x))
                     (-> result)
                     (-> result (str x))))]
             (f ""))))

(defn join
  ; @param (collection) n
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
  ([n separator]
   (join n separator {}))

  ([n separator {:keys [join-empty?] :or {join-empty? true}}]
   (letfn [(f [result dex]
              (cond ; ...
                    (-> n count (= dex))
                    (-> result)
                    ; ...
                    (or join-empty? (-> n (nth dex) empty? not))
                    (if (and (-> n count dec (not= dex))
                             (-> (nth n (inc dex)) str empty? not))
                        (str result (nth n dex) separator)
                        (str result (nth n dex)))
                    ; ...
                    :return result))]
          ; ...
          (if (seqable? n)
              (f "" 0)))))

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
  ; @example
  ; (split "" #".")
  ; =>
  ; []
  ;
  ; @return (strings in vector)
  [n delimiter]
  (let [n (str n)]
       (cond (-> n empty?)        []
             (-> delimiter some?) (clojure.string/split n delimiter)
             :return              [n])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn cover
  ; @param (*) n
  ; @param (*) x
  ; @param (integer)(opt) offset
  ;
  ; @usage
  ; (cover "user@email.com" "**")
  ;
  ; @example
  ; (cover "user@email.com" "**")
  ; =>
  ; "**er@email.com"
  ;
  ; @example
  ; (cover "user@email.com" "**" 2)
  ; =>
  ; "us**@email.com"
  ;
  ; @return (string)
  ([n x]
   (cover n x 0))

  ([n x offset]
   (let [n      (str n)
         x      (str x)
         offset (seqable/normalize-cursor n offset)]
        (letfn [(f [result cursor]
                   (cond ; If the cursor exceeded the end of the given 'n' string...
                         (-> n count (= cursor))
                         (-> result)
                         ; ...
                         (< cursor offset)
                         (f (str result (nth n cursor))
                            (inc cursor))
                         ; ...
                         (-> x count (+ offset) (<= cursor))
                         (f (str result (nth n cursor))
                            (inc cursor))
                         ; ...
                         :else
                         (f (str result (nth x (- cursor offset)))
                            (inc cursor))))]
               ; ...
               (f "" 0)))))

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
        (if (->  n empty?)
            (->  n)
            (str x separator n)))))

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
        (if (->  n empty?)
            (->  n)
            (str n separator x)))))

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
  ; @param (integer) cursor
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
  [n x cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (str (subs n 0 cursor) x
            (subs n   cursor))))

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
        (letfn [(f [result cursor]
                   (cond ; ...
                         (< (-> n count)
                            (-> x count (+ cursor)))
                         (-> result)
                         ; ...
                         (= x (subs n cursor (+ cursor (count x))))
                         (f (inc result)
                            (if separate-matches? (+ cursor (count x))
                                                  (inc cursor)))
                         ; ...
                         :else
                         (f result (inc cursor))))]
               ; ...
               (f 0 0)))))

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
   (<= min (count-occurences n x options))))

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
   (>= max (count-occurences n x options))))

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
        (letfn [(f [n x] (clojure.string/ends-with? n x))]
               (if case-sensitive? (f n x)
                                   (f (clojure.string/lower-case n)
                                      (clojure.string/lower-case x)))))))

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
  ([n x]
   (not-ends-with? n x {}))

  ([n x options]
   (let [ends-with? (ends-with? n x options)]
        (not ends-with?))))

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
       (->  n)
       (str n x))))

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
       (-> n))))

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
        (letfn [(f [n x] (clojure.string/starts-with? n x))]
               (if case-sensitive? (f n x)
                                   (f (clojure.string/lower-case n)
                                      (clojure.string/lower-case x)))))))

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
  ([n x]
   (not-starts-with? n x {}))

  ([n x options]
   (let [starts-with? (starts-with? n x options)]
        (not starts-with?))))

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
       (->  n)
       (str x n))))

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
       (-> n))))

(defn matches-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @example
  ; (matches-with? "abc" "ab")
  ; =>
  ; false
  ;
  ; @example
  ; (matches-with? "abc" "abc")
  ; =>
  ; true
  ;
  ; @example
  ; (matches-with? "abc" "Abc")
  ; =>
  ; false
  ;
  ; @example
  ; (matches-with? "abc" "Abc" {:case-sensitive? false})
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n x]
   (matches-with? n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (let [n (str n)
         x (str x)]
        (or (= n x)
            (and (not case-sensitive?)
                 (= (clojure.string/lower-case n)
                    (clojure.string/lower-case x)))))))

(defn not-matches-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @example
  ; (not-matches-with? "abc" "ab")
  ; =>
  ; true
  ;
  ; @example
  ; (not-matches-with? "abc" "abc")
  ; =>
  ; false
  ;
  ; @example
  ; (not-matches-with? "abc" "Abc")
  ; =>
  ; true
  ;
  ; @example
  ; (not-matches-with? "abc" "Abc" {:case-sensitive? false})
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x]
   (not-matches-with? n x {}))

  ([n x options]
   (not (matches-with? n x options))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn starts-at?
  ; @description
  ; Returns TRUE if the given 'x' value (converted to string) starts at the given 'cursor' value in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (*) x
  ; @param (integer) cursor
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (starts-at? "The things you used to own, now they own you." "things" 4)
  ;
  ; @example
  ; (starts-at? "The things you used to own, now they own you." "things" 4)
  ; =>
  ; true
  ;
  ; @example
  ; (starts-at? "The things you used to own, now they own you." "things" 2)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x cursor]
   (starts-at? n x cursor {}))

  ([n x cursor {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (let [n      (str n)
         x      (str x)
         cursor (seqable/normalize-cursor n cursor)]
        (letfn [(f [n x] (let [end-cursor (-> x count (+ cursor))]
                              (and (-> n (seqable/cursor-in-bounds? end-cursor))
                                   (-> n (subs cursor end-cursor)
                                         (= x)))))]
               (if case-sensitive? (f n x)
                                   (f (clojure.string/lower-case n)
                                      (clojure.string/lower-case x)))))))

(defn ends-at?
  ; @description
  ; Returns TRUE if the given 'x' value (converted to string) ends at the given 'cursor' value in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (*) x
  ; @param (integer) cursor
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (ends-at? "The things you used to own, now they own you." "things" 10)
  ;
  ; @example
  ; (ends-at? "The things you used to own, now they own you." "things" 10)
  ; =>
  ; true
  ;
  ; @example
  ; (ends-at? "The things you used to own, now they own you." "things" 15)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x cursor]
   (ends-at? n x cursor {}))

  ([n x cursor {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (let [n      (str n)
         x      (str x)
         cursor (seqable/normalize-cursor n cursor)]
        (letfn [(f [n x] (let [start-cursor (->> x count (- cursor))]
                              (and (-> n (seqable/cursor-in-bounds? start-cursor))
                                   (-> n (subs start-cursor cursor)
                                         (= x)))))]
               (if case-sensitive? (f n x)
                                   (f (clojure.string/lower-case n)
                                      (clojure.string/lower-case x)))))))
