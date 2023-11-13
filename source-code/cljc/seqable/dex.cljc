
(ns seqable.dex)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn normalize-dex
  ; @description
  ; - Normalizes the given index value for the given 'n' sequence.
  ; - Negative index values are used backwards as they were distances from the last index (not from the first).
  ; - The output is a non-negative index value that is not less than 0 and not more than the maximum index value.
  ;
  ; @param (*) string
  ; @param (integer) dex
  ;
  ; @usage
  ; (normalize-dex [:a :b :c] -2)
  ;
  ; @example
  ; (normalize-dex [:a :b :c] -2)
  ; =>
  ; 0
  ;
  ; @example
  ; (normalize-dex [:a :b :c] -5)
  ; =>
  ; 0
  ;
  ; @example
  ; (normalize-dex [:a :b :c] 5)
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n dex]
  (cond (-> dex integer? not)   (-> 0)
        (-> n seqable? not)     (-> dex)
        (-> n count   (<= dex)) (-> n count dec)
        (-> n count - (>= dex)) (-> 0)
        (< dex 0)               (-> n count dec (+ dex))
        :return                 (-> dex)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn dex-in-bounds?
  ; @description
  ; - Returns TRUE if the given 'dex' value falls between 0 and the highest possible index value (= item count minus one).
  ; - Cursors are different from indexes!
  ;   A cursor could point to the very end of a sequence while an index could only point before the last item.
  ;
  ; @param (*) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dex-in-bounds? [:a :b :c] 2)
  ;
  ; @example
  ; (dex-in-bounds? [:a :b :c] 2)
  ; =>
  ; true
  ;
  ; @example
  ; (dex-in-bounds? [:a :b :c] 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n dex]
  (and (-> dex nat-int?)
       (-> n seqable?)
       (-> n count (> dex))))

(defn dex-out-of-bounds?
  ; @description
  ; - Returns TRUE if the given 'dex' value doesn't fall between 0 and the highest possible index value (= item count minus one).
  ; - Cursors are different from indexes!
  ;   A cursor could point to the very end of a sequence while an index could only point before the last item.
  ;
  ; @param (*) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dex-out-of-bounds? [:a :b :c] 3)
  ;
  ; @example
  ; (dex-out-of-bounds? [:a :b :c] 3)
  ; =>
  ; true
  ;
  ; @example
  ; (dex-out-of-bounds? [:a :b :c] 2)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n dex]
  (or (-> dex nat-int? not)
      (-> n seqable? not)
      (-> n count (<= dex))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn next-dex
  ; @descripiton
  ; Returns the following index after the given 'dex' value in the given 'n' value.
  ;
  ; @param (seqable) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (next-dex [:a :b :c] 1)
  ;
  ; @example
  ; (next-dex [:a :b :c] 1)
  ; =>
  ; 2
  ;
  ; @example
  ; (next-dex [:a :b :c] 2)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n dex]
  (cond (-> n seqable? not)       (-> 0)
        (-> n count dec (<= dex)) (-> 0)
        (-> dex (< 0))            (-> 0)
        :return                   (-> dex inc)))

(defn prev-dex
  ; @descripiton
  ; Returns the previous index before the given 'dex' value in the given 'n' value.
  ;
  ; @param (seqable) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (prev-dex [:a :b :c] 1)
  ;
  ; @example
  ; (prev-dex [:a :b :c] 1)
  ; =>
  ; 0
  ;
  ; @example
  ; (prev-dex [:a :b :c] 0)
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n dex]
  (cond (-> n seqable? not)      (-> 0)
        (-> dex (<= 0))          (-> n count dec)
        (-> n count dec (< dex)) (-> n count dec)
        :return                  (-> dex dec)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn dex-range
  ; @param (seqable) n
  ;
  ; @usage
  ; (dex-range [:a :b :c])
  ;
  ; @example
  ; (dex-range [:a :b :c])
  ; =>
  ; [0 1 2]
  ;
  ; @return (vector)
  [n]
  (-> n count range vec))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn dex-first?
  ; @param (seqable) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dex-first? [:a :b :c] 1)
  ;
  ; @example
  ; (dex-first? [:a :b :c] 1)
  ; =>
  ; true
  ;
  ; @example
  ; (dex-first? "abc" 1)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [_ dex]
  (= dex 0))

(defn dex-last?
  ; @param (seqable) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dex-last? [:a :b :c] 2)
  ;
  ; @example
  ; (dex-last? [:a :b :c] 2)
  ; =>
  ; true
  ;
  ; @example
  ; (dex-last? "abc" 2)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n dex]
  (-> n count dec (= dex)))

(defn first-dex
  ; @param (seqable) n
  ;
  ; @usage
  ; (first-dex [:a :b :c])
  ;
  ; @example
  ; (first-dex [:a :b :c])
  ; =>
  ; 0
  ;
  ; @example
  ; (first-dex "abc")
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n]
  (if (-> n seqable?)
      (-> 0)))

(defn last-dex
  ; @param (seqable) n
  ;
  ; @usage
  ; (last-dex [:a :b :c])
  ;
  ; @example
  ; (last-dex [:a :b :c])
  ; =>
  ; 2
  ;
  ; @example
  ; (last-dex "abc")
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n]
  (if (-> n seqable?)
      (-> n count dec)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn inc-dex
  ; @description
  ; Returns the next item's index after the given 'dex' value.
  ;
  ; @param (seqable) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (inc-dex [:a :b :c] 1)
  ;
  ; @example
  ; (inc-dex [:a :b :c] 1)
  ; =>
  ; 2
  ;
  ; @example
  ; (inc-dex [:a :b :c] 2)
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n dex]
  (if (-> n (dex-last? dex))
      (-> dex)
      (-> dex inc)))

(defn dec-dex
  ; @description
  ; Returns the previous item's index before the given 'dex' value.
  ;
  ; @param (seqable) n
  ; @param (integer) dex
  ;
  ; @usage
  ; (dec-dex [:a :b :c] 2)
  ;
  ; @example
  ; (dec-dex [:a :b :c] 2)
  ; =>
  ; 1
  ;
  ; @example
  ; (dec-dex [:a :b :c] 0)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n dex]
  (if (-> n (dex-first? dex))
      (-> dex)
      (-> dex dec)))
