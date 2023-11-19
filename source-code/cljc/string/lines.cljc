
(ns string.lines
    (:require [clojure.string]
              [seqable.api :as seqable]
              [string.set  :as set]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn containing-line
  ; @description
  ; Returns the containing line where the given cursor position falls within.
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (containing-line "abc\ndef\nghi" 5)
  ;
  ; @example
  ; (containing-line "abc\ndef\nghi" 5)
  ; =>
  ; "def"
  ;
  ; @return (string)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor cursor)]
       (subs n (if-let [prev-newline-position (clojure.string/last-index-of (subs n 0 cursor) "\n")] (+ prev-newline-position 1)      (-> 0))
               (if-let [next-newline-distance (clojure.string/index-of      (subs n   cursor) "\n")] (+ next-newline-distance cursor) (-> n count)))))

(defn remove-line
  ; @description
  ; Removes the line where the given cursor position falls within the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (remove-line "abc\ndef\nghi" 5)
  ;
  ; @example
  ; (remove-line "abc\ndef\nghi" 5)
  ; =>
  ; "abc\nghi"
  ;
  ; @example
  ; (remove-line "abc" 2)
  ; =>
  ; ""
  ;
  ; @return (string)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor cursor)]
       (str (if-let [prev-newline-position (clojure.string/last-index-of (subs n 0 cursor) "\n")] (subs n 0 (-> prev-newline-position)))
            (if-let [next-newline-distance (clojure.string/index-of      (subs n   cursor) "\n")] (subs n   (-> next-newline-distance (+ cursor)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn in-empty-line?
  ; @description
  ; Returns TRUE if the given cursor position falls within an empty line?
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (in-empty-line? "abc\n\ndef" 4)
  ;
  ; @example
  ; (in-empty-line? "abc\n\ndef" 4)
  ; =>
  ; true
  ;
  ; @example
  ; (in-empty-line? "abc\n\ndef" 5)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor cursor)]
       (-> n (containing-line cursor)
             (empty?))))

(defn in-blank-line?
  ; @description
  ; Returns TRUE if the given cursor position falls within a blank line?
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (in-blank-line? "abc\n   \ndef" 4)
  ;
  ; @example
  ; (in-blank-line? "abc\n   \ndef" 4)
  ; =>
  ; true
  ;
  ; @example
  ; (in-blank-line? "abc\ndef\nghi" 5)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor cursor)]
       (-> n (containing-line cursor)
             (re-find #"[\s\t]{0,}")
             (boolean))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-newlines
  ; @param (*) n
  ;
  ; @usage
  ; (remove-newlines "abc\r\n")
  ;
  ; @example
  ; (remove-newlines "abc\r\n")
  ; =>
  ; "abc"
  ;
  ; @return (string)
  [n]
  (clojure.string/replace (str n) #"[\r\n]" ""))

(defn line-count
  ; @description
  ; Returns the count of newlines in the given string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (line-count "abc\n")
  ;
  ; @example
  ; (line-count "abc\n")
  ; =>
  ; 1
  ;
  ; @example
  ; (line-count "abc")
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n]
  (get (-> n str frequencies) \newline))

(defn max-lines
  ; @description
  ; - Limits the line count of the given 'n' string.
  ; - With the '{:reverse? true}' setting it removes the beginning of the given string instead of removing the end.
  ;
  ; @param (*) n
  ; @param (integer) limit
  ; @param (map)(opt) options
  ; {:reverse? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (max-lines "abc\ndef" 1)
  ;
  ; @example
  ; (max-lines "abc\ndef\nghi" 2)
  ; =>
  ; "abc\ndef"
  ;
  ; @example
  ; (max-lines "abc\ndef\nghi" 2 {:reverse? true})
  ; =>
  ; "def\nghi"
  ;
  ; @return (string)
  ([n limit]
   (max-lines n limit {}))

  ([n limit {:keys [reverse?]}]
   (let [n     (str       n)
         lines (set/split n #"\n")
         count (count     lines)
         limit (min       limit count)
         lines (if reverse? (subvec lines (- count limit) count)
                            (subvec lines 0 limit))]
        (letfn [(f [result dex]
                   (if (= dex limit)
                       (-> result)
                       (f (str result (if (not= dex 0) "\n") (nth lines dex))
                          (inc dex))))]
               (f "" 0)))))
