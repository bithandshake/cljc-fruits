
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
  ; @example
  ; (replace-item [:a :b :b :b] :b :x)
  ; =>
  ; [:a :x :x :x]
  ;
  ; @return (vector)
  [n a b]
  ; XXX#6799 (source-code/cljc/vector/core.cljc)
  (letfn [(f [result x]
             (if (= x a)
                 (conj result b)
                 (conj result x)))]
         (vec (reduce f [] n))))

(defn replace-first-item
  ; @description
  ; Replaces the first item in the given 'n' vector with the given 'x' value.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @return (vector)
  [n x]
  ; XXX#6799 (source-code/cljc/vector/core.cljc)
  (if (-> n vector?)
      (-> n drop (cons x) vec)
      (-> [x])))

(defn replace-last-item
  ; @description
  ; Replaces the last item in the given 'n' vector with the given 'x' value.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @return (vector)
  [n x]
  ; XXX#6799 (source-code/cljc/vector/core.cljc)
  (if (-> n vector?)
      (-> n drop-last (conj x) vec)
      (-> [x])))
