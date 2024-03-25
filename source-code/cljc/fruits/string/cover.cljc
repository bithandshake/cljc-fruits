
(ns fruits.string.cover
    (:require [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn cover
  ; @param (string) n
  ; @param (string) x
  ; @param (integer)(opt) offset
  ;
  ; @usage
  ; (cover "user@provider.com" "**")
  ; =>
  ; "**er@provider.com"
  ;
  ; @usage
  ; (cover "user@provider.com" "**" 2)
  ; =>
  ; "us**@provider.com"
  ;
  ; @return (string)
  ([n x]
   (cover n x 0))

  ([n x offset]
   (let [n      (str n)
         x      (str x)
         offset (seqable/normalize-cursor n offset {:adjust? true :mirror? true})]
        (letfn [(f0 [result cursor]
                    (cond ; If the cursor exceeded the end of the given 'n' string...
                          (-> n count (= cursor))
                          (-> result)
                          ; ...
                          (< cursor offset)
                          (f0 (str result (nth n cursor))
                              (inc cursor))
                          ; ...
                          (-> x count (+ offset) (<= cursor))
                          (f0 (str result (nth n cursor))
                             (inc cursor))
                          ; ...
                          :else
                          (f0 (str result (nth x (- cursor offset)))
                             (inc cursor))))]
               ; ...
               (f0 "" 0)))))
