
(ns string.cursor)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn cursor-in-bounds?
  ; @description
  ; - Returns TRUE if the given 'cursor' value is falls between 0 and the highest possible cursor value ('(count n)').
  ; - Cursors are different from indexes in strings.
  ;   A cursor could be at the very end of the string while an index could only point to the last character at the end.
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (cursor-in-bounds? "abc" 3)
  ;
  ; @example
  ; (cursor-in-bounds? "abc" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (cursor-in-bounds? "abc" 4)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (let [n (str n)]
       (and (-> cursor nat-int?)
            (-> n count (>= cursor)))))

(defn cursor-out-of-bounds?
  ; @description
  ; - Returns TRUE if the given 'cursor' value is NOT falls between 0 and the highest possible cursor value ('(count n)').
  ; - Cursors are different from indexes in strings.
  ;   A cursor could be at the very end of the string while an index could only point to the last character at the end.
  ;
  ; @param (*) n
  ; @param (integer) cursor
  ;
  ; @usage
  ; (cursor-out-of-bounds? "abc" 4)
  ;
  ; @example
  ; (cursor-out-of-bounds? "abc" 4)
  ; =>
  ; true
  ;
  ; @example
  ; (cursor-out-of-bounds? "abc" 3)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n cursor]
  (let [n (str n)]
       (or (-> cursor nat-int? not)
           (-> n count (< cursor)))))
