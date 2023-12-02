
(ns string.inline
    (:require [clojure.string]
              [seqable.api :as seqable]
              [string.set  :as set]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn inline-position
  ; @description
  ; Returns the inline position of the given cursor position (distance from the nearest preceding
  ; newline / break character) within the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (inline-position "\nabc\ndef" 7)
  ;
  ; @example
  ; (inline-position "\nabc\ndef" 7)
  ; =>
  ; 2
  ;
  ; @example
  ; (inline-position "\nabc\rdef" 6)
  ; =>
  ; 1
  ;
  ; @example
  ; (inline-position "abc" 3)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (min (if-let [newline-position (clojure.string/last-index-of (subs n 0 cursor) "\n")]
                    (-> cursor (- newline-position 1))
                    (-> cursor))
            (if-let [break-position (clojure.string/last-index-of (subs n 0 cursor) "\r")]
                    (-> cursor (- break-position 1))
                    (-> cursor)))))

(defn left-spacing-length
  ; @description
  ; Returns the preceding whitespace count of the given cursor position whithin the actual line
  ; in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (left-spacing-length " abc   def " 7)
  ;
  ; @example
  ; (left-spacing-length " abc   def " 7)
  ; =>
  ; 3
  ;
  ; @example
  ; (left-spacing-length " abc   def " 6)
  ; =>
  ; 2
  ;
  ; @example
  ; (left-spacing-length " abc   def " 8)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (loop [length 0]
             (cond (seqable/cursor-first? n (- cursor length))  (-> length)
                   (-> n (nth (- cursor length 1)) str (= " ")) (-> length inc recur)
                   :return length))))

(defn right-spacing-length
  ; @description
  ; Returns the following whitespace count of the given cursor position whithin the actual line
  ; in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (right-spacing-length " abc   def " 4)
  ;
  ; @example
  ; (right-spacing-length " abc   def " 4)
  ; =>
  ; 3
  ;
  ; @example
  ; (right-spacing-length " abc   def " 3)
  ; =>
  ; 0
  ;
  ; @example
  ; (right-spacing-length " abc   def " 5)
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (loop [length 0]
             (cond (seqable/cursor-last? n (+ cursor length)) (-> length)
                   (-> n (nth (+ cursor length)) str (= " ")) (-> length inc recur)
                   :return length))))

(defn left-indent-length
  ; @description
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (left-indent-length " abc   def " 7)
  ;
  ; @example
  ; (left-indent-length " abc   def " 7)
  ; =>
  ; 1
  ;
  ; @example
  ; (left-indent-length " abc   def " 6)
  ; =>
  ; 1
  ;
  ; @example
  ; (left-indent-length " abc   def " 8)
  ; =>
  ; 1
  ;
  ; @return (integer)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (loop [length 0 shift 0]
             (cond (seqable/cursor-first? n (- cursor shift))   (-> length)
                   (-> n (nth (- cursor shift 1)) str (= "\n")) (-> length)
                   (-> n (nth (- cursor shift 1)) str (= "\r")) (-> length)
                   (-> n (nth (- cursor shift 1)) str (= " "))  (recur (inc length) (inc shift))
                   :else                                        (recur (->       0) (inc shift))))))

(defn right-indent-length
  ; @description
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (right-indent-length " abc   def " 4)
  ;
  ; @example
  ; (right-indent-length " abc   def " 4)
  ; =>
  ; 1
  ;
  ; @example
  ; (right-indent-length " abc   def " 3)
  ; =>
  ; 1
  ;
  ; @example
  ; (right-indent-length " abc   def " 5)
  ; =>
  ; 1
  ;
  ; @return (integer)
  [n cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (loop [length 0 shift 0]
             (cond (seqable/cursor-last? n (+ cursor shift))  (-> length)
                   (-> n (nth (+ cursor shift)) str (= "\n")) (-> length)
                   (-> n (nth (+ cursor shift)) str (= "\r")) (-> length)
                   (-> n (nth (+ cursor shift)) str (= " "))  (recur (inc length) (inc shift))
                   :else                                      (recur (->       0) (inc shift))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn fix-inline-position
  ; @description
  ; Ensures that the given cursor position has a specific inline position, by adding / removing
  ; extra whitespaces before the cursor position or even inserting a newline character if needed.
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ; @param (integer) fixed-position
  ;
  ; @usage
  ; (fix-inline-position "abc" 0 2)
  ;
  ; @example
  ; (fix-inline-position "abc" 0 2)
  ; =>
  ; "  abc"
  ;
  ; @example
  ; (fix-inline-position "abcdef" 3 6)
  ; =>
  ; "abc   def"
  ;
  ; @example
  ; (fix-inline-position "abcdef" 3 1)
  ; =>
  ; "abc\n def"
  ;
  ; @return (string)
  [n cursor fixed-position]
  (let [n       (str n)
        cursor  (seqable/normalize-cursor n cursor)
        surplus (left-spacing-length      n cursor)
        shift   (- fixed-position (inline-position n cursor))]
       (cond (-> shift (= 0))          (-> n)
             (-> shift (> 0))          (str (subs n 0 (-> cursor))
                                            (set/repeat " " shift)
                                            (subs n   (-> cursor)))
             (-> shift - (<= surplus)) (str (subs n 0 (-> cursor (+ shift)))
                                            (subs n   (-> cursor)))
             :surplus<shift            (str (subs n 0 cursor) "\n"
                                            (set/repeat " " fixed-position)
                                            (subs n   cursor)))))
