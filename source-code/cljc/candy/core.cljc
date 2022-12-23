
(ns candy.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn param
  ; @description
  ; A simple noop function for wrapping parameters of another functions or scopes.
  ; Returns with the given n parameter.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (param "x")
  ;
  ; @usage
  ; (defn my-function [a b] (str a b))
  ; (my-function (param "x")
  ;              (inc   42))
  ;
  ; @example
  ; (param "x")
  ; =>
  ; "x"
  ;
  ; @return (*)
  [n] n)

(defn return
  ; @description
  ; A simple noop function for wrapping return values of another functions or scopes.
  ; Returns with the given n parameter.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (return "x")
  ;
  ; @usage
  ; (let [my-value 42]
  ;      (return my-value))
  ;
  ; @example
  ; (return "x")
  ; =>
  ; "x"
  ;
  ; @return (*)
  [n] n)

(defn none
  ; @description
  ; A simple noop function for ignoring values.
  ; Returns with nil.
  ;
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
