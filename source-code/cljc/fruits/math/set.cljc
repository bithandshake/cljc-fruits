
(ns fruits.math.set)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn add
  ; @description
  ; Adds the given number type parameters.
  ;
  ; @param (list of *) abc
  ;
  ; @usage
  ; (add 7 42 69)
  ; =>
  ; 118
  ;
  ; @usage
  ; (add 7 "My string" 69)
  ; =>
  ; 76
  ;
  ; @return (number)
  [& abc]
  (letfn [(f0 [total x]
              (if (-> x number?)
                  (+  total x)
                  (-> total)))]
         (reduce f0 0 (vec abc))))

(defn subtract
  ; @description
  ; Subtracts the given number type parameters.
  ;
  ; @param (list of *) abc
  ;
  ; @usage
  ; (subtract 7 42 69)
  ; =>
  ; -104
  ;
  ; @usage
  ; (add 7 "My string" 69)
  ; =>
  ; -62
  ;
  ; @return (number)
  [& abc]
  (letfn [(f0 [total x]
              (if (-> x number?)
                  (-  total x)
                  (-> total)))]
         (reduce f0 0 (vec abc))))

(defn multiply
  ; @description
  ; Multiplies the given number type parameters.
  ;
  ; @param (list of *) abc
  ;
  ; @usage
  ; (multiply 7 42 69)
  ; =>
  ; 20286
  ;
  ; @usage
  ; (multiply 7 "My string" 69)
  ; =>
  ; 483
  ;
  ; @return (number)
  [& abc]
  (letfn [(f0 [total x]
              (if (-> x number?)
                  (*  total x)
                  (-> total)))]
         (reduce f0 0 (vec abc))))
