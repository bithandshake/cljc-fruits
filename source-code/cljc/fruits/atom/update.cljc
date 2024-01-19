
(ns fruits.atom.update)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn not!
  ; @param (atom) n
  ;
  ; @usage
  ; (def my-atom (atom true))
  ; (not! my-atom)
  ;
  ; @return (boolean)
  [n]
  (swap! n not))

(defn inc!
  ; @param (atom) n
  ;
  ; @usage
  ; (def my-atom (atom 42))
  ; (inc! my-atom)
  ;
  ; @return (number)
  [n]
  (swap! n inc))

(defn dec!
  ; @param (atom) n
  ;
  ; @usage
  ; (def my-atom (atom 42))
  ; (dec! my-atom)
  ;
  ; @return (number)
  [n]
  (swap! n dec))
