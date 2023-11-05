
(ns vector.cursor)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn cursor-in-bounds?
  ; @description
  ; - Returns TRUE if the given 'cursor' value is falls between 0 and the highest possible cursor value ('(count n)').
  ; - Cursors are different from indexes in vectors.
  ;   A cursor could be at the very end of the vector while an index could only point to the last item at the end.
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
  (and (-> cursor nat-int?)
       (-> n count (>= cursor))))

(defn cursor-out-of-bounds?
  ; @description
  ; - Returns TRUE if the given 'cursor' value is NOT falls between 0 and the highest possible cursor value ('(count n)').
  ; - Cursors are different from indexes in vectors.
  ;   A cursor could be at the very end of the vector while an index could only point to the last item at the end.
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
  (or (-> cursor nat-int?)
      (-> n count (< cursor))))
