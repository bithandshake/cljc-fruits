
(ns candy.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn param
  ; @param (*) n
  ;
  ; @example
  ; (param "x")
  ; =>
  ; "x"
  ;
  ; @return (*)
  [n] n)

(defn return
  ; @param (*) n
  ;
  ; @example
  ; (return "x")
  ; =>
  ; "x"
  ;
  ; @return (*)
  [n] n)

(defn none
  ; @param (*) n
  ;
  ; @example
  ; (none "x")
  ; =>
  ; nil
  ;
  ; @return (nil)
  [_] nil)
