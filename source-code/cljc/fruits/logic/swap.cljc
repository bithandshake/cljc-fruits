
(ns fruits.logic.swap)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

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
  ; @usage
  ; (swap "A" "A" "B")
  ; =>
  ; "B"
  ;
  ; @usage
  ; (swap "B" "A" "B")
  ; =>
  ; "A"
  ;
  ; @usage
  ; (swap "C" "A" "B")
  ; =>
  ; "C"
  ;
  ; @return (*)
  [x a b]
  (cond (= x a) b
        (= x b) a
        :return x))
