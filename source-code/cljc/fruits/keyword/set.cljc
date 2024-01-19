
(ns fruits.keyword.set)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn join
  ; @description
  ; Joins the items of the given 'n' vector and converts the result into keyword.
  ;
  ; @param (keywords or strings in vector) n
  ; @param (string)(opt) delimiter
  ;
  ; @usage
  ; (join [:a :b "c" :d])
  ; =>
  ; :abcd
  ;
  ; @usage
  ; (join [:x/a :x/b "c" :d])
  ; =>
  ; :abcd
  ;
  ; @usage
  ; (join [:a :b "c" :d] ".")
  ; =>
  ; :a.b.c.d
  ;
  ; @return (keyword)
  ([n]
   (join n nil))

  ([n delimiter]
   (letfn [(f0 [result x]
               (if (keyword? x) (str result (if result delimiter) (name x))
                                (str result (if result delimiter) x)))]
          (keyword (reduce f0 nil n)))))
