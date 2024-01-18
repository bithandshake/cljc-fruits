
(ns fruits.seqable.path
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn dynamic-path
  ; @description
  ; Converts the given dynamic path into a static path of a specific item in the given 'n' value.
  ;
  ; @param (map or vector) n
  ; @param (vector) path
  ;
  ; @usage
  ; (dynamic-path [{:a [{:b "B"}]}] [last-dex :a last-dex :b])
  ; =>
  ; [0 :a 0 b]
  ;
  ; @return (vector)
  [n path]
  (let [path (mixed/to-vector path)]
       (letfn [(f0 [result k]
                   (if (fn? k)
                       (conj result (-> n (get-in result) k))
                       (conj result (-> k))))]
              (reduce f0 [] path))))
