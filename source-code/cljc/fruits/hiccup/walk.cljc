
(ns fruits.hiccup.walk
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn walk
  ; @description
  ; Iterates over the given 'n' HICCUP value and applies the given 'f' function on each element.
  ;
  ; @param (hiccup) n
  ; @param (function) f
  ;
  ; @usage
  ; (walk [:td [:p {:style {:color "red"}}]]
  ;       #(conj % "420"))
  ; =>
  ; [:td [:p {:style {:color "red"}} "420"] "420"]
  ;
  ; @return (hiccup)
  [n f]
  (if (vector? n)
      (let [f (mixed/to-ifn f)]
           (letfn [(f0 [result x]
                       (conj result (walk x f)))]
                  (reduce f0 [] (f n))))
      (-> n)))
