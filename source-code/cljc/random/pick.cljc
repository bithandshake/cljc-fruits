
(ns random.pick)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn pick-vector-item
  ; @param (vector) n
  ;
  ; @usage
  ; (pick-vector-item [:a :b :c :d]
  ;
  ; @example
  ; (pick-vector-item [:a :b :c]
  ; =>
  ; :a
  ;
  ; @example
  ; (pick-vector-item [:a :b :c]
  ; =>
  ; :c
  ;
  ; @return (*)
  [n]
  ; Alternative: rand-nth
  (nth n (-> n count rand-int)))
