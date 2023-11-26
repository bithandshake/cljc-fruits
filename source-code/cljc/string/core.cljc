
(ns string.core
    (:require [clojure.string]
              [math.api       :as math]
              [seqable.api    :as seqable]
              [string.contain :as contain]
              [string.cut     :as cut]
              [string.dex     :as dex]))

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
        (letfn [(f0 [result cursor]
                    (cond ; If the cursor exceeded the end of the given 'n' string...
                          (-> n count (= cursor))
                          (-> result)
                          ; ...
                          (< cursor offset)
                          (f0 (str result (nth n cursor))
                              (inc cursor))
                          ; ...
                          (-> x count (+ offset) (<= cursor))
                          (f0 (str result (nth n cursor))
                             (inc cursor))
                          ; ...
                          :else
                          (f0 (str result (nth x (- cursor offset)))
                             (inc cursor))))]
               ; ...
               (f0 "" 0)))))

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
        (letfn [(f0 [result cursor]
                    (cond ; ...
                          (< (-> n count)
                             (-> x count (+ cursor)))
                          (-> result)
                          ; ...
                          (= x (subs n cursor (+ cursor (count x))))
                          (f0 (inc result)
                             (if separate-matches? (+ cursor (count x))
                                                   (inc cursor)))
                          ; ...
                          :else
                          (f0 result (inc cursor))))]
               ; ...
               (f0 0 0)))))

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

(defn starts-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (starts-with? "abcdef" "ab")
  ;
  ; @example
  ; (starts-with? "abcdef" "ab")
  ; =>
  ; true
  ;
  ; @example
  ; (starts-with? "abcdef" "")
  ; =>
  ; true
  ;
  ; @example
  ; (starts-with? "abcdef" "bc")
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x]
   (starts-with? n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (let [n (str n)
         x (str x)]
        (letfn [(f0 [n x] (clojure.string/starts-with? n x))]
               (if case-sensitive? (f0 n x)
                                   (f0 (clojure.string/lower-case n)
                                       (clojure.string/lower-case x)))))))

(defn ends-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (ends-with? "abcdef" "ef")
  ;
  ; @example
  ; (ends-with? "abcdef" "ef")
  ; =>
  ; true
  ;
  ; @example
  ; (ends-with? "abcdef" "")
  ; =>
  ; true
  ;
  ; @example
  ; (ends-with? "abcdef" "de")
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x]
   (ends-with? n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (let [n (str n)
         x (str x)]
        (letfn [(f0 [n x] (clojure.string/ends-with? n x))]
               (if case-sensitive? (f0 n x)
                                   (f0 (clojure.string/lower-case n)
                                       (clojure.string/lower-case x)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not-starts-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (not-starts-with? "abcdef" "bc")
  ;
  ; @example
  ; (not-starts-with? "abcdef" "bc")
  ; =>
  ; true
  ;
  ; @example
  ; (not-starts-with? "abcdef" "")
  ; =>
  ; false
  ;
  ; @example
  ; (not-starts-with? "abcdef" "ab")
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x]
   (not-starts-with? n x {}))

  ([n x options]
   (let [starts-with? (starts-with? n x options)]
        (not starts-with?))))

(defn not-ends-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (not-ends-with? "abcdef" "de")
  ;
  ; @example
  ; (not-ends-with? "abcdef" "de")
  ; =>
  ; true
  ;
  ; @example
  ; (not-ends-with? "abcdef" "")
  ; =>
  ; false
  ;
  ; @example
  ; (not-ends-with? "abcdef" "ef")
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x]
   (not-ends-with? n x {}))

  ([n x options]
   (let [ends-with? (ends-with? n x options)]
        (not ends-with?))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn starts-with!
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (starts-with! "abcdef" "ab")
  ;
  ; @example
  ; (starts-with! "abcdef" "ab")
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (starts-with! "cdef" "ab")
  ; =>
  ; "abcdef"
  ;
  ; @return (string)
  ([n x]
   (starts-with! n x {}))

  ([n x options]
   (if (starts-with? n x options)
       (->  n)
       (str x n))))

(defn ends-with!
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (ends-with! "abcdef" "ef")
  ;
  ; @example
  ; (ends-with! "abcdef" "ef")
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (ends-with! "abcd" "ef")
  ; =>
  ; "abcdef"
  ;
  ; @return (string)
  ([n x]
   (ends-with! n x {}))

  ([n x options]
   (if (ends-with? n x options)
       (->  n)
       (str n x))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not-starts-with!
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (not-starts-with! "abcdef" "ab")
  ;
  ; @example
  ; (not-starts-with! "abcdef" "ab")
  ; =>
  ; "cdef"
  ;
  ; @example
  ; (not-starts-with! "cdef" "ab")
  ; =>
  ; "cdef"
  ;
  ; @example
  ; (not-starts-with! nil "ab")
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

(defn not-ends-with!
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (not-ends-with! "abcdef" "ef")
  ;
  ; @example
  ; (not-ends-with! "abcdef" "ef")
  ; =>
  ; "abcd"
  ;
  ; @example
  ; (not-ends-with! "abcd" "ef")
  ; =>
  ; "abcd"
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

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn matches-with?
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (matches-with? "abc" "abc")
  ;
  ; @example
  ; (matches-with? "abc" "abc")
  ; =>
  ; true
  ;
  ; @example
  ; (matches-with? "abc" "ab")
  ; =>
  ; false
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
  ; @usage
  ; (not-matches-with? "abc" "ab")
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
  ; (starts-at? "abcdef" "de" 3)
  ;
  ; @example
  ; (starts-at? "abcdef" "de" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (starts-at? "abcdef" "de" 2)
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
        (letfn [(f0 [n x]
                    (let [end-cursor (-> x count (+ cursor))]
                         (and (-> n (seqable/cursor-in-bounds? end-cursor))
                              (-> n (subs cursor end-cursor)
                                    (= x)))))]
               (if case-sensitive? (f0 n x)
                                   (f0 (clojure.string/lower-case n)
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
  ; (ends-at? "abcdef" "de" 5)
  ;
  ; @example
  ; (ends-at? "abcdef" "de" 5)
  ; =>
  ; true
  ;
  ; @example
  ; (ends-at? "abcdef" "de" 2)
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
        (letfn [(f0 [n x]
                    (let [start-cursor (->> x count (- cursor))]
                         (and (-> n (seqable/cursor-in-bounds? start-cursor))
                              (-> n (subs start-cursor cursor)
                                    (= x)))))]
               (if case-sensitive? (f0 n x)
                                   (f0 (clojure.string/lower-case n)
                                       (clojure.string/lower-case x)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not-starts-at?
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
  ; (not-starts-at? "abcdef" "de" 2)
  ;
  ; @example
  ; (not-starts-at? "abcdef" "de" 2)
  ; =>
  ; true
  ;
  ; @example
  ; (not-starts-at? "abcdef" "de" 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x cursor]
   (not-starts-at? n x cursor {}))

  ([n x cursor options]
   (let [starts-at? (starts-at? n x cursor options)]
        (not starts-at?))))

(defn not-ends-at?
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
  ; (not-ends-at? "abcdef" "de" 2)
  ;
  ; @example
  ; (not-ends-at? "abcdef" "de" 2)
  ; =>
  ; true
  ;
  ; @example
  ; (not-ends-at? "abcdef" "de" 5)
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x cursor]
   (not-ends-at? n x cursor {}))

  ([n x cursor options]
   (let [ends-at? (ends-at? n x cursor options)]
        (not ends-at?))))
