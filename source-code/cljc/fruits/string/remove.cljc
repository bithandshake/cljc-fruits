
(ns fruits.string.remove
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-part
  ; @description
  ; Removes every occurence of the given 'x' value from the given 'n' string.
  ;
  ; @param (string) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (remove-part "abc" "b")
  ; =>
  ; "ac"
  ;
  ; @usage
  ; (remove-part "abc abc" "b")
  ; =>
  ; "ac ac"
  ;
  ; @usage
  ; (remove-part "abc abc 123" #"\d")
  ; =>
  ; "abc abc "
  ;
  ; @usage
  ; (remove-part "///" "//")
  ; =>
  ; "/"
  ;
  ; @return (string)
  [n x]
  (let [n (str n)]
       (clojure.string/replace n x "")))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-first-character
  ; @description
  ; Removes the first character of the given 'n' string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (remove-first-character "abcdef")
  ; =>
  ; "bcdef"
  ;
  ; @return (string)
  [n]
  (let [n (str n)]
       (if (-> n count pos?)
           (-> n (subs 1))
           (-> n))))

(defn remove-last-character
  ; @description
  ; Removes the last character of the given 'n' string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (remove-last-character "abcdef")
  ; =>
  ; "abcde"
  ;
  ; @return (string)
  [n]
  (let [n (str n)]
       (if (-> n count pos?)
           (-> n (subs 0 (-> n count dec)))
           (-> n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-first-occurence
  ; @description
  ; Removes the first occurence of the given 'x' string from the given 'n' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (remove-first-occurence "abc abc 123" "abc")
  ; =>
  ; " abc 123"
  ;
  ; @usage
  ; (remove-first-occurence "abc abc 123" "def")
  ; =>
  ; "abc abc 123"
  ;
  ; @return (string)
  ([n x]
   (remove-first-occurence n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x]
               (if-let [dex (clojure.string/index-of o x)]
                       (str (subs n 0 dex)
                            (subs n (+ dex (count x))))
                       (-> n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case))))))

(defn remove-last-occurence
  ; @description
  ; Removes the last occurence of the given 'x' string from the given 'n' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (remove-last-occurence "abc abc 123" "abc")
  ; =>
  ; "abc  123"
  ;
  ; @usage
  ; (remove-last-occurence "abc abc 123" "def")
  ; =>
  ; "abc abc 123"
  ;
  ; @return (string)
  ([n x]
   (remove-last-occurence n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x]
               (if-let [dex (clojure.string/last-index-of o x)]
                       (str (subs n 0 dex)
                            (subs n (+ dex (count x))))
                       (-> n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case))))))
