
(ns fruits.string.lines
    (:require [clojure.string]
              [fruits.seqable.api  :as seqable]
              [fruits.string.check :as check]
              [fruits.string.set   :as set]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn line-position
  ; @description
  ; Returns the first cursor position of the corresponding line of a specific cursor position in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @return (integer)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (if-let [preceding-newline-position (clojure.string/last-index-of (subs n 0 cursor) "\n")]
               (-> preceding-newline-position inc)
               (-> 0))))

(defn prev-line-position
  ; @description
  ; Returns the first cursor position of the previous line of a specific cursor position in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @return (integer)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (if-let [preceding-newline-position (clojure.string/last-index-of (subs n 0 cursor) "\n")]
               (if-let [antecedent-newline-position (clojure.string/last-index-of (subs n 0 preceding-newline-position) "\n")]
                       (-> antecedent-newline-position inc)
                       (-> 0))
               (-> nil))))

(defn next-line-position
  ; @description
  ; Returns the first cursor position of the next line of a specific cursor position in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @return (integer)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (if-let [following-newline-position (clojure.string/index-of (subs n cursor) "\n")]
               (-> following-newline-position inc (+ cursor))
               (-> nil))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn containing-line
  ; @description
  ; Returns the containing line where the given cursor position falls within the given 'n' value (converted to string).
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
        cursor (seqable/normalize-cursor n cursor)]
       (subs n (if-let [prev-newline-position (clojure.string/last-index-of (subs n 0 cursor) "\n")] (+ prev-newline-position 1)      (-> 0))
               (if-let [next-newline-distance (clojure.string/index-of      (subs n   cursor) "\n")] (+ next-newline-distance cursor) (-> n count)))))

(defn remove-containing-line
  ; @description
  ; Removes the line where the given cursor position falls within the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (remove-containing-line "abc\ndef\nghi" 5)
  ;
  ; @example
  ; (remove-containing-line "abc\ndef\nghi" 5)
  ; =>
  ; "abc\nghi"
  ;
  ; @example
  ; (remove-containing-line "abc" 2)
  ; =>
  ; ""
  ;
  ; @return (string)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (str (if-let [prev-newline-position (clojure.string/last-index-of (subs n 0 cursor) "\n")] (subs n 0 (-> prev-newline-position)))
            (if-let [next-newline-distance (clojure.string/index-of      (subs n   cursor) "\n")] (subs n   (-> next-newline-distance (+ cursor)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn in-empty-line?
  ; @description
  ; Returns TRUE if the given cursor position falls within an empty line.
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
  ; (in-empty-line? "abc\ndef\nghi" 4)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (-> n (containing-line cursor) check/empty?)))

(defn not-in-empty-line?
  ; @description
  ; Returns TRUE if the given cursor position falls within a nonempty line.
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (not-in-empty-line? "abc\ndef\nghi" 4)
  ;
  ; @example
  ; (not-in-empty-line?"abc\ndef\nghi" 4)
  ; =>
  ; true
  ;
  ; @example
  ; (not-in-empty-line? "abc\n\ndef" 4)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (-> n (containing-line cursor) check/not-empty?)))

(defn in-blank-line?
  ; @description
  ; Returns TRUE if the given cursor position falls within a blank line.
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
        cursor (seqable/normalize-cursor n cursor)]
       (-> n (containing-line cursor) check/blank?)))

(defn not-in-blank-line?
  ; @description
  ; Returns TRUE if the given cursor position falls within a nonblank line.
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (not-in-blank-line? "abc\ndef\nghi" 4)
  ;
  ; @example
  ; (not-in-blank-line? "abc\ndef\nghi" 4)
  ; =>
  ; true
  ;
  ; @example
  ; (not-in-blank-line? "abc\n   \nghi" 4)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (-> n (containing-line cursor) check/not-blank?)))

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

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

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
        (letfn [(f0 [result dex]
                    (if (= dex limit)
                        (-> result)
                        (f0 (str result (if (not= dex 0) "\n") (nth lines dex))
                           (inc dex))))]
               (f0 "" 0)))))
