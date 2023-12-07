
(ns fruits.seqable.cursor
    (:require [fruits.seqable.dex :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn normalize-cursor
  ; @description
  ; - Normalizes the given cursor value for the given 'n' sequence.
  ; - Negative cursor values are used backwards as they were distances from the end of the sequence (not from the beginning).
  ; - The output is a non-negative cursor value that is not less than 0 and not more than the maximum cursor value.
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (normalize-cursor [:a :b :c] -2)
  ;
  ; @example
  ; (normalize-cursor [:a :b :c] -2)
  ; =>
  ; 1
  ;
  ; @example
  ; (normalize-cursor [:a :b :c] -5)
  ; =>
  ; 0
  ;
  ; @example
  ; (normalize-cursor [:a :b :c] 5)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n cursor]
  (if (-> n seqable?)
      (cond (-> cursor integer? not)  (-> 0)
            (-> n count   (< cursor)) (-> n count)
            (-> n count - (> cursor)) (-> 0)
            (< cursor 0)              (-> n count (+ cursor))
            :return                   (-> cursor))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn cursor-in-bounds?
  ; @description
  ; - Returns TRUE if the given 'cursor' value falls between 0 and the highest possible cursor value (= item count).
  ; - Cursors are different from indexes!
  ;   A cursor could point to the very end of a sequence while an index could only point before the last item.
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (cursor-in-bounds? [:a :b :c] 3)
  ;
  ; @example
  ; (cursor-in-bounds? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @example
  ; (cursor-in-bounds? [:a :b :c] 4)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (if (-> n seqable?)
      (and (-> cursor nat-int?)
           (-> n count (>= cursor)))))

(defn cursor-out-of-bounds?
  ; @description
  ; - Returns TRUE if the given 'cursor' value doesn't fall between 0 and the highest possible cursor value (= item count).
  ; - Cursors are different from indexes!
  ;   A cursor could point to the very end of a sequence while an index could only point before the last item.
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (cursor-out-of-bounds? [:a :b :c] 4)
  ;
  ; @example
  ; (cursor-out-of-bounds? [:a :b :c] 4)
  ; =>
  ; true
  ;
  ; @example
  ; (cursor-out-of-bounds? [:a :b :c] 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (if (-> n seqable?)
      (or (-> cursor nat-int? not)
          (-> n count (< cursor)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn next-cursor
  ; @descripiton
  ; Returns the following cursor after the given 'cursor' value in the given 'n' value.
  ;
  ; @param (seqable) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (next-cursor [:a :b :c] 1)
  ;
  ; @example
  ; (next-cursor [:a :b :c] 1)
  ; =>
  ; 2
  ;
  ; @example
  ; (next-cursor [:a :b :c] 3)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n cursor]
  (if (-> n seqable?)
      (cond (-> n count (<= cursor)) (-> 0)
            (-> cursor (< 0))        (-> 0)
            :return                  (-> cursor inc))))

(defn prev-cursor
  ; @descripiton
  ; Returns the previous cursor before the given 'cursor' value in the given 'n' value.
  ;
  ; @param (seqable) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (prev-cursor [:a :b :c] 1)
  ;
  ; @example
  ; (prev-cursor [:a :b :c] 1)
  ; =>
  ; 0
  ;
  ; @example
  ; (prev-cursor [:a :b :c] 0)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n cursor]
  (if (-> n seqable?)
      (cond (-> cursor (<= 0))      (-> n count)
            (-> n count (< cursor)) (-> n count)
            :return                 (-> cursor dec))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn cursor-range
  ; @param (seqable) n
  ;
  ; @usage
  ; (cursor-range [:a :b :c])
  ;
  ; @example
  ; (cursor-range [:a :b :c])
  ; =>
  ; [0 1 2 3]
  ;
  ; @return (vector)
  [n]
  (if (-> n seqable?)
      (-> n count inc range vec)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn cursor-first?
  ; @param (seqable) n
  ; @param (cursor) dex
  ;
  ; @usage
  ; (cursor-first? [:a :b :c] 1)
  ;
  ; @example
  ; (cursor-first? [:a :b :c] 1)
  ; =>
  ; true
  ;
  ; @example
  ; (cursor-first? "abc" 1)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n cursor]
  (if (-> n seqable?)
      (= cursor 0)))

(defn cursor-last?
  ; @param (seqable) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (cursor-last? [:a :b :c] 3)
  ;
  ; @example
  ; (cursor-last? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @example
  ; (cursor-last? "abc" 3)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n cursor]
  (if (-> n seqable?)
      (-> n count (= cursor))))

(defn first-cursor
  ; @param (seqable) n
  ;
  ; @first
  ; (last-cursor [:a :b :c])
  ;
  ; @example
  ; (first-cursor [:a :b :c])
  ; =>
  ; 0
  ;
  ; @example
  ; (first-cursor "abc")
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n]
  (if (-> n seqable?)
      (-> 0)))

(defn last-cursor
  ; @param (seqable) n
  ;
  ; @usage
  ; (last-cursor [:a :b :c])
  ;
  ; @example
  ; (last-cursor [:a :b :c])
  ; =>
  ; 3
  ;
  ; @example
  ; (last-cursor "abc")
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n]
  (if (-> n seqable?)
      (-> n count)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn inc-cursor
  ; @description
  ; Increases the given 'cursor' value except if the result would be out of bounds within the given 'n' value.
  ;
  ; @param (seqable) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (inc-cursor [:a :b :c] 1)
  ;
  ; @example
  ; (inc-cursor [:a :b :c] 2)
  ; =>
  ; 3
  ;
  ; @example
  ; (inc-cursor [:a :b :c] 3)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n cursor]
  (if (-> n seqable?)
      (cond (-> n count (= cursor)) (-> cursor)
            (-> n count (< cursor)) (-> n count)
            (-> cursor  (<      0)) (-> 0)
            :else                   (-> cursor inc))))

(defn dec-cursor
  ; @description
  ; Decreases the given 'cursor' value except if the result would be out of bounds within the given 'n' value.
  ;
  ; @param (seqable) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dec-cursor [:a :b :c] 3)
  ;
  ; @example
  ; (dec-cursor [:a :b :c] 3)
  ; =>
  ; 2
  ;
  ; @example
  ; (dec-cursor [:a :b :c] 0)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n cursor]
  (if (-> n seqable?)
      (cond (-> n count (< cursor)) (-> n count)
            (-> cursor  (<      2)) (-> 0)
            :else                   (-> cursor dec))))
