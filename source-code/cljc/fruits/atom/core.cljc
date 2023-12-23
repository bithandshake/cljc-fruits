
(ns fruits.atom.core)

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
