
(ns logic.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn =?
  ; @description
  ; If 'a' is equal to 'b', then returns 'c', else returns 'd'.
  ;
  ; @param (*) a
  ; @param (*) b
  ; @param (*) c
  ; @param (*) d
  ;
  ; @example
  ; (=? "A" "B" "equal" "not equal")
  ; =>
  ; "not equal"
  ;
  ; @return (*)
  ([a b c]
   (when (= a b) c))

  ([a b c d]
   (if (= a b) c d)))

(defn not=?
  ; @description
  ; If 'a' is NOT equal to 'b', then returns 'c', else returns 'd'.
  ;
  ; @param (*) a
  ; @param (*) b
  ; @param (*) c
  ; @param (*) d
  ;
  ; @example
  ; (not=? "A" "B" "not equal" "equal")
  ; =>
  ; "not equal"
  ;
  ; @return (*)
  ([a b c]
   (when-not (= a b) c))

  ([a b c d]
   (if-not (= a b) c d)))

(defn if-or
  ; @description
  ; If 'a' or 'b' is TRUE, then returns 'c', else returns 'd'.
  ;
  ; @param (*) a
  ; @param (*) b
  ; @param (*) c
  ; @param (*) d
  ;
  ; @example
  ; (if-or true false "C" "D")
  ; =>
  ; "C"
  ;
  ; @return (*)
  ([a b c]
   (when (or a b) c))

  ([a b c d]
   (if (or a b) c d)))

(defn if-and
  ; @description
  ; If 'a' and 'b' is TRUE, then returns 'c', else returns 'd'.
  ;
  ; @param (*) a
  ; @param (*) b
  ; @param (*) c
  ; @param (*) d
  ;
  ; @example
  ; (if-and true false "C" "D")
  ; =>
  ; "D"
  ;
  ; @return (*)
  ([a b c]
   (when (and a b) c))

  ([a b c d]
   (if (and a b) c d)))

(defn nor
  ; @description
  ; Returns TRUE if all the given parameters are FALSE.
  ;
  ; @param (list of *) abc
  ;
  ; @example
  ; (nor true false false)
  ; =>
  ; false
  ;
  ; @example
  ; (nor false false false)
  ; =>
  ; true
  ;
  ; @example
  ; (nor false nil)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [& abc]
  (not-any? boolean abc))

(defn nor=
  ; @description
  ; Returns TRUE if 'a' is not equal to any other parameter, FALSE otherwise.
  ;
  ; @param (*) a
  ; @param (list of *) bcd
  ;
  ; @example
  ; (nor= :a :b :c)
  ; =>
  ; true
  ;
  ; @example
  ; (nor= :a :b :a)
  ; =>
  ; false
  ;
  ; @example
  ; (nor= :a :a)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [a & bcd]
  (not (some #(= a %) bcd)))

(defn or=
  ; @description
  ; Returns TRUE if 'a' is equal to any other parameter, FALSE otherwise.
  ;
  ; @param (*) a
  ; @param (list of *) bcd
  ;
  ; @example
  ; (or= :a :b :c)
  ; =>
  ; false
  ;
  ; @example
  ; (or= :a :b :a)
  ; =>
  ; true
  ;
  ; @example
  ; (or= :a :a)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [a & bcd]
  (boolean (some #(= a %) bcd)))

(defn swap
  ; @description
  ; Returns 'b' if 'x' is equal to 'a',
  ; returns 'a' if 'x' is equal to 'b',
  ; returns 'x' otherwise.
  ;
  ; @param (*) x
  ; @param (*) a
  ; @param (*) b
  ;
  ; @example
  ; (swap "A" "A" "B")
  ; =>
  ; "B"
  ;
  ; @example
  ; (swap "B" "A" "B")
  ; =>
  ; "A"
  ;
  ; @example
  ; (swap "C" "A" "B")
  ; =>
  ; "C"
  ;
  ; @return (*)
  [x a b]
  (cond (= x a) b
        (= x b) a
        :return x))
