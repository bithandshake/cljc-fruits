
(ns atom.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not!
  ; @param (atom) atom
  ;
  ; @usage
  ; (def my-atom (atom true))
  ; (not! my-atom)
  ;
  ; @return (atom)
  [atom]
  (swap! atom not))

(defn inc!
  ; @param (atom) atom
  ;
  ; @usage
  ; (def my-atom (atom 42))
  ; (inc! my-atom)
  ;
  ; @return (atom)
  [atom]
  (swap! atom inc))

(defn dec!
  ; @param (atom) atom
  ;
  ; @usage
  ; (def my-atom (atom 42))
  ; (dec! my-atom)
  ;
  ; @return (atom)
  [atom]
  (swap! atom dec))

(defn apply!
  ; @param (atom) atom
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (def my-atom (atom 42))
  ; (defn my-f [x] (inc x))
  ; (apply! my-atom my-f)
  ;
  ; @return (atom)
  [atom f & params])
  ; TODO
