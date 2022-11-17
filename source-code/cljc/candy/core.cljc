
(ns candy.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn param
  ; @param (*) n
  ;
  ; @usage
  ; (param "x")
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
  ; @usage
  ; (return "x")
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
  ; @usage
  ; (none "x")
  ;
  ; @example
  ; (none "x")
  ; =>
  ; nil
  ;
  ; @return (nil)
  [_] nil)
