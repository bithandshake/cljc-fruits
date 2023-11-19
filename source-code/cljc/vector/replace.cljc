
(ns vector.replace)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn replace-item
  ; @description
  ; Replaces items (that are indentical to the given 'a' value) in the given 'n' vector with the given 'b' value.
  ;
  ; @param (vector) n
  ; @param (*) a
  ; @param (*) b
  ;
  ; @usage
  ; (replace-item [:a :b :c] :c :x)
  ;
  ; @example
  ; (replace-item [:a :b :c :d :c] :c :x)
  ; =>
  ; [:a :b :x :d :x]
  ;
  ; @return (vector)
  [n a b]
  ; XXX#6799
  (letfn [(f [result x]
             (if (= x a)
                 (conj result b)
                 (conj result x)))]
         (vec (reduce f [] n))))
