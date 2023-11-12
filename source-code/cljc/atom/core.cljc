
(ns atom.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not!
  ; @param (atom) n
  ;
  ; @usage
  ; (def my-atom (atom true))
  ; (not! my-atom)
  ;
  ; @return (atom)
  [n]
  (swap! n not))

(defn inc!
  ; @param (atom) n
  ;
  ; @usage
  ; (def my-atom (atom 42))
  ; (inc! my-atom)
  ;
  ; @return (atom)
  [n]
  (swap! n inc))

(defn dec!
  ; @param (atom) n
  ;
  ; @usage
  ; (def my-atom (atom 42))
  ; (dec! my-atom)
  ;
  ; @return (atom)
  [n]
  (swap! n dec))

(defn apply!
  ; @param (atom) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (def my-atom (atom 42))
  ; (defn my-f [x] (inc x))
  ; (apply! my-atom my-f)
  ;
  ; @return (atom)
  [n f & params])
  ; TODO
