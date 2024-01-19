
(ns fruits.logic.equal)

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
  ; @usage
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
  ; @usage
  ; (not=? "A" "B" "not equal" "equal")
  ; =>
  ; "not equal"
  ;
  ; @return (*)
  ([a b c]
   (when-not (= a b) c))

  ([a b c d]
   (if-not (= a b) c d)))

(defn nor=
  ; @description
  ; Returns TRUE if 'a' is not equal to any other parameter, FALSE otherwise.
  ;
  ; @param (*) a
  ; @param (list of *) bcd
  ;
  ; @usage
  ; (nor= :a :b :c)
  ; =>
  ; true
  ;
  ; @usage
  ; (nor= :a :b :a)
  ; =>
  ; false
  ;
  ; @usage
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
  ; @usage
  ; (or= :a :b :c)
  ; =>
  ; false
  ;
  ; @usage
  ; (or= :a :b :a)
  ; =>
  ; true
  ;
  ; @usage
  ; (or= :a :a)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [a & bcd]
  (boolean (some #(= a %) bcd)))
