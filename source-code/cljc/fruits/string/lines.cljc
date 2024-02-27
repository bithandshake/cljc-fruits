
(ns fruits.string.lines
    (:require [clojure.string]
              [fruits.seqable.api  :as seqable]
              [fruits.string.check :as check]
              [fruits.string.set   :as set]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn line-position
  ; @description
  ; Returns the first cursor position of the corresponding line of a specific cursor position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (line-position "abc\ndef\nghi" 2)
  ; =>
  ; 0
  ;
  ; @usage
  ; (line-position "abc\ndef\nghi" 3)
  ; =>
  ; 0
  ;
  ; @usage
  ; (line-position "abc\ndef\nghi" 8)
  ; =>
  ; 8
  ;
  ; @usage
  ; (line-position "abc\ndef\nghi" 9)
  ; =>
  ; 8
  ;
  ; @return (integer)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
       (if-let [preceding-newline-position (clojure.string/last-index-of (subs n 0 cursor) "\n")]
               (-> preceding-newline-position inc)
               (-> 0))))

(defn prev-line-position
  ; @description
  ; Returns the first cursor position of the previous line of a specific cursor position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (prev-line-position "abc\ndef\nghi" 2)
  ; =>
  ; nil
  ;
  ; @usage
  ; (prev-line-position "abc\ndef\nghi" 3)
  ; =>
  ; nil
  ;
  ; @usage
  ; (prev-line-position "abc\ndef\nghi" 8)
  ; =>
  ; 4
  ;
  ; @usage
  ; (prev-line-position "abc\ndef\nghi" 9)
  ; =>
  ; 4
  ;
  ; @return (integer)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
       (if-let [preceding-newline-position (clojure.string/last-index-of (subs n 0 cursor) "\n")]
               (if-let [antecedent-newline-position (clojure.string/last-index-of (subs n 0 preceding-newline-position) "\n")]
                       (-> antecedent-newline-position inc)
                       (-> 0))
               (-> nil))))

(defn next-line-position
  ; @description
  ; Returns the first cursor position of the next line of a specific cursor position in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (next-line-position "abc\ndef\nghi" 4)
  ; =>
  ; 8
  ;
  ; @usage
  ; (next-line-position "abc\ndef\nghi" 5)
  ; =>
  ; 8
  ;
  ; @usage
  ; (next-line-position "abc\ndef\nghi" 8)
  ; =>
  ; nil
  ;
  ; @usage
  ; (next-line-position "abc\ndef\nghi" 9)
  ; =>
  ; nil
  ;
  ; @return (integer)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
       (if-let [following-newline-position (clojure.string/index-of (subs n cursor) "\n")]
               (-> following-newline-position inc (+ cursor))
               (-> nil))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn containing-line
  ; @description
  ; Returns the containing line where the given cursor position falls within the given 'n' string.
  ;
  ; @param (string) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (containing-line "abc\ndef\nghi" 5)
  ; =>
  ; "def"
  ;
  ; @return (string)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
       (subs n (if-let [prev-newline-position (clojure.string/last-index-of (subs n 0 cursor) "\n")] (+ prev-newline-position 1)      (-> 0))
               (if-let [next-newline-distance (clojure.string/index-of      (subs n   cursor) "\n")] (+ next-newline-distance cursor) (-> n count)))))

(defn remove-containing-line
  ; @description
  ; Removes the line where the given cursor position falls within the given 'n' string.
  ;
  ; @param (string) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (remove-containing-line "abc\ndef\nghi" 5)
  ; =>
  ; "abc\nghi"
  ;
  ; @usage
  ; (remove-containing-line "abc" 2)
  ; =>
  ; ""
  ;
  ; @return (string)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
       (str (if-let [prev-newline-position (clojure.string/last-index-of (subs n 0 cursor) "\n")] (subs n 0 (-> prev-newline-position)))
            (if-let [next-newline-distance (clojure.string/index-of      (subs n   cursor) "\n")] (subs n   (-> next-newline-distance (+ cursor)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn in-empty-line?
  ; @description
  ; Returns TRUE if the given cursor position falls within an empty line.
  ;
  ; @param (string) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (in-empty-line? "abc\n\ndef" 4)
  ; =>
  ; true
  ;
  ; @usage
  ; (in-empty-line? "abc\ndef\nghi" 4)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
       (-> n (containing-line cursor) check/empty?)))

(defn not-in-empty-line?
  ; @description
  ; Returns TRUE if the given cursor position falls within a nonempty line.
  ;
  ; @param (string) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (not-in-empty-line?"abc\ndef\nghi" 4)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-in-empty-line? "abc\n\ndef" 4)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
       (-> n (containing-line cursor) check/not-empty?)))

(defn in-blank-line?
  ; @description
  ; Returns TRUE if the given cursor position falls within a blank line.
  ;
  ; @param (string) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (in-blank-line? "abc\n   \ndef" 4)
  ; =>
  ; true
  ;
  ; @usage
  ; (in-blank-line? "abc\ndef\nghi" 5)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
       (-> n (containing-line cursor) check/blank?)))

(defn not-in-blank-line?
  ; @description
  ; Returns TRUE if the given cursor position falls within a nonblank line.
  ;
  ; @param (string) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (not-in-blank-line? "abc\ndef\nghi" 4)
  ; =>
  ; true
  ;
  ; @usage
  ; (not-in-blank-line? "abc\n   \nghi" 4)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
       (-> n (containing-line cursor) check/not-blank?)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-newlines
  ; @description
  ; Removes the newline and break characters from the given 'n' string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (remove-newlines "abc\r\n")
  ; =>
  ; "abc"
  ;
  ; @return (string)
  [n]
  (clojure.string/replace (str n) #"[\r\n]" ""))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn newline-count
  ; @description
  ; Returns the count of newline characters in the given 'n' string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (newline-count "abc\ndef")
  ; =>
  ; 1
  ;
  ; @usage
  ; (newline-count "abc")
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n]
  (get (-> n str frequencies) \newline))

(defn line-count
  ; @description
  ; Returns the count of lines within the given 'n' string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (line-count "abc\ndef")
  ; =>
  ; 2
  ;
  ; @usage
  ; (line-count "abc")
  ; =>
  ; 1
  ;
  ; @return (integer)
  [n]
  (-> n newline-count inc))

(defn limit-lines
  ; @description
  ; - Limits the line count of the given 'n' string.
  ; - With the '{:reverse? true}' setting it removes the beginning of the given string instead of removing the end.
  ;
  ; @param (string) n
  ; @param (integer) limit
  ; @param (map)(opt) options
  ; {:reverse? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (limit-lines "abc\ndef\nghi" 2)
  ; =>
  ; "abc\ndef"
  ;
  ; @usage
  ; (limit-lines "abc\ndef\nghi" 2 {:reverse? true})
  ; =>
  ; "def\nghi"
  ;
  ; @return (string)
  ([n limit]
   (limit-lines n limit {}))

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
