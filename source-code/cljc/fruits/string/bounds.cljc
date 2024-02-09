
(ns fruits.string.bounds
    (:require [clojure.string]
              [fruits.seqable.api :as seqable]
              [fruits.string.cut  :as cut]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn starts-with?
  ; @description
  ; Returns TRUE if the given 'n' string starts with the given 'x' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (starts-with? "abcdef" "ab")
  ; =>
  ; true
  ;
  ; @usage
  ; (starts-with? "abcdef" "")
  ; =>
  ; true
  ;
  ; @usage
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
  ; @description
  ; Returns TRUE if the given 'n' string ends with the given 'x' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (ends-with? "abcdef" "ef")
  ; =>
  ; true
  ;
  ; @usage
  ; (ends-with? "abcdef" "")
  ; =>
  ; true
  ;
  ; @usage
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
  ; @description
  ; Returns TRUE if the given 'n' string not starts with the given 'x' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (not-starts-with? "abcdef" "bc")
  ; =>
  ; true
  ;
  ; @usage
  ; (not-starts-with? "abcdef" "")
  ; =>
  ; false
  ;
  ; @usage
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
  ; @description
  ; Returns TRUE if the given 'n' string not ends with the given 'x' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (not-ends-with? "abcdef" "de")
  ; =>
  ; true
  ;
  ; @usage
  ; (not-ends-with? "abcdef" "")
  ; =>
  ; false
  ;
  ; @usage
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

(defn start-with
  ; @description
  ; Ensures that the given 'n' string starts with the given 'x' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (start-with "abcdef" "ab")
  ; =>
  ; "abcdef"
  ;
  ; @usage
  ; (start-with "cdef" "ab")
  ; =>
  ; "abcdef"
  ;
  ; @return (string)
  ([n x]
   (start-with n x {}))

  ([n x options]
   (if (starts-with? n x options)
       (->  n)
       (str x n))))

(defn end-with
  ; @description
  ; Ensures that the given 'n' string ends with the given 'x' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (end-with "abcdef" "ef")
  ; =>
  ; "abcdef"
  ;
  ; @usage
  ; (end-with "abcd" "ef")
  ; =>
  ; "abcdef"
  ;
  ; @return (string)
  ([n x]
   (end-with n x {}))

  ([n x options]
   (if (ends-with? n x options)
       (->  n)
       (str n x))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not-start-with
  ; @description
  ; Ensures that the given 'n' string not starts with the given 'x' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (not-start-with "abcdef" "ab")
  ; =>
  ; "cdef"
  ;
  ; @usage
  ; (not-start-with "cdef" "ab")
  ; =>
  ; "cdef"
  ;
  ; @usage
  ; (not-start-with nil "ab")
  ; =>
  ; nil
  ;
  ; @return (string)
  ([n x]
   (not-start-with n x {}))

  ([n x options]
   (if (starts-with?              n x options)
       (cut/after-first-occurence n x)
       (-> n))))

(defn not-end-with
  ; @description
  ; Ensures that the given 'n' string not ends with the given 'x' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (not-end-with "abcdef" "ef")
  ; =>
  ; "abcd"
  ;
  ; @usage
  ; (not-end-with "abcd" "ef")
  ; =>
  ; "abcd"
  ;
  ; @usage
  ; (not-end-with nil ".")
  ; =>
  ; nil
  ;
  ; @return (string)
  ([n x]
   (not-end-with n x {}))

  ([n x options]
   (if (ends-with?                n x options)
       (cut/before-last-occurence n x)
       (-> n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn starts-at?
  ; @description
  ; Returns TRUE if the given 'x' string starts at the given 'cursor' position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (integer) cursor
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (starts-at? "abcdef" "de" 3)
  ; =>
  ; true
  ;
  ; @usage
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
         cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
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
  ; Returns TRUE if the given 'x' string ends at the given 'cursor' position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (integer) cursor
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (ends-at? "abcdef" "de" 5)
  ; =>
  ; true
  ;
  ; @usage
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
         cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
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
  ; Returns TRUE if the given 'x' string not starts at the given 'cursor' position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (integer) cursor
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (not-starts-at? "abcdef" "de" 2)
  ; =>
  ; true
  ;
  ; @usage
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
  ; Returns TRUE if the given 'x' string not ends at the given 'cursor' position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (integer) cursor
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (not-ends-at? "abcdef" "de" 2)
  ; =>
  ; true
  ;
  ; @usage
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

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn matches-with?
  ; @description
  ; Returns TRUE if the given 'n' string matches the given 'n' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (matches-with? "abc" "abc")
  ; =>
  ; true
  ;
  ; @usage
  ; (matches-with? "abc" "ab")
  ; =>
  ; false
  ;
  ; @usage
  ; (matches-with? "abc" "Abc")
  ; =>
  ; false
  ;
  ; @usage
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
  ; @description
  ; Returns TRUE if the given 'n' string not matches the given 'n' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)
  ;   Default: true}
  ;
  ; @usage
  ; (not-matches-with? "abc" "ab")
  ; =>
  ; true
  ;
  ; @usage
  ; (not-matches-with? "abc" "abc")
  ; =>
  ; false
  ;
  ; @usage
  ; (not-matches-with? "abc" "Abc")
  ; =>
  ; true
  ;
  ; @usage
  ; (not-matches-with? "abc" "Abc" {:case-sensitive? false})
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x]
   (not-matches-with? n x {}))

  ([n x options]
   (not (matches-with? n x options))))
