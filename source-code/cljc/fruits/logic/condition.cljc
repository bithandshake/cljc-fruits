
(ns fruits.logic.condition)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn if-or
  ; @description
  ; If 'a' or 'b' is TRUE, then returns 'c', else returns 'd'.
  ;
  ; @param (*) a
  ; @param (*) b
  ; @param (*) c
  ; @param (*) d
  ;
  ; @usage
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
  ; @usage
  ; (if-and true false "C" "D")
  ; =>
  ; "D"
  ;
  ; @return (*)
  ([a b c]
   (when (and a b) c))

  ([a b c d]
   (if (and a b) c d)))
