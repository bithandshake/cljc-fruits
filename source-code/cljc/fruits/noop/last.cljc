
(ns fruits.noop.last)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn param
  ; @description
  ; Returns the last parameter as it is.
  ;
  ; @param (list of *) params
  ;
  ; @usage
  ; (param :a :b :c)
  ; =>
  ; :c
  ;
  ; @return (*)
  [& params]
  (-> params last))

(defn return
  ; @description
  ; Returns the last parameter as it is.
  ;
  ; @param (list of *) params
  ;
  ; @usage
  ; (return :a :b :c)
  ; =>
  ; :c
  ;
  ; @return (*)
  [& params]
  (-> params last))

(defn none
  ; @description
  ; Returns nil.
  ;
  ; @param (list of *) params
  ;
  ; @usage
  ; (none :a :b :c)
  ; =>
  ; nil
  ;
  ; @return (nil)
  [& _])
